package mypack.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationService {
    public static String getCurrentLocation() throws IOException {
        String ipServiceUrl = "http://checkip.amazonaws.com";
        URL url = new URL(ipServiceUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String location = reader.readLine();
        reader.close();
        return location;
    }
}
