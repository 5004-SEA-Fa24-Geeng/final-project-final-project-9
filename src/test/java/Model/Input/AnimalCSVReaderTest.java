package Model.Input;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import Model.Animals.IAnimal;

/**
 * Tests for the AnimalCSVReader class.
 */
public class AnimalCSVReaderTest {

    @TempDir
    Path tempDir;

    @Test
    public void testReadAnimals_ValidCSV() throws Exception {
        // Create a temporary CSV file
        File csvFile = tempDir.resolve("test-animals.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write CSV header
            writer.write("AnimalType,Species,AnimalSize,Gender,Pattern,Color,Age,Address,City,Time,Date,AnimalDescription,LocationDescription,Number,Image\n");
            // Write a test animal
            writer.write("DOG,LABRADOR,MEDIUM,MALE,SOLID,BLACK,ADULT,123 Main St,Seattle,12:30 PM,2023-04-15,Friendly black lab,Near the park,12345,dog123.jpg\n");
            // Write another test animal
            writer.write("CAT,PERSIAN,SMALL,FEMALE,SPOTTED,WHITE,YOUNG,456 Oak St,Bellevue,3:45 PM,2023-04-16,Fluffy white cat,In the garden,12346,cat456.jpg\n");
        }

        // Create reader with the test file
        AnimalCSVReader reader = new AnimalCSVReader(csvFile.getAbsolutePath());
        
        // Read animals
        List<IAnimal> animals = reader.readAnimals();
        
        // Verify results
        assertNotNull(animals);
        assertEquals(2, animals.size());
        
        // Verify first animal
        IAnimal firstAnimal = animals.get(0);
        assertEquals("DOG", firstAnimal.getAnimalType());
        assertEquals("LABRADOR", firstAnimal.getSpecies());
        assertEquals("MEDIUM", firstAnimal.getAnimalSize());
        assertEquals("MALE", firstAnimal.getGender());
        assertEquals("SOLID", firstAnimal.getPattern());
        assertEquals("BLACK", firstAnimal.getColor());
        assertEquals("ADULT", firstAnimal.getAge());
        assertEquals("123 Main St", firstAnimal.getAddress());
        assertEquals("Seattle", firstAnimal.getArea());
        assertEquals("12:30 PM", firstAnimal.getTime());
        assertEquals("2023-04-15", firstAnimal.getSeenDate());
        assertEquals("Friendly black lab", firstAnimal.getDescription());
        assertEquals("Near the park", firstAnimal.getLocDesc());
        assertEquals("12345", firstAnimal.getNumber());
        assertEquals("dog123.jpg", firstAnimal.getImage());
        
        // Verify second animal
        IAnimal secondAnimal = animals.get(1);
        assertEquals("CAT", secondAnimal.getAnimalType());
        assertEquals("PERSIAN", secondAnimal.getSpecies());
        assertEquals("SMALL", secondAnimal.getAnimalSize());
        assertEquals("FEMALE", secondAnimal.getGender());
    }

    @Test
    public void testReadAnimals_EmptyFile() throws Exception {
        // Create an empty temporary CSV file
        File csvFile = tempDir.resolve("empty-animals.csv").toFile();
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write only the header
            writer.write("AnimalType,Species,AnimalSize,Gender,Pattern,Color,Age,Address,City,Time,Date,AnimalDescription,LocationDescription,Number,Image\n");
        }

        // Create reader with the empty file
        AnimalCSVReader reader = new AnimalCSVReader(csvFile.getAbsolutePath());
        
        // Read animals
        List<IAnimal> animals = reader.readAnimals();
        
        // Verify results
        assertNotNull(animals);
        assertEquals(0, animals.size());
    }

    @Test
    public void testReadAnimals_NonexistentFile() {
        // Create reader with a nonexistent file that definitely doesn't exist
        String nonexistentFilePath = tempDir.resolve("definitely-does-not-exist-" + System.currentTimeMillis() + ".csv").toString();
        AnimalCSVReader reader = new AnimalCSVReader(nonexistentFilePath);

        try {
            // Read animals
            List<IAnimal> animals = reader.readAnimals();
            
            // Verify results - should return an empty list, not null
            assertNotNull(animals, "Animals list should not be null even for nonexistent file");
            assertEquals(0, animals.size(), "Animals list should be empty for nonexistent file");
        } catch (Exception e) {
            // This test is failing because the method is throwing an uncaught exception
            // This assertion will fail and show the actual exception being thrown
            assertEquals("Expected empty list, got exception: " + e.getClass().getName(), 
                       "Expected empty list, got exception: none", 
                       "AnimalCSVReader.readAnimals() should handle nonexistent files gracefully");
        }
    }
} 