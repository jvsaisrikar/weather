package mypack.model;

import org.json.JSONObject;

public class WeatherData {
    private JSONObject currentWeather;
    private JSONObject forecastWeather;

    // Constructor
    public WeatherData(JSONObject currentWeather, JSONObject forecastWeather) {
        this.currentWeather = currentWeather;
        this.forecastWeather = forecastWeather;
    }

    // Getters and setters
    public JSONObject getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(JSONObject currentWeather) {
        this.currentWeather = currentWeather;
    }

    public JSONObject getForecastWeather() {
        return forecastWeather;
    }

    public void setForecastWeather(JSONObject forecastWeather) {
        this.forecastWeather = forecastWeather;
    }
}
