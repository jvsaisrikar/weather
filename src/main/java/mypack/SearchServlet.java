package mypack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.net.InetAddress;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        // Dummy authentication logic (replace with your database authentication logic)
        if (true) {
            // Dummy location (replace with database retrieval logic)
            String location = (String) request.getParameter("locationValue"); 
            if ("current".equals(location)) {
            	String ipServiceUrl = "http://checkip.amazonaws.com";
                URL url = new URL(ipServiceUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                location = reader.readLine();
                reader.close();

            }
            // Fetch weather data based on the location
            JSONObject weatherData = getWeatherData(location);
            JSONObject weatherForcastData = weatherForcastData(location);
            // Create a JSON object with user name and weather details
            JSONObject json = new JSONObject();
            json.put("weather", weatherData);
            json.put("weatherForcast", weatherForcastData);

            // Set JSON object as attribute in request scope
            request.setAttribute("userData", json);

            // Redirect to home.jsp
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } 
    }
    
    
    private JSONObject weatherForcastData(String location) throws IOException {
        // Weather API URL with location parameter
        final String API_KEY = "2f61d4b600974f67a95235409243001";
        String locationModified = location.replace(" ", "%20");
        final String BASE_URL = "https://api.weatherapi.com/v1/forecast.json?q=" +locationModified + "&days=3" + "&key=" + API_KEY;

        // Open connection to the API URL
        URI uri = URI.create(BASE_URL);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get response code
        int responseCode = connection.getResponseCode();

        // If response code is OK (200), read the JSON response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            // Read response line by line and append to StringBuilder
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }

            // Close the reader
            reader.close();
            System.out.println(jsonResponse);
            
            // Parse the JSON response string to JSONObject
            JSONObject jsonObject = new JSONObject(jsonResponse.toString());
            
            return jsonObject;
        }

        return null;
    }


    private JSONObject getWeatherData(String location) throws IOException {
        // Weather API URL with location parameter
        final String API_KEY = "2f61d4b600974f67a95235409243001";
        String locationModified = location.replace(" ", "%20");
        final String BASE_URL = "https://api.weatherapi.com/v1/current.json" + "?q=" + locationModified + "&key=" + API_KEY;

        // Open connection to the API URL
        URI uri = URI.create(BASE_URL);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get response code
        int responseCode = connection.getResponseCode();

        // If response code is OK (200), read the JSON response
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            // Read response line by line and append to StringBuilder
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }

            // Close the reader
            reader.close();
            System.out.println(jsonResponse);
            // Parse the JSON response string to JSONObject
            JSONObject jsonObject = new JSONObject(jsonResponse.toString());
            
            return jsonObject;
        }

        return null;
    }

}
    

