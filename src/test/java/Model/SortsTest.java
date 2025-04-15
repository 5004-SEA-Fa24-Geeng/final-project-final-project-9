package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Animals.Animal;
import Model.Animals.IAnimal;

/**
 * Tests for the Sorts utility class.
 */
public class SortsTest {
    private List<IAnimal> animals;
    private IAnimal dog1;
    private IAnimal dog2;
    private IAnimal cat1;
    private IAnimal cat2;

    @BeforeEach
    public void setUp() {
        animals = new ArrayList<>();
        
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
        
        // Add animals to list
        animals.add(dog1);
        animals.add(cat1);
        animals.add(dog2);
        animals.add(cat2);
    }

    @Test
    public void testSortByDateAscending() {
        // Sort by date ascending
        Collections.sort(animals, Sorts.sortByDate(true));
        
        // Check order: dog1 (01/01/2023), cat1 (01/20/2023), dog2 (02/15/2023), cat2 (03/10/2023)
        assertEquals(dog1, animals.get(0));
        assertEquals(cat1, animals.get(1));
        assertEquals(dog2, animals.get(2));
        assertEquals(cat2, animals.get(3));
    }
    
    @Test
    public void testSortByDateDescending() {
        // Sort by date descending
        Collections.sort(animals, Sorts.sortByDate(false));
        
        // Check order: cat2 (03/10/2023), dog2 (02/15/2023), cat1 (01/20/2023), dog1 (01/01/2023)
        assertEquals(cat2, animals.get(0));
        assertEquals(dog2, animals.get(1));
        assertEquals(cat1, animals.get(2));
        assertEquals(dog1, animals.get(3));
    }
    
    @Test
    public void testSortByTypeAscending() {
        // Sort by type ascending
        Collections.sort(animals, Sorts.sortByType(true));
        
        // CAT comes before DOG alphabetically
        assertEquals("CAT", animals.get(0).getAnimalType());
        assertEquals("CAT", animals.get(1).getAnimalType());
        assertEquals("DOG", animals.get(2).getAnimalType());
        assertEquals("DOG", animals.get(3).getAnimalType());
    }
    
    @Test
    public void testSortByTypeDescending() {
        // Sort by type descending
        Collections.sort(animals, Sorts.sortByType(false));
        
        // DOG comes before CAT in descending order
        assertEquals("DOG", animals.get(0).getAnimalType());
        assertEquals("DOG", animals.get(1).getAnimalType());
        assertEquals("CAT", animals.get(2).getAnimalType());
        assertEquals("CAT", animals.get(3).getAnimalType());
    }
    
    @Test
    public void testSortBySizeAscending() {
        // Sort by size ascending (SMALL, MEDIUM, LARGE)
        Collections.sort(animals, Sorts.sortBySize(true));
        
        // Check the sizes are in ascending order
        assertEquals("SMALL", animals.get(0).getAnimalSize());
        assertEquals("MEDIUM", animals.get(1).getAnimalSize());
        assertEquals("MEDIUM", animals.get(2).getAnimalSize());
        assertEquals("LARGE", animals.get(3).getAnimalSize());
    }
    
    @Test
    public void testSortBySizeDescending() {
        // Sort by size descending (LARGE, MEDIUM, SMALL)
        Collections.sort(animals, Sorts.sortBySize(false));
        
        // Check the sizes are in descending order
        assertEquals("LARGE", animals.get(0).getAnimalSize());
        assertEquals("MEDIUM", animals.get(1).getAnimalSize());
        assertEquals("MEDIUM", animals.get(2).getAnimalSize());
        assertEquals("SMALL", animals.get(3).getAnimalSize());
    }
    
    @Test
    public void testSortByAgeAscending() {
        // Sort by age ascending
        Collections.sort(animals, Sorts.sortByAge(true));
        
        // Since "ADULT" and "YOUNG" are sorted alphabetically
        // ADULT comes before YOUNG alphabetically
        assertEquals("ADULT", animals.get(0).getAge());
        assertEquals("ADULT", animals.get(1).getAge());
        assertEquals("YOUNG", animals.get(2).getAge());
        assertEquals("YOUNG", animals.get(3).getAge());
    }
    
    @Test
    public void testSortByAgeDescending() {
        // Sort by age descending
        Collections.sort(animals, Sorts.sortByAge(false));
        
        // YOUNG comes before ADULT in descending alphabetical order
        assertEquals("YOUNG", animals.get(0).getAge());
        assertEquals("YOUNG", animals.get(1).getAge());
        assertEquals("ADULT", animals.get(2).getAge());
        assertEquals("ADULT", animals.get(3).getAge());
    }
    
    @Test
    public void testSortByAreaAscending() {
        // Sort by area ascending
        Collections.sort(animals, Sorts.sortByArea(true));
        
        // Check areas in alphabetical order: Boston, Cambridge, Malden, Somerville
        assertEquals("Boston", animals.get(0).getArea());
        assertEquals("Cambridge", animals.get(1).getArea());
        assertEquals("Malden", animals.get(2).getArea());
        assertEquals("Somerville", animals.get(3).getArea());
    }
    
    @Test
    public void testSortByAreaDescending() {
        // Sort by area descending
        Collections.sort(animals, Sorts.sortByArea(false));
        
        // Check areas in reverse alphabetical order: Somerville, Malden, Cambridge, Boston
        assertEquals("Somerville", animals.get(0).getArea());
        assertEquals("Malden", animals.get(1).getArea());
        assertEquals("Cambridge", animals.get(2).getArea());
        assertEquals("Boston", animals.get(3).getArea());
    }
    
    @Test
    public void testSortBySpeciesAscending() {
        // Sort by species ascending
        Collections.sort(animals, Sorts.sortBySpecies(true));
        
        // Check species in alphabetical order: GOLDEN RETRIEVER, LABRADOR, SIAMESE, TABBY
        assertEquals("GOLDEN RETRIEVER", animals.get(0).getSpecies());
        assertEquals("LABRADOR", animals.get(1).getSpecies());
        assertEquals("SIAMESE", animals.get(2).getSpecies());
        assertEquals("TABBY", animals.get(3).getSpecies());
    }
    
    @Test
    public void testSortBySpeciesDescending() {
        // Sort by species descending
        Collections.sort(animals, Sorts.sortBySpecies(false));
        
        // Check species in reverse alphabetical order: TABBY, SIAMESE, LABRADOR, GOLDEN RETRIEVER
        assertEquals("TABBY", animals.get(0).getSpecies());
        assertEquals("SIAMESE", animals.get(1).getSpecies());
        assertEquals("LABRADOR", animals.get(2).getSpecies());
        assertEquals("GOLDEN RETRIEVER", animals.get(3).getSpecies());
    }
} 