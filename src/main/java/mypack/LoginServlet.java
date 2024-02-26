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
import javax.servlet.http.HttpSession;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.json.JSONObject;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("weatherApp");
        MongoCollection<Document> users = database.getCollection("User");

        // Query for a user with the given email and password
        Document user = users.find(Filters.and(
                Filters.eq("email", email),
                Filters.eq("password", password)
        )).first();


        if (user != null) {
            //show user's stored location in homepage when user logins
            String location = user.getString("location");
            //if location in user db is empty display current location
            if (location.isEmpty()) {
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
        } else {
        	System.out.println("Error in fetching in username");
            // Authentication failed
            // Redirect back to the login page with an error message or display error message on the same page
            //TODO proper error handling
            response.sendRedirect("errorLogin.jsp");
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
    
