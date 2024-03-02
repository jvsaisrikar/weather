package mypack.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherService {

    public JSONObject getWeatherForecastData(String location) throws IOException {
        String baseUrl = "https://api.weatherapi.com/v1/forecast.json";
        return fetchWeatherData(baseUrl, location);
    }

    private JSONObject fetchWeatherData(String baseUrl, String location) throws IOException {
        // Access the WEATHER_API_KEY from environment variable
        String apiKey = System.getenv("WEATHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API key not found in environment variables");
        }
        location = location.replace(" ", "%20");
        String requestUrl = baseUrl + "?key=" + apiKey + "&q=" + location + "&days=14" + "&alerts=yes";
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonResponse = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonResponse.append(line);
        }
        reader.close();

        return new JSONObject(jsonResponse.toString());
    }
}
