package view;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for the MapGeocoder class.
 */
public class MapGeocoderTest {

    /**
     * Test the GeoLocation class constructor and getters.
     */
    @Test
    public void testGeoLocation() {
        // Create a test GeoLocation
        double testLatitude = 47.608013;
        double testLongitude = -122.335167;
        String testDisplayName = "Seattle, King County, Washington, USA";
        
        MapGeocoder.GeoLocation location = new MapGeocoder.GeoLocation(
                testLatitude, testLongitude, testDisplayName);
        
        // Test getters
        assertEquals(testLatitude, location.getLatitude(), 0.000001, "Latitude should match");
        assertEquals(testLongitude, location.getLongitude(), 0.000001, "Longitude should match");
        assertEquals(testDisplayName, location.getDisplayName(), "Display name should match");
    }
    
    /**
     * Test the getCoordinates method with a valid Washington address.
     * This is an integration test that makes an actual API call, so it's marked with disabled.
     * Enable this test when you need to verify the geocoding functionality.
     */
    @Test
    public void testGetCoordinatesWithValidWashingtonAddress() throws IOException {
        // Use a well-known address in Washington
        String address = "Space Needle, Seattle, WA";
        
        // Try to get coordinates
        MapGeocoder.GeoLocation location = MapGeocoder.getCoordinates(address);
        
        // The location should not be null for a valid address
        assertNotNull(location, "Location should not be null for a valid address");
        
        // Check that the latitude and longitude are in the approximate area of Seattle
        // Space Needle coordinates: approx 47.6205, -122.3493
        assertTrue(location.getLatitude() > 47.0 && location.getLatitude() < 48.0, 
                "Latitude should be approximately in Seattle area");
        assertTrue(location.getLongitude() > -123.0 && location.getLongitude() < -122.0, 
                "Longitude should be approximately in Seattle area");
        
        // Check that the display name contains the expected elements
        String displayName = location.getDisplayName().toLowerCase();
        assertTrue(displayName.contains("seattle") && displayName.contains("washington"), 
                "Display name should include both Seattle and Washington");
    }
    
    /**
     * Test the getCoordinates method with an invalid address.
     * This is an integration test that makes an actual API call, so it's marked with disabled.
     * Enable this test when you need to verify the geocoding functionality.
     */
    @Test
    public void testGetCoordinatesWithInvalidAddress() throws IOException {
        // Use a non-existent address
        String address = "XYZXYZXYZ, Nonexistent Place, ABC123";
        
        // Try to get coordinates
        MapGeocoder.GeoLocation location = MapGeocoder.getCoordinates(address);
        
        // The location should be null for an invalid address
        assertNull(location, "Location should be null for an invalid address");
    }
    
    /**
     * Test the getCoordinates method with a non-Washington address.
     * This is an integration test that makes an actual API call, so it's marked with disabled.
     * Enable this test when you need to verify the geocoding functionality.
     */
    @Test
    public void testGetCoordinatesWithNonWashingtonAddress() throws IOException {
        // Use an address in New York, which should be filtered out
        String address = "Empire State Building, New York, NY";
        
        // Try to get coordinates
        MapGeocoder.GeoLocation location = MapGeocoder.getCoordinates(address);
        
        // The location should be null because it's not in Washington
        assertNull(location, "Location should be null for a non-Washington address");
    }
} 