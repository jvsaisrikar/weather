package mypack.controller;

import mypack.service.WeatherService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final WeatherService weatherService = new WeatherService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("locationValue");

        try {
            if ("current".equals(location)) {
                location = getCurrentLocation();
            }

            JSONObject weatherForecastDataJson = weatherService.getWeatherForecastData(location);
            // Create a JSON object with username and weather details
            JSONObject json = new JSONObject();
            json.put("weather", weatherForecastDataJson);
            json.put("weatherForecast", weatherForecastDataJson);
            // Set JSON object as attribute in request scope
            request.setAttribute("userData", json);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("errorSearchInvalid.jsp");
        }
    }

    private String getCurrentLocation() throws IOException {
    	String ipServiceUrl = "http://checkip.amazonaws.com";
        URL url = new URL(ipServiceUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String location = reader.readLine();
        reader.close();
        return location;
    }
}
