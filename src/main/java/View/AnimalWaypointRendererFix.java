package View;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointRenderer;

import Model.Animals.IAnimal;

/**
 * An improved animal waypoint renderer that normalizes animal type to uppercase
 * for color mapping, ensuring consistent rendering regardless of case.
 */
public class AnimalWaypointRendererFix implements WaypointRenderer<View.WaypointWithInfo> {
    // Store colors for different animal types
    private final Map<String, Color> typeColors = new HashMap<>();
    
    public AnimalWaypointRendererFix() {
        // Initialize color mapping
        typeColors.put("DOG", new Color(65, 105, 225)); // Blue
        typeColors.put("CAT", new Color(220, 20, 60));  // Red
        typeColors.put("BIRD", new Color(50, 205, 50)); // Green
        typeColors.put("RABBIT", new Color(255, 165, 0)); // Orange
        typeColors.put("HAMSTER", new Color(255, 215, 0)); // Gold
        typeColors.put("HEDGEHOG", new Color(139, 69, 19)); // Brown
        typeColors.put("GOOSE", new Color(0, 191, 255)); // Light blue
        typeColors.put("DUCK", new Color(34, 139, 34)); // Forest green
    }
    
    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer map, View.WaypointWithInfo waypoint) {
        try {
            // Convert geographic coordinates to screen coordinates
            GeoPosition pos = waypoint.getPosition();
            Point2D point = map.getTileFactory().geoToPixel(pos, map.getZoom());
            
            // Note: No need to convert to viewport coordinates, as Graphics2D is already in the correct coordinate system
            int x = (int) point.getX();
            int y = (int) point.getY();
            
            // Get the animal directly from the waypoint
            IAnimal animal = waypoint.getAnimal();
            String animalType = animal.getAnimalType();
            
            // Normalize animal type to uppercase for consistent mapping
            String normalizedType = animalType.toUpperCase();
            
            // Simplified debug output to reduce console clutter
            System.out.println("Painting animal: " + animalType + ", type: " + normalizedType + 
                              ", position: (" + x + ", " + y + ")");
            
            // Determine color based on normalized animal type
            Color dotColor = Color.RED; // Default to red
            if (typeColors.containsKey(normalizedType)) {
                dotColor = typeColors.get(normalizedType);
            } else {
                System.out.println("No color mapping found for: " + normalizedType + ", using default red");
            }
            
            // Increase dot size to make it easier to click
            g.setColor(dotColor);
            Ellipse2D dot = new Ellipse2D.Double(x - 8, y - 8, 16, 16);  // 16x16 pixel size
            g.fill(dot);
            
            // Add black border to enhance visibility
            g.setColor(Color.BLACK);
            g.draw(dot);
        } catch (Exception e) {
            System.err.println("Error drawing waypoint: " + e.getMessage());
        }
    }
}
