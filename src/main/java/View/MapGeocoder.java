package View;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import Model.AnimalList;
import Model.Animals.IAnimal;
import Model.IAnimalList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapGeocoder {

    private static final String NOMINATIM_API = "https://nominatim.openstreetmap.org/search";
    private static final String USER_AGENT = "Stray Pet Spotter";

    /**
     * Test getCoordinates() method.
     * @param args input
     */
    public static void main(String[] args) {
        IAnimalList animals = new AnimalList();
        try {
            GeoLocation location;
            String address;
            // Get coordinates
            for (IAnimal animal : animals.getAnimals()) {
                address = animal.getAddress() + "," + animal.getArea();
                location = getCoordinates(address);
                if (location == null) {
                    System.out.println("Animal Number: " + animal.getNumber() + " need to change address.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GeoLocation getCoordinates(String address) throws IOException {
        // URL encode the address
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

        // Create the URL for the Nominatim API request
        String requestUrl = NOMINATIM_API +
                "?q=" + encodedAddress +
                "&format=json" +
                "&addressdetails=1" +
                "&limit=1";

        // Create the HTTP connection
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method
        connection.setRequestMethod("GET");

        // Set User-Agent (required by Nominatim usage policy)
        connection.setRequestProperty("User-Agent", USER_AGENT);

        // Get the response
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());

            if (jsonArray.length() > 0) {
                JSONObject result = jsonArray.getJSONObject(0);

                double latitude = result.getDouble("lat");
                double longitude = result.getDouble("lon");
                String displayName = result.getString("display_name");

                return new GeoLocation(latitude, longitude, displayName);
            }
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }

        return null;
    }

    // Helper class to store location information
    public static class GeoLocation {
        private final double latitude;
        private final double longitude;
        private final String displayName;

        public GeoLocation(double latitude, double longitude, String displayName) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.displayName = displayName;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
