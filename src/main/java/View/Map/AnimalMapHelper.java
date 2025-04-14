package View.Map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import Model.Animals.IAnimal;

/**
 * Helper class for displaying animal locations on the map and handling click events
 */
public class AnimalMapHelper {
    private final JXMapViewer mapViewer;
    private final Consumer<IAnimal> showAnimalDetailsFunction;
    private Map<GeoPosition, IAnimal> positionToAnimal;
    
    /**
     * Create AnimalMapHelper instance
     * @param mapViewer Map viewer component
     * @param showAnimalDetailsFunction Function to display animal details
     */
    public AnimalMapHelper(JXMapViewer mapViewer, Consumer<IAnimal> showAnimalDetailsFunction) {
        this.mapViewer = mapViewer;
        this.showAnimalDetailsFunction = showAnimalDetailsFunction;
        this.positionToAnimal = new HashMap<>();
    }
    
    /**
     * Display list of animals on the map
     * @param animals List of animals to display
     */
    public void displayAnimalsOnMap(List<IAnimal> animals) {
        // Clear old mappings
        positionToAnimal.clear();
        
        // Create new waypoint set
        Set<Waypoint> waypoints = new HashSet<>();
        
        // Add waypoint for each animal
        for (IAnimal animal : animals) {
            try {
                // Get location information
                String address = animal.getAddress();
                String area = animal.getArea();
                
                // Format full address, ensuring it includes city and state information
                GeoPosition position = formatAddressAndGetPosition(address, area);
                
                // Add waypoint and mapping
                waypoints.add(new DefaultWaypoint(position));
                positionToAnimal.put(position, animal);
            } catch (Exception e) {
                System.err.println("Error displaying animal on map: " + e.getMessage());
            }
        }
        
        // If there are waypoints, set map center and zoom level
        if (!waypoints.isEmpty()) {
            Waypoint firstWaypoint = waypoints.iterator().next();
            mapViewer.setAddressLocation(firstWaypoint.getPosition());
            mapViewer.setZoom(14);
        }
        
        // Set up waypoint painter
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);
        
        // Set up click listener
        setupClickListener();
    }
    
    /**
     * Set up map click listener
     */
    private void setupClickListener() {
        // Remove existing click listeners
        for (java.awt.event.MouseListener listener : mapViewer.getMouseListeners()) {
            if (listener != null && !(listener instanceof PanMouseInputListener)) {
                mapViewer.removeMouseListener(listener);
            }
        }
        
        // Add new click listener
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D clickPoint = e.getPoint();
                handleMapClick(clickPoint);
            }
        });
    }
    
    /**
     * Handle map click event
     * @param clickPoint Click position
     */
    private void handleMapClick(Point2D clickPoint) {
        // Find the closest waypoint
        GeoPosition closestPosition = null;
        double closestDistance = Double.MAX_VALUE;
        
        for (GeoPosition position : positionToAnimal.keySet()) {
            Point2D waypointPoint = mapViewer.getTileFactory().geoToPixel(position, mapViewer.getZoom());
            double distance = clickPoint.distance(waypointPoint);
            
            if (distance < 15 && distance < closestDistance) {
                closestPosition = position;
                closestDistance = distance;
            }
        }
        
        // Display animal details
        if (closestPosition != null) {
            IAnimal animal = positionToAnimal.get(closestPosition);
            if (animal != null) {
                // Use functional interface callback
                showAnimalDetailsFunction.accept(animal);
            }
        }
    }
    
    /**
     * Format address and get geographic position
     * @param address Address
     * @param area Area
     * @return Geographic position
     */
    private GeoPosition formatAddressAndGetPosition(String address, String area) {
        // If address is empty, use area information
        if (address == null || address.isEmpty()) {
            return getPositionFromArea(area);
        }
        
        // Format full address
        String fullAddress = address;
        if (!fullAddress.toLowerCase().contains(area.toLowerCase())) {
            fullAddress += ", " + area;
        }
        if (!fullAddress.toLowerCase().contains("washington")) {
            fullAddress += ", Washington State, USA";
        }
        
        // Get geographic position
        GeoPosition position = getPositionFromAddress(fullAddress);
        if (position != null) {
            return position;
        }
        
        // If address parsing fails, use area information
        return getPositionFromArea(area);
    }
    
    /**
     * Get geographic position from address
     * @param address Address
     * @return Geographic position
     */
    private GeoPosition getPositionFromAddress(String address) {
        // Match common places in Seattle
        if (address.toLowerCase().contains("space needle") || address.toLowerCase().contains("seattle center")) {
            return new GeoPosition(47.6205, -122.3493);
        } else if (address.toLowerCase().contains("pike place") || address.toLowerCase().contains("pike market")) {
            return new GeoPosition(47.6097, -122.3422);
        } else if (address.toLowerCase().contains("university of washington") || address.toLowerCase().contains("uw")) {
            return new GeoPosition(47.6553, -122.3035);
        } else if (address.toLowerCase().contains("downtown seattle")) {
            return new GeoPosition(47.6050, -122.3344);
        } else if (address.toLowerCase().contains("capitol hill")) {
            return new GeoPosition(47.6253, -122.3222);
        } else if (address.toLowerCase().contains("ballard")) {
            return new GeoPosition(47.6677, -122.3847);
        } else if (address.toLowerCase().contains("fremont")) {
            return new GeoPosition(47.6510, -122.3473);
        } else if (address.toLowerCase().contains("green lake")) {
            return new GeoPosition(47.6795, -122.3271);
        } else if (address.toLowerCase().contains("south lake union")) {
            return new GeoPosition(47.6267, -122.3366);
        } else if (address.toLowerCase().contains("queen anne")) {
            return new GeoPosition(47.6370, -122.3570);
        } else if (address.toLowerCase().contains("georgetown")) {
            return new GeoPosition(47.5479, -122.3230);
        } else if (address.toLowerCase().contains("west seattle")) {
            return new GeoPosition(47.5667, -122.3875);
        } else if (address.toLowerCase().contains("rainier")) {
            return new GeoPosition(47.5685, -122.3200);
        } else if (address.toLowerCase().contains("u district") || address.toLowerCase().contains("university district")) {
            return new GeoPosition(47.6614, -122.3127);
        }
        
        // Try matching city names
        for (String city : new String[]{"seattle", "bellevue", "redmond", "kirkland", "everett", 
                           "tacoma", "renton", "kent", "lynnwood", "bothell"}) {
            if (address.toLowerCase().contains(city)) {
                return getPositionFromArea(city.toUpperCase());
            }
        }
        
        // Default return to Seattle center
        return new GeoPosition(47.6062, -122.3321);
    }
    
    /**
     * Get geographic position from area name
     * @param area Area name
     * @return Geographic position
     */
    private GeoPosition getPositionFromArea(String area) {
        switch (area) {
            case "SEATTLE":
                return new GeoPosition(47.6062, -122.3321);
            case "BELLEVUE":
                return new GeoPosition(47.6101, -122.2015);
            case "REDMOND":
                return new GeoPosition(47.6740, -122.1215);
            case "KIRKLAND":
                return new GeoPosition(47.6769, -122.2060);
            case "EVERETT":
                return new GeoPosition(47.9789, -122.2021);
            case "TACOMA":
                return new GeoPosition(47.2529, -122.4443);
            case "RENTON":
                return new GeoPosition(47.4829, -122.2171);
            case "KENT":
                return new GeoPosition(47.3809, -122.2348);
            case "LYNNWOOD":
                return new GeoPosition(47.8279, -122.3053);
            case "BOTHELL":
                return new GeoPosition(47.7601, -122.2054);
            default:
                return new GeoPosition(47.6062, -122.3321); // Default Seattle
        }
    }
} 