package Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Animals.IAnimal;

class AnimalListTest {
    private AnimalList animalList;

    @BeforeEach
    void setUp() {
        animalList = new AnimalList();
    }

    @Test
    void testGetAnimals() {
        List<IAnimal> animals = animalList.getAnimals();
        assertNotNull(animals);
    }

    @Test
    void testCount() {
        int count = animalList.count();
        assertTrue(count >= 0);
    }

    @Test
    void testGetMaxNumber() {
        int maxNumber = animalList.getMaxNumber();
        assertTrue(maxNumber >= 0);
    }

    @Test
    void testAddNewAnimal() {
        int initialCount = animalList.count();
        animalList.addNewAnimal(
            "DOG", "Labrador", "Medium", "Male", "Solid", "Black",
            "Adult", "04/11/2024", "10:00", "123 Main St", "Downtown",
            "Near park", "Friendly dog"
        );
        assertEquals(initialCount + 1, animalList.count());
    }

    @Test
    void testAddNewAnimalInvalidType() {
        int initialCount = animalList.count();
        animalList.addNewAnimal(
            "INVALID", "Labrador", "Medium", "Male", "Solid", "Black",
            "Adult", "04/11/2024", "10:00", "123 Main St", "Downtown",
            "Near park", "Friendly dog"
        );
        // Count should remain the same due to invalid type
        assertEquals(initialCount, animalList.count());
    }
} 