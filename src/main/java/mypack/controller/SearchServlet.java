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
        String dateForecast = request.getParameter("dateForecast");
        String location = request.getParameter("locationValue");
        if ("current".equals(location)) {
            location = getCurrentLocation();
        }

        JSONObject weatherForecastDataDateJson = null;
        JSONObject weatherForecastDataJson = null;
        // Check if a date is provided
        //!dateForecast.trim().isEmpty() checking for white space
        if (dateForecast != null && !dateForecast.trim().isEmpty()) {
            // Call the method with date if date is provided
            System.out.println("weatherForecastDataDateJson date if cond");
            weatherForecastDataDateJson = weatherService.getWeatherForecastData(location, dateForecast);
        } else {
            // Call the method without date if no date is provided
            weatherForecastDataJson = weatherService.getWeatherForecastData(location);
        }

        // Create a JSON object with weather details
        JSONObject json = new JSONObject();
        json.put("weatherForecastDataDateJson", weatherForecastDataDateJson);
        json.put("currentAndForecastWeather", weatherForecastDataJson);
        //TODO remove thos
        System.out.println(json);
        // Set JSON object as attribute in request scope
        request.setAttribute("userData", json);
        request.getRequestDispatcher("home.jsp").forward(request, response);
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
