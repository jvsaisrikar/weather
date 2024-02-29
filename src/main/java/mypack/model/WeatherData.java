package mypack.model;

import org.json.JSONObject;

public class WeatherData {
    private JSONObject weatherDetails;

    public WeatherData() {
        this.weatherDetails = new JSONObject();
    }

    public void setCurrentAndForecastWeather(JSONObject currentAndForecastWeather) {
        this.weatherDetails.put("currentAndForecastWeather", currentAndForecastWeather);
    }

    public JSONObject getWeatherDetails() {
        return weatherDetails;
    }
}
