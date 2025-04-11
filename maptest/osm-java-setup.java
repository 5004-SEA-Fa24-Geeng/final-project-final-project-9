// First, add JMapViewer to your project
// If using Maven, add this dependency to your pom.xml:
/*
<dependency>
    <groupId>org.openstreetmap.jmapviewer</groupId>
    <artifactId>jmapviewer</artifactId>
    <version>2.13</version>
</dependency>
*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public class LocationMapViewer {
    private JFrame frame;
    private JMapViewer mapViewer;
    private List<Location> locations;

    public LocationMapViewer(List<Location> locations) {
        this.locations = locations;
        initializeUI();
    }

    private void initializeUI() {
        // Create a new JFrame
        frame = new JFrame("Location Map Viewer");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create the map viewer
        mapViewer = new JMapViewer();
        
        // Create a panel for buttons
        JPanel panel = new JPanel();
        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");
        JButton showAllButton = new JButton("Show All Locations");
        
        // Add action listeners
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.setZoom(mapViewer.getZoom() + 1);
            }
        });
        
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.setZoom(mapViewer.getZoom() - 1);
            }
        });
        
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLocations();
            }
        });
        
        // Add buttons to panel
        panel.add(zoomInButton);
        panel.add(zoomOutButton);
        panel.add(showAllButton);
        
        // Add components to frame
        frame.add(mapViewer, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
    }
    
    public void display() {
        frame.setVisible(true);
        displayLocations();
    }
    
    public void displayLocations() {
        // Clear existing markers
        mapViewer.removeAllMapMarkers();
        
        List<Coordinate> coordinates = new ArrayList<>();
        
        // Add a marker for each location
        for (Location location : locations) {
            try {
                // Parse the location string to get coordinates
                Coordinate coord = parseLocation(location.getLocation());
                
                if (coord != null) {
                    // Add marker to the map
                    MapMarker marker = new MapMarkerDot(location.getName(), coord);
                    mapViewer.addMapMarker(marker);
                    coordinates.add(coord);
                }
            } catch (Exception e) {
                System.err.println("Error parsing location: " + location.getLocation());
                e.printStackTrace();
            }
        }
        
        // If we have at least one valid coordinate, center the map
        if (!coordinates.isEmpty()) {
            // Calculate center of all coordinates
            double latSum = 0, lonSum = 0;
            for (Coordinate coord : coordinates) {
                latSum += coord.getLat();
                lonSum += coord.getLon();
            }
            double centerLat = latSum / coordinates.size();
            double centerLon = lonSum / coordinates.size();
            
            mapViewer.setDisplayPosition(new Coordinate(centerLat, centerLon), 8);
        }
    }
    
    // This method parses your location string format into coordinates
    private Coordinate parseLocation(String locationStr) {
        // This implementation depends on your location string format
        // Example implementation for "latitude,longitude" format:
        try {
            String[] parts = locationStr.split(",");
            if (parts.length >= 2) {
                double lat = Double.parseDouble(parts[0].trim());
                double lon = Double.parseDouble(parts[1].trim());
                return new Coordinate(lat, lon);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid location format: " + locationStr);
        }
        return null;
    }
}
