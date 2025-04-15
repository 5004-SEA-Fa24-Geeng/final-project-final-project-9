package Model;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Animals.Animal;
import Model.Animals.IAnimal;

/**
 * Tests for the AnimalFilter class.
 */
public class AnimalFilterTest {
    private AnimalFilter filter;
    private AnimalList animalList;
    private IAnimal dog1;
    private IAnimal dog2;
    private IAnimal cat1;
    private IAnimal cat2;

    @BeforeEach
    public void setUp() throws Exception {
        // Create test animals with different attributes
        dog1 = new Animal(
                "DOG",            // type
                "LABRADOR",       // species
                "LARGE",          // size
                "MALE",           // gender
                "SOLID",          // pattern
                "BLACK",          // color
                "ADULT",          // age
                "123 Main St",    // address
                "Boston",         // area
                "12:30 PM",       // time
                "01/01/2023",     // seenDate (earlier date)
                "Friendly dog",   // description
                "Near the park",  // locDesc
                "12345",          // number
                "dog1.jpg"        // image
        );
        
        dog2 = new Animal(
                "DOG",            // type
                "GOLDEN RETRIEVER", // species
                "MEDIUM",         // size
                "FEMALE",         // gender
                "SOLID",          // pattern
                "GOLDEN",         // color
                "YOUNG",          // age
                "456 Oak St",     // address
                "Cambridge",      // area
                "3:45 PM",        // time
                "02/15/2023",     // seenDate (middle date)
                "Playful dog",    // description
                "In a yard",      // locDesc
                "12346",          // number
                "dog2.jpg"        // image
        );
        
        cat1 = new Animal(
                "CAT",            // type
                "SIAMESE",        // species
                "SMALL",          // size
                "FEMALE",         // gender
                "STRIPED",        // pattern
                "GRAY",           // color
                "YOUNG",          // age
                "789 Pine St",    // address
                "Somerville",     // area
                "9:15 AM",        // time
                "01/20/2023",     // seenDate
                "Shy cat",        // description
                "Under a porch",  // locDesc
                "12347",          // number
                "cat1.jpg"        // image
        );
        
        cat2 = new Animal(
                "CAT",            // type
                "TABBY",          // species
                "MEDIUM",         // size
                "MALE",           // gender
                "BICOLOR",        // pattern
                "ORANGE",         // color
                "ADULT",          // age
                "101 Elm St",     // address
                "Malden",         // area
                "5:30 PM",        // time
                "03/10/2023",     // seenDate (latest date)
                "Friendly cat",   // description
                "In a garden",    // locDesc
                "12348",          // number
                "cat2.jpg"        // image
        );
        
        // Create a test AnimalList with our animals
        animalList = createTestAnimalList(dog1, dog2, cat1, cat2);
        
        // Create a filter with our animal list
        filter = new AnimalFilter(animalList);
    }
    
    /**
     * Helper method to create a test AnimalList with the specified animals.
     * This uses reflection to set the animalList field directly.
     * 
     * @param animals The animals to include in the list
     * @return An AnimalList containing the specified animals
     * @throws Exception If reflection fails
     */
    private AnimalList createTestAnimalList(IAnimal... animals) throws Exception {
        AnimalList list = new AnimalList() {
            // Override constructor to avoid loading from CSV
            {
                // Empty initializer
            }
            
            // Override write to avoid writing to the file
            @Override
            public void write() {
                // Do nothing
            }
        };
        
        // Use reflection to set the animalList field directly
        Field animalListField = AnimalList.class.getDeclaredField("animalList");
        animalListField.setAccessible(true);
        
        List<IAnimal> testList = new ArrayList<>();
        for (IAnimal animal : animals) {
            testList.add(animal);
        }
        
        animalListField.set(list, testList);
        return list;
    }

    @Test
    public void testConstructor() {
        // Test that filter initializes with all animals from the list
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        assertEquals(4, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
        assertTrue(filteredAnimals.contains(dog2));
        assertTrue(filteredAnimals.contains(cat1));
        assertTrue(filteredAnimals.contains(cat2));
    }

    @Test
    public void testFilterByType() {
        // Test filtering by animal type "DOG"
        filter.filter("TYPE", "DOG");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should only contain dogs
        assertEquals(2, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
        assertTrue(filteredAnimals.contains(dog2));
        assertFalse(filteredAnimals.contains(cat1));
        assertFalse(filteredAnimals.contains(cat2));
    }
    
    @Test
    public void testFilterBySpecies() {
        // Test filtering by species "TABBY"
        filter.filter("SPECIES", "TABBY");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should only contain the tabby cat
        assertEquals(1, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(cat2));
    }
    
    @Test
    public void testFilterBySize() {
        // Test filtering by size "MEDIUM"
        filter.filter("SIZE", "MEDIUM");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain animals with MEDIUM size
        assertEquals(2, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog2));
        assertTrue(filteredAnimals.contains(cat2));
    }
    
    @Test
    public void testFilterByGender() {
        // Test filtering by gender "FEMALE"
        filter.filter("GENDER", "FEMALE");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain female animals
        assertEquals(2, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog2));
        assertTrue(filteredAnimals.contains(cat1));
    }
    
    @Test
    public void testFilterByPattern() {
        // Test filtering by pattern "SOLID"
        filter.filter("PATTERN", "SOLID");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain animals with SOLID pattern
        assertEquals(2, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
        assertTrue(filteredAnimals.contains(dog2));
    }
    
    @Test
    public void testFilterByColor() {
        // Test filtering by color "BLACK"
        filter.filter("COLOR", "BLACK");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain animals with BLACK color
        assertEquals(1, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
    }
    
    @Test
    public void testFilterByAge() {
        // Test filtering by age "ADULT"
        filter.filter("AGE", "ADULT");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain ADULT animals
        assertEquals(2, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
        assertTrue(filteredAnimals.contains(cat2));
    }
    
    @Test
    public void testFilterByArea() {
        // Test filtering by area "Boston"
        filter.filter("AREA", "Boston");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain animals from Boston
        assertEquals(1, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
    }
    
    @Test
    public void testFilterBySeenDate() throws Exception {
        // This test is a bit tricky because the filter depends on current time
        // Let's test the "within 1 month" filter using the relative time difference
        
        // Get current time
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        // Create a date within the last month from current time
        // Note: In a real test you might want to mock the system time
        String dateWithinOneMonth = sdf.format(new Date(currentTimeMillis - (25L * 24 * 60 * 60 * 1000)));
        
        // Create a new animal with this date
        IAnimal recentAnimal = new Animal(
                "DOG", "PUG", "SMALL", "MALE", "SOLID", "BROWN", "YOUNG",
                "999 Recent St", "Boston", "10:00 AM", dateWithinOneMonth,
                "Recent sighting", "Near highway", "99999", "recent.jpg"
        );
        
        // Create a new AnimalList with all animals including the recent one
        animalList = createTestAnimalList(dog1, dog2, cat1, cat2, recentAnimal);
        filter = new AnimalFilter(animalList);
        
        // Filter for animals seen within 1 month
        filter.filter("SEENDATE", "within 1 month");
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should contain the recent animal
        assertTrue(filteredAnimals.contains(recentAnimal));
    }
    
    @Test
    public void testSequentialFiltering() {
        // Test applying multiple filters sequentially
        // First filter by type "DOG"
        filter.filter("TYPE", "DOG");
        // Then filter by size "MEDIUM"
        filter.filter("SIZE", "MEDIUM");
        
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Should only contain medium-sized dogs
        assertEquals(1, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog2));
    }
    
    @Test
    public void testSortOnDateAscending() {
        // Test sorting by date in ascending order
        filter.sortOnDate(true);
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Check order: dog1 (01/01/2023), cat1 (01/20/2023), dog2 (02/15/2023), cat2 (03/10/2023)
        assertEquals(dog1, filteredAnimals.get(0));
        assertEquals(cat1, filteredAnimals.get(1));
        assertEquals(dog2, filteredAnimals.get(2));
        assertEquals(cat2, filteredAnimals.get(3));
    }
    
    @Test
    public void testSortOnDateDescending() {
        // Test sorting by date in descending order
        filter.sortOnDate(false);
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        
        // Check order: cat2 (03/10/2023), dog2 (02/15/2023), cat1 (01/20/2023), dog1 (01/01/2023)
        assertEquals(cat2, filteredAnimals.get(0));
        assertEquals(dog2, filteredAnimals.get(1));
        assertEquals(cat1, filteredAnimals.get(2));
        assertEquals(dog1, filteredAnimals.get(3));
    }
    
    @Test
    public void testReset() {
        // First filter to get a subset
        filter.filter("TYPE", "DOG");
        assertEquals(2, filter.getFilteredAnimals().size());
        
        // Reset the filter
        filter.reset();
        
        // Check that all animals are included again
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        assertEquals(4, filteredAnimals.size());
        assertTrue(filteredAnimals.contains(dog1));
        assertTrue(filteredAnimals.contains(dog2));
        assertTrue(filteredAnimals.contains(cat1));
        assertTrue(filteredAnimals.contains(cat2));
    }
    
    @Test
    public void testFilterWithInvalidData() {
        // Test filtering with an invalid animal data field
        filter.filter("INVALID_FIELD", "some_value");
        
        // Should not change the filtered list
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        assertEquals(4, filteredAnimals.size());
    }
    
    @Test
    public void testFilterWithNonExistentValue() {
        // Test filtering with a value that doesn't match any animal
        filter.filter("TYPE", "BIRD");
        
        // Should return an empty list
        List<IAnimal> filteredAnimals = filter.getFilteredAnimals();
        assertEquals(0, filteredAnimals.size());
    }
} 