package Model.Input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import Model.Animals.Animal;
import Model.Animals.IAnimal;

/**
 * Tests for the AnimalCSVWriter class.
 */
public class AnimalCSVWriterTest {

    @TempDir
    Path tempDir;

    @Test
    public void testWriteAnimals() throws Exception {
        // Create a temporary file for output
        File outputFile = tempDir.resolve("output-animals.csv").toFile();
        
        // Create test animals
        List<IAnimal> animals = new ArrayList<>();
        animals.add(new Animal(
                "DOG", "LABRADOR", "MEDIUM", "MALE", "SOLID", "BLACK", "ADULT",
                "123 Main St", "Seattle", "12:30 PM", "2023-04-15",
                "Friendly black lab", "Near the park", "12345", "dog123.jpg"
        ));
        animals.add(new Animal(
                "CAT", "PERSIAN", "SMALL", "FEMALE", "SPOTTED", "WHITE", "YOUNG",
                "456 Oak St", "Bellevue", "3:45 PM", "2023-04-16",
                "Fluffy white cat", "In the garden", "12346", "cat456.jpg"
        ));
        
        // Create writer with the output file
        AnimalCSVWriter writer = new AnimalCSVWriter(outputFile.getAbsolutePath());
        
        // Write animals
        writer.writeAnimals(animals);
        
        // Verify file exists
        assertTrue(outputFile.exists());
        
        // Read and verify the content
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        // Verify header and data
        assertEquals(3, lines.size()); // Header + 2 animals
        
        // Verify header (removing quotes that CSVWriter adds)
        String header = lines.get(0).replace("\"", "");
        assertTrue(header.contains("AnimalType"));
        assertTrue(header.contains("Species"));
        assertTrue(header.contains("AnimalSize"));
        assertTrue(header.contains("Gender"));
        assertTrue(header.contains("Pattern"));
        assertTrue(header.contains("Color"));
        assertTrue(header.contains("Age"));
        assertTrue(header.contains("Address"));
        assertTrue(header.contains("City"));
        assertTrue(header.contains("Time"));
        assertTrue(header.contains("Date"));
        assertTrue(header.contains("AnimalDescription"));
        assertTrue(header.contains("LocationDescription"));
        assertTrue(header.contains("Number"));
        assertTrue(header.contains("Image"));
        
        // Verify first animal data (removing quotes that CSVWriter adds)
        String firstAnimal = lines.get(1).replace("\"", "");
        assertTrue(firstAnimal.contains("DOG"));
        assertTrue(firstAnimal.contains("LABRADOR"));
        assertTrue(firstAnimal.contains("MEDIUM"));
        assertTrue(firstAnimal.contains("MALE"));
        assertTrue(firstAnimal.contains("SOLID"));
        assertTrue(firstAnimal.contains("BLACK"));
        assertTrue(firstAnimal.contains("ADULT"));
        assertTrue(firstAnimal.contains("123 Main St"));
        assertTrue(firstAnimal.contains("Seattle"));
        assertTrue(firstAnimal.contains("12:30 PM"));
        assertTrue(firstAnimal.contains("2023-04-15"));
        assertTrue(firstAnimal.contains("Friendly black lab"));
        assertTrue(firstAnimal.contains("Near the park"));
        assertTrue(firstAnimal.contains("12345"));
        assertTrue(firstAnimal.contains("dog123.jpg"));
        
        // Verify second animal data (removing quotes that CSVWriter adds)
        String secondAnimal = lines.get(2).replace("\"", "");
        assertTrue(secondAnimal.contains("CAT"));
        assertTrue(secondAnimal.contains("PERSIAN"));
        assertTrue(secondAnimal.contains("SMALL"));
        assertTrue(secondAnimal.contains("FEMALE"));
    }
    
    @Test
    public void testWriteAnimals_EmptyList() throws Exception {
        // Create a temporary file for output
        File outputFile = tempDir.resolve("empty-animals.csv").toFile();
        
        // Create an empty list
        List<IAnimal> animals = new ArrayList<>();
        
        // Create writer with the output file
        AnimalCSVWriter writer = new AnimalCSVWriter(outputFile.getAbsolutePath());
        
        // Write empty list
        writer.writeAnimals(animals);
        
        // Verify file exists
        assertTrue(outputFile.exists());
        
        // Read and verify the content
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        // Verify only header is present
        assertEquals(1, lines.size());
        
        // Verify header (removing quotes that CSVWriter adds)
        String header = lines.get(0).replace("\"", "");
        assertTrue(header.contains("AnimalType"));
        assertTrue(header.contains("Image"));
    }
} 