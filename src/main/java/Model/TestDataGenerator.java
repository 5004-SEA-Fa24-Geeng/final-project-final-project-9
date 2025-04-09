package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {
    private static final Random random = new Random();
    
    // City center coordinates
    private static final class CityLocation {
        String city;
        double latitude;
        double longitude;
        
        CityLocation(String city, double latitude, double longitude) {
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    
    private static final CityLocation[] CITY_LOCATIONS = {
        new CityLocation("Seattle", 47.6062, -122.3321),
        new CityLocation("Bellevue", 47.6101, -122.2015),
        new CityLocation("Redmond", 47.6740, -122.1215),
        new CityLocation("Kirkland", 47.6769, -122.2060),
        new CityLocation("Everett", 47.9789, -122.2021),
        new CityLocation("Tacoma", 47.2529, -122.4443),
        new CityLocation("Renton", 47.4829, -122.2171),
        new CityLocation("Kent", 47.3809, -122.2348),
        new CityLocation("Lynnwood", 47.8279, -122.3055),
        new CityLocation("Bothell", 47.7623, -122.2054)
    };
    
    // Animal types
    private static final String[] ANIMAL_TYPES = {"dog", "cat", "bird", "rabbit", "hamster", "Guinea pig", "hedgehog", "turtle"};
    
    // Dog breeds
    private static final String[] DOG_SPECIES = {"Husky", "Labrador", "Golden Retriever", "German Shepherd", "Poodle", "Bulldog", "Beagle", "Chihuahua"};
    
    // Cat breeds
    private static final String[] CAT_SPECIES = {"Persian", "Siamese", "Maine Coon", "Ragdoll", "British Shorthair", "Sphynx", "Bengal", "Russian Blue"};
    
    // Colors
    private static final String[] COLORS = {"black", "white", "brown", "golden", "gray", "orange", "mixed"};
    
    // Patterns
    private static final String[] PATTERNS = {"solid", "spotted", "striped", "brindle", "merle", "tuxedo"};
    
    // Location descriptions
    private static final String[] LOCATION_DESCRIPTIONS = {
        "Downtown", "Park", "Shopping Mall", "Residential Area", "School", "Hospital",
        "Restaurant", "Bus Station", "Train Station", "Library", "Gym", "Supermarket"
    };
    
    public static List<PostedAnimal> generateTestData(int count) {
        List<PostedAnimal> animals = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            String animalType = ANIMAL_TYPES[random.nextInt(ANIMAL_TYPES.length)];
            String species = animalType.equals("dog") ? DOG_SPECIES[random.nextInt(DOG_SPECIES.length)] :
                           animalType.equals("cat") ? CAT_SPECIES[random.nextInt(CAT_SPECIES.length)] :
                           "Unknown";
            
            CityLocation cityLocation = CITY_LOCATIONS[random.nextInt(CITY_LOCATIONS.length)];
            
            // Generate a random location around the city center
            double latitude = cityLocation.latitude + (random.nextDouble() - 0.5) * 0.1;
            double longitude = cityLocation.longitude + (random.nextDouble() - 0.5) * 0.1;
            
            PostedAnimal animal = new PostedAnimal(
                random.nextDouble() * 1000,  // number
                animalType,
                species,
                random.nextInt(4),  // size (0-3)
                random.nextInt(3),  // gender (0-2)
                PATTERNS[random.nextInt(PATTERNS.length)],
                COLORS[random.nextInt(COLORS.length)],
                random.nextInt(4),  // age (0-3)
                new Integer[]{2024, 3, random.nextInt(28) + 1, random.nextInt(24), random.nextInt(60)},  // witnessedTime
                LOCATION_DESCRIPTIONS[random.nextInt(LOCATION_DESCRIPTIONS.length)],
                cityLocation.city,
                false,  // ifAdded
                "user" + random.nextInt(100),  // witness
                "path/to/image" + random.nextInt(10) + ".jpg",  // image
                "A " + COLORS[random.nextInt(COLORS.length)] + " " + species + " was seen here.",
                latitude,   // latitude
                longitude   // longitude
            );
            
            animals.add(animal);
        }
        
        return animals;
    }
    
    // Calculate distance between two coordinates using Haversine formula
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in kilometers
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }
} 