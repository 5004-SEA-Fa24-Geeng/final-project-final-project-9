package Model.Input;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import Model.Animals.Animal;
import Model.Animals.IAnimal;

/**
 * Tests for the AnimalListWrapper class.
 */
public class AnimalListWrapperTest {

    @Test
    public void testDefaultConstructor() {
        AnimalListWrapper wrapper = new AnimalListWrapper();
        assertNull(wrapper.getAnimals());
    }

    @Test
    public void testParameterizedConstructor() {
        // Create sample animal list
        List<IAnimal> animals = new ArrayList<>();
        animals.add(new Animal(
                "DOG", "LABRADOR", "MEDIUM", "MALE", "SOLID", "BLACK", "ADULT",
                "123 Main St", "Seattle", "12:30 PM", "2023-04-15",
                "Friendly black lab", "Near the park", "12345", "dog123.jpg"
        ));
        
        // Create wrapper with animals
        AnimalListWrapper wrapper = new AnimalListWrapper(animals);
        
        // Verify the wrapper contains the animal list
        assertNotNull(wrapper.getAnimals());
        assertEquals(1, wrapper.getAnimals().size());
        assertEquals("DOG", wrapper.getAnimals().get(0).getAnimalType());
        assertEquals("LABRADOR", wrapper.getAnimals().get(0).getSpecies());
        assertEquals("MEDIUM", wrapper.getAnimals().get(0).getAnimalSize());
    }

    @Test
    public void testSetAnimals() {
        // Create an empty wrapper
        AnimalListWrapper wrapper = new AnimalListWrapper();
        
        // Create sample animal list
        List<IAnimal> animals = new ArrayList<>();
        animals.add(new Animal(
                "CAT", "PERSIAN", "SMALL", "FEMALE", "SPOTTED", "WHITE", "YOUNG",
                "456 Oak St", "Bellevue", "3:45 PM", "2023-04-16",
                "Fluffy white cat", "In the garden", "12346", "cat456.jpg"
        ));
        
        // Set the animals
        wrapper.setAnimals(animals);
        
        // Verify the wrapper now contains the animal list
        assertNotNull(wrapper.getAnimals());
        assertEquals(1, wrapper.getAnimals().size());
        assertEquals("CAT", wrapper.getAnimals().get(0).getAnimalType());
        assertEquals("PERSIAN", wrapper.getAnimals().get(0).getSpecies());
        assertEquals("SMALL", wrapper.getAnimals().get(0).getAnimalSize());
    }
} 