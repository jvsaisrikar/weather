package mypack.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mypack.model.UserModel;
import mypack.service.WeatherService;
import mypack.service.UserService;
import org.json.JSONObject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserService userService = new UserService();
        UserModel user = userService.findUserByEmailAndPassword(email, password);

        if (user != null) {
            WeatherService weatherService = new WeatherService();
            JSONObject weatherForecastData = weatherService.getWeatherForecastData(user.getLocation());
            // Create a JSON object with username and weather details
            JSONObject json = new JSONObject();
            json.put("weather", weatherForecastData);
            json.put("weatherForecast", weatherForecastData);
            // Set JSON object as attribute in request scope
            request.setAttribute("userData", json);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            response.sendRedirect("errorLogin.jsp");
        }
    }
}
