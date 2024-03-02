package mypack.controller;

import mypack.service.WeatherService;
import mypack.service.LocationService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final WeatherService weatherService = new WeatherService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("locationValue");

        try {
            if ("current".equals(location)) {
                location = LocationService.getCurrentLocation();
            }
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            JSONObject weatherForecastDataJson = weatherService.getWeatherForecastData(location);
            // Create a JSON object with username and weather details
            JSONObject json = new JSONObject();
            json.put("weather", weatherForecastDataJson);
            json.put("weatherForecast", weatherForecastDataJson);
            // Set JSON object as attribute in request scope
            request.setAttribute("userData", json);
            request.setAttribute("username", username);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("errorSearchInvalid.jsp");
        }
    }
}
