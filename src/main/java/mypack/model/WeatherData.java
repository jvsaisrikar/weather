package mypack.model;

import org.json.JSONObject;

public class WeatherData {
    private final JSONObject weatherDetails;

    public WeatherData() {
        this.weatherDetails = new JSONObject();
    }

    public void setCurrentWeather(JSONObject currentWeather) {
        this.weatherDetails.put("weather", currentWeather);
    }

    public void setForecastWeather(JSONObject forecastWeather) {
        this.weatherDetails.put("weatherForecast", forecastWeather);
    }

    public JSONObject getWeatherDetails() {
        return weatherDetails;
    }
}
