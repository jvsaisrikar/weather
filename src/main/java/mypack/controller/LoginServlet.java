package mypack.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypack.model.UserModel;
import mypack.model.WeatherData;
import mypack.service.WeatherService;
import mypack.service.UserService;
import org.json.JSONObject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String username = email.split("@")[0];

        UserService userService = new UserService();
        UserModel user = userService.findUserByEmailAndPassword(email, password);

        if (user != null) {
            WeatherService weatherService = new WeatherService();
            JSONObject weatherDataCurrentAndForecast = weatherService.getWeatherForecastData(user.getLocation());

            // Create and set data in WeatherData object
            WeatherData weatherData = new WeatherData();
            weatherData.setCurrentWeather(weatherDataCurrentAndForecast);
            weatherData.setForecastWeather(weatherDataCurrentAndForecast);

            // Set JSON object as attribute in request scope
            request.setAttribute("userData", weatherData.getWeatherDetails());
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            response.sendRedirect("errorLogin.jsp");
        }
    }
}
