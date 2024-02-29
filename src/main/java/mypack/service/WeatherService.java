package mypack.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherService {
    private final String API_KEY = "2f61d4b600974f67a95235409243001";

    public JSONObject getWeatherForecastData(String location) throws IOException {
        String baseUrl = "https://api.weatherapi.com/v1/forecast.json";
        return fetchWeatherData(baseUrl, location);
    }

    // Overloaded method to get weather data with an additional date parameter
    public JSONObject getWeatherForecastData(String location, String date) throws IOException {
        String baseUrl = "https://api.weatherapi.com/v1/forecast.json";
        return fetchWeatherData(baseUrl, location, date);
    }

    private JSONObject fetchWeatherData(String baseUrl, String location) throws IOException {
        // Call the overloaded method with an empty date
        return fetchWeatherData(baseUrl, location, "");
    }

    // Overloaded fetchWeatherData method that also accepts a date
    private JSONObject fetchWeatherData(String baseUrl, String location, String date) throws IOException {
        location = location.replace(" ", "%20");
        String requestUrl = baseUrl + "?key=" + API_KEY + "&q=" + location + "&days=3";

        if (!date.isEmpty()) {
            requestUrl += "&dt=" + date;
        }

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
        //TODO remove this
        System.out.println(jsonResponse.toString());
        return new JSONObject(jsonResponse.toString());
    }
}
