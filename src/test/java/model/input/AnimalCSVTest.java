package model.input;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import model.animals.IAnimal;

/**
 * Tests for the AnimalCSV class.
 */
public class AnimalCSVTest {

    @Test
    public void testToAnimal() throws Exception {
        // Create an AnimalCSV object with test data
        AnimalCSV animalCSV = new AnimalCSV();
        
        // Set test values using reflection
        setField(animalCSV, "animalType", "DOG");
        setField(animalCSV, "species", "LABRADOR");
        setField(animalCSV, "animalSize", "MEDIUM");
        setField(animalCSV, "gender", "MALE");
        setField(animalCSV, "pattern", "SOLID");
        setField(animalCSV, "color", "BLACK");
        setField(animalCSV, "age", "ADULT");
        setField(animalCSV, "address", "123 Main St");
        setField(animalCSV, "city", "Seattle");
        setField(animalCSV, "time", "12:30 PM");
        setField(animalCSV, "date", "2023-04-15");
        setField(animalCSV, "description", "Friendly black lab");
        setField(animalCSV, "locDesc", "Near the park");
        setField(animalCSV, "number", "12345");
        setField(animalCSV, "image", "dog123.jpg");
        
        // Convert to Animal object
        IAnimal animal = animalCSV.toAnimal();
        
        // Verify values were correctly transferred
        assertNotNull(animal);
        assertEquals("DOG", animal.getAnimalType());
        assertEquals("LABRADOR", animal.getSpecies());
        assertEquals("MEDIUM", animal.getAnimalSize());
        assertEquals("MALE", animal.getGender());
        assertEquals("SOLID", animal.getPattern());
        assertEquals("BLACK", animal.getColor());
        assertEquals("ADULT", animal.getAge());
        assertEquals("123 Main St", animal.getAddress());
        assertEquals("Seattle", animal.getArea());
        assertEquals("12:30 PM", animal.getTime());
        assertEquals("2023-04-15", animal.getSeenDate());
        assertEquals("Friendly black lab", animal.getDescription());
        assertEquals("Near the park", animal.getLocDesc());
        assertEquals("12345", animal.getNumber());
        assertEquals("dog123.jpg", animal.getImage());
    }
    
    /**
     * Helper method to set a field value using reflection.
     * 
     * @param object The object to set the field on
     * @param fieldName The name of the field
     * @param value The value to set
     * @throws Exception If the field cannot be accessed
     */
    private void setField(Object object, String fieldName, String value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
} 