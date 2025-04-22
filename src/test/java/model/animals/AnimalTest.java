package model.animals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Animal class.
 */
public class AnimalTest {
    private Animal animal;
    
    @BeforeEach
    public void setUp() {
        animal = new Animal(
                "DOG",             // type
                "LABRADOR",        // species
                "MEDIUM",          // size
                "MALE",            // gender
                "SOLID",           // pattern
                "BLACK",           // color
                "ADULT",           // age
                "123 Main St",     // address
                "Boston",          // area
                "12:30 PM",        // time
                "2023-04-15",      // seenDate
                "Friendly black lab", // description
                "Near the park",   // locDesc
                "12345",           // number
                "dog123.jpg"       // image
        );
    }
    
    @Test
    public void testAnimalConstructor() {
        assertNotNull(animal);
    }
    
    @Test
    public void testGetAnimalType() {
        assertEquals("DOG", animal.getAnimalType());
    }
    
    @Test
    public void testGetSpecies() {
        assertEquals("LABRADOR", animal.getSpecies());
    }
    
    @Test
    public void testGetAnimalSize() {
        assertEquals("MEDIUM", animal.getAnimalSize());
    }
    
    @Test
    public void testGetGender() {
        assertEquals("MALE", animal.getGender());
    }
    
    @Test
    public void testGetPattern() {
        assertEquals("SOLID", animal.getPattern());
    }
    
    @Test
    public void testGetColor() {
        assertEquals("BLACK", animal.getColor());
    }
    
    @Test
    public void testGetAge() {
        assertEquals("ADULT", animal.getAge());
    }
    
    @Test
    public void testGetAddress() {
        assertEquals("123 Main St", animal.getAddress());
    }
    
    @Test
    public void testGetArea() {
        assertEquals("Boston", animal.getArea());
    }
    
    @Test
    public void testGetTime() {
        assertEquals("12:30 PM", animal.getTime());
    }
    
    @Test
    public void testGetSeenDate() {
        assertEquals("2023-04-15", animal.getSeenDate());
    }
    
    @Test
    public void testGetDescription() {
        assertEquals("Friendly black lab", animal.getDescription());
    }
    
    @Test
    public void testGetLocDesc() {
        assertEquals("Near the park", animal.getLocDesc());
    }
    
    @Test
    public void testGetNumber() {
        assertEquals("12345", animal.getNumber());
    }
    
    @Test
    public void testGetImage() {
        assertEquals("dog123.jpg", animal.getImage());
    }
} 