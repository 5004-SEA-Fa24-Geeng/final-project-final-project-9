package Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Animals.IAnimal;

class AnimalFilterTest {
    private AnimalList animalList;
    private AnimalFilter animalFilter;

    @BeforeEach
    void setUp() {
        animalList = new AnimalList();
        animalFilter = new AnimalFilter(animalList);
    }

    @Test
    void testFilterOnType() {
        animalFilter.filter("TYPE", "DOG");
        List<IAnimal> filtered = animalFilter.getFilteredAnimals();
        for (IAnimal animal : filtered) {
            assertEquals("DOG", animal.getAnimalType());
        }
    }

    @Test
    void testFilterOnSpecies() {
        animalFilter.filter("SPECIES", "Labrador");
        List<IAnimal> filtered = animalFilter.getFilteredAnimals();
        for (IAnimal animal : filtered) {
            assertEquals("Labrador", animal.getSpecies());
        }
    }

    @Test
    void testFilterOnSize() {
        animalFilter.filter("SIZE", "Medium");
        List<IAnimal> filtered = animalFilter.getFilteredAnimals();
        for (IAnimal animal : filtered) {
            assertEquals("Medium", animal.getAnimalSize());
        }
    }

    @Test
    void testFilterOnGender() {
        animalFilter.filter("GENDER", "Male");
        List<IAnimal> filtered = animalFilter.getFilteredAnimals();
        for (IAnimal animal : filtered) {
            assertEquals("Male", animal.getGender());
        }
    }

    @Test
    void testFilterOnSeenDate() {
        animalFilter.filter("SEENDATE", "within 1 week");
        List<IAnimal> filtered = animalFilter.getFilteredAnimals();
        assertNotNull(filtered);
    }

    @Test
    void testReset() {
        // Apply some filters
        animalFilter.filter("TYPE", "DOG");
        int filteredCount = animalFilter.getFilteredAnimals().size();
        
        // Reset the filter
        animalFilter.reset();
        
        // After reset, the filtered list should be the same as the original list
        assertEquals(animalList.count(), animalFilter.getFilteredAnimals().size());
    }

    @Test
    void testSortOnDate() {
        // Sort in ascending order
        animalFilter.sortOnDate(true);
        List<IAnimal> sorted = animalFilter.getFilteredAnimals();
        
        // Verify the list is not empty
        assertFalse(sorted.isEmpty());
        
        // Sort in descending order
        animalFilter.sortOnDate(false);
        sorted = animalFilter.getFilteredAnimals();
        assertFalse(sorted.isEmpty());
    }
} 