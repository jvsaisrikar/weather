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

    private JSONObject fetchWeatherData(String baseUrl, String location) throws IOException {
        location = location.replace(" ", "%20");
        //3-day forecast hardcoded for UI display
        String requestUrl = baseUrl + "?key=" + API_KEY + "&q=" + location + "&days=3";
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
