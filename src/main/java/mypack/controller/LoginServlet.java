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
import mypack.service.LocationService;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            UserService userService = new UserService();
            UserModel user = userService.findUserByEmail(email);

            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                if (user.getLocation().isEmpty()) {
                    //if empty get current location and set it
                    user.setLocation(LocationService.getCurrentLocation());
                }

                WeatherService weatherService = new WeatherService();
                JSONObject weatherDataCurrentAndForecast = weatherService.getWeatherForecastData(user.getLocation());

                // Create and set data in WeatherData object
                WeatherData weatherData = new WeatherData();
                weatherData.setCurrentWeather(weatherDataCurrentAndForecast);
                weatherData.setForecastWeather(weatherDataCurrentAndForecast);

                // Set JSON object as attribute in request scope
                request.setAttribute("userData", weatherData.getWeatherDetails());
                //split if @ exists and assign to email
                String emailModified = null;
                if (email != null && email.contains("@")) {
                    emailModified = email.split("@")[0];
                }
                HttpSession session = request.getSession();
                session.setAttribute("username", emailModified);
                request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                response.sendRedirect("errorLogin.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("errorLogin.jsp");
        }
    }
}
