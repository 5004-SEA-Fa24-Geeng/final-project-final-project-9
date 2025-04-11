import java.util.ArrayList;
import java.util.List;

public class MapApplication {
    public static void main(String[] args) {
        // Create some sample locations
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Eiffel Tower", "48.8584,2.2945"));
        locations.add(new Location("Statue of Liberty", "40.6892,-74.0445"));
        locations.add(new Location("Sydney Opera House", "-33.8568,151.2153"));
        locations.add(new Location("Taj Mahal", "27.1751,78.0421"));
        locations.add(new Location("Great Wall of China", "40.4319,116.5704"));
        
        // Create and display the map viewer
        LocationMapViewer mapViewer = new LocationMapViewer(locations);
        mapViewer.display();
    }
}
