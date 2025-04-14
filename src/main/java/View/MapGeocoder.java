package View;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.AnimalList;
import Model.Animals.IAnimal;
import Model.IAnimalList;

public class MapGeocoder {

    /** The API to look up addresses. */
    private static final String NOMINATIM_API = "https://nominatim.openstreetmap.org/search";

    /** The name of this application. */
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


    /**
     * Get the coordinates of a given address.
     * @param address given address
     * @return a new GeoLocation object
     * @throws IOException if fails
     */
    public static GeoLocation getCoordinates(String address) throws IOException {
        System.out.println("Attempting to geocode address: " + address);
        
        // Add Washington state to the address if not already present
        String searchAddress = address;
        if (!address.toLowerCase().contains("washington")) {
            searchAddress = address + ", Washington, USA";
        }
        
        // URL encode the address
        String encodedAddress = URLEncoder.encode(searchAddress, StandardCharsets.UTF_8);

        // Create the URL for the Nominatim API request
        String requestUrl = NOMINATIM_API +
                "?q=" + encodedAddress +
                "&format=json" +
                "&addressdetails=1" +
                "&limit=1" +
                "&countrycodes=us"; // Restrict to US results

        System.out.println("Request URL: " + requestUrl);

        // Create the HTTP connection
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method
        connection.setRequestMethod("GET");

        // Set User-Agent (required by Nominatim usage policy)
        connection.setRequestProperty("User-Agent", USER_AGENT);

        // Get the response
        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response: " + response.toString());

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());

            if (!jsonArray.isEmpty()) {
                JSONObject result = jsonArray.getJSONObject(0);

                double latitude = result.getDouble("lat");
                double longitude = result.getDouble("lon");
                String displayName = result.getString("display_name");

                // Verify the result is in Washington state
                if (displayName.toLowerCase().contains("washington")) {
                    System.out.println("Successfully geocoded to: " + latitude + ", " + longitude);
                    return new GeoLocation(latitude, longitude, displayName);
                } else {
                    System.out.println("Result not in Washington state: " + displayName);
                    return null;
                }
            } else {
                System.out.println("No results found for address");
            }
        } else {
            System.out.println("HTTP request failed with response code: " + responseCode);
        }

        return null;
    }


    /**
     * Helper class to store location information.
     */
    public static class GeoLocation {
        /** Latitude. */
        private final double latitude;

        /** Longitude. */
        private final double longitude;

        /** Diaplay name. */
        private final String displayName;


        /**
         * Constructor of GeoLocation.
         * @param latitude latitude of the address
         * @param longitude longitude of the address
         * @param displayName display name of the address
         */
        public GeoLocation(double latitude, double longitude, String displayName) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.displayName = displayName;
        }


        /**
         * Get latitude.
         * @return latitude
         */
        public double getLatitude() {
            return latitude;
        }


        /**
         * Get longitude.
         * @return longitude
         */
        public double getLongitude() {
            return longitude;
        }


        /**
         * Get display name.
         * @return displayName
         */
        public String getDisplayName() {
            return displayName;
        }
    }
}
