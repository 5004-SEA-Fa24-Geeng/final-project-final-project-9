package Model.Output;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Animals.Animal;
import Model.Animals.IAnimal;

/**
 * Tests for the AnimalOutputGenerator class.
 */
public class AnimalOutputGeneratorTest {

    private AnimalOutputGenerator generator;
    private List<IAnimal> animals;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        generator = new AnimalOutputGenerator();
        animals = new ArrayList<>();
        
        // Add test animals
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
        
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testGenerateOutput_JSON() {
        // Generate JSON output
        generator.generateOutput(animals, OutputFormat.JSON, outputStream);
        
        // Verify output
        String output = outputStream.toString();
        assertNotNull(output);
        assertFalse(output.isEmpty());
        
        // Check JSON format
        assertTrue(output.contains("{"));
        assertTrue(output.contains("}"));
        assertTrue(output.contains("\"animals\""));
        
        // Check animal data
        assertTrue(output.contains("\"type\": \"DOG\""));
        assertTrue(output.contains("\"breed\": \"LABRADOR\""));
        assertTrue(output.contains("\"size\": \"MEDIUM\""));
        assertTrue(output.contains("\"gender\": \"MALE\""));
        assertTrue(output.contains("\"pattern\": \"SOLID\""));
        assertTrue(output.contains("\"color\": \"BLACK\""));
        assertTrue(output.contains("\"age\": \"ADULT\""));
        assertTrue(output.contains("\"city\": \"Seattle\""));
        assertTrue(output.contains("\"address\": \"123 Main St\""));
        
        assertTrue(output.contains("\"type\": \"CAT\""));
        assertTrue(output.contains("\"breed\": \"PERSIAN\""));
        assertTrue(output.contains("\"gender\": \"FEMALE\""));
    }

    @Test
    public void testGenerateOutput_XML() {
        // Generate XML output
        generator.generateOutput(animals, OutputFormat.XML, outputStream);
        
        // Verify output
        String output = outputStream.toString();
        assertNotNull(output);
        assertFalse(output.isEmpty());
        
        // Check XML format
        assertTrue(output.contains("<?xml"));
        assertTrue(output.contains("<animals>"));
        assertTrue(output.contains("</animals>"));
        assertTrue(output.contains("<animal>"));
        assertTrue(output.contains("</animal>"));
        
        // Check animal data
        assertTrue(output.contains("<type>DOG</type>"));
        assertTrue(output.contains("<breed>LABRADOR</breed>"));
        assertTrue(output.contains("<size>MEDIUM</size>"));
        assertTrue(output.contains("<gender>MALE</gender>"));
        assertTrue(output.contains("<pattern>SOLID</pattern>"));
        assertTrue(output.contains("<color>BLACK</color>"));
        assertTrue(output.contains("<age>ADULT</age>"));
        assertTrue(output.contains("<city>Seattle</city>"));
        assertTrue(output.contains("<address>123 Main St</address>"));
        
        assertTrue(output.contains("<type>CAT</type>"));
        assertTrue(output.contains("<breed>PERSIAN</breed>"));
        assertTrue(output.contains("<gender>FEMALE</gender>"));
    }

    @Test
    public void testGenerateOutput_CSV() {
        // Generate CSV output
        generator.generateOutput(animals, OutputFormat.CSV, outputStream);
        
        // Verify output
        String output = outputStream.toString();
        assertNotNull(output);
        assertFalse(output.isEmpty());
        
        // CSV should have header and data rows
        String[] lines = output.split("\n");
        assertTrue(lines.length >= 3); // Header + at least 2 data rows
        
        // Check header contains expected fields
        String header = lines[0];
        assertTrue(header.contains("Type"));
        assertTrue(header.contains("Breed"));
        assertTrue(header.contains("Size"));
        assertTrue(header.contains("Gender"));
        
        // Check data rows contain animal information
        assertTrue(output.contains("DOG"));
        assertTrue(output.contains("LABRADOR"));
        assertTrue(output.contains("MEDIUM"));
        assertTrue(output.contains("MALE"));
        
        assertTrue(output.contains("CAT"));
        assertTrue(output.contains("PERSIAN"));
        assertTrue(output.contains("FEMALE"));
    }

    @Test
    public void testGenerateOutput_TXT() {
        // Generate TXT output
        generator.generateOutput(animals, OutputFormat.TXT, outputStream);
        
        // Verify output
        String output = outputStream.toString();
        assertNotNull(output);
        assertFalse(output.isEmpty());
        
        // Check text format
        assertTrue(output.contains("Animal Information:"));
        assertTrue(output.contains("Type: DOG"));
        assertTrue(output.contains("Breed: LABRADOR"));
        assertTrue(output.contains("Size: MEDIUM"));
        assertTrue(output.contains("Gender: MALE"));
        assertTrue(output.contains("Pattern: SOLID"));
        assertTrue(output.contains("Color: BLACK"));
        assertTrue(output.contains("Age: ADULT"));
        assertTrue(output.contains("Area: Seattle"));
        assertTrue(output.contains("Address: 123 Main St"));
        
        assertTrue(output.contains("Type: CAT"));
        assertTrue(output.contains("Breed: PERSIAN"));
        assertTrue(output.contains("Gender: FEMALE"));
    }

    @Test
    public void testGenerateOutput_EmptyList_JSON() {
        // Create empty animal list
        List<IAnimal> emptyList = new ArrayList<>();
        
        try {
            // Generate JSON output with empty list
            generator.generateOutput(emptyList, OutputFormat.JSON, outputStream);
            
            // Verify JSON output
            String jsonOutput = outputStream.toString();
            assertNotNull(jsonOutput, "JSON output should not be null for empty list");
            assertFalse(jsonOutput.isEmpty(), "JSON output should not be empty for empty list");
            
            // Expected format based on our implementation
            String expectedOutput = "{\n  \"animals\": []\n}\n";
            
            // Normalize line endings for consistent comparison
            String normalizedOutput = jsonOutput.replace("\r\n", "\n");
            
            // Compare exact output
            assertEquals(expectedOutput, normalizedOutput, 
                    "JSON output for empty list doesn't match expected format: " + jsonOutput);
        } catch (Exception e) {
            fail("Exception during JSON generation for empty list: " + e.getMessage());
        }
    }
    
    @Test
    public void testGenerateOutput_EmptyList_XML() {
        // Create empty animal list
        List<IAnimal> emptyList = new ArrayList<>();
        outputStream = new ByteArrayOutputStream(); // Fresh stream
        
        try {
            // Generate XML output with empty list
            generator.generateOutput(emptyList, OutputFormat.XML, outputStream);
            
            // Verify XML output
            String xmlOutput = outputStream.toString();
            assertNotNull(xmlOutput, "XML output should not be null for empty list");
            assertFalse(xmlOutput.isEmpty(), "XML output should not be empty for empty list");
            
            // Basic XML structure verification
            assertTrue(xmlOutput.contains("<?xml"), "XML output should contain XML declaration");
            assertTrue(xmlOutput.contains("<animals>"), "XML output should contain opening animals tag");
            assertTrue(xmlOutput.contains("</animals>"), "XML output should contain closing animals tag");
            
            // Verify no animal elements
            assertFalse(xmlOutput.contains("<animal>"), "XML output should not contain animal elements for empty list");
        } catch (Exception e) {
            fail("Exception during XML generation for empty list: " + e.getMessage());
        }
    }
    
    @Test
    public void testGenerateOutput_EmptyList_CSV() {
        // Create empty animal list
        List<IAnimal> emptyList = new ArrayList<>();
        outputStream = new ByteArrayOutputStream(); // Fresh stream
        
        try {
            // Generate CSV output with empty list
            generator.generateOutput(emptyList, OutputFormat.CSV, outputStream);
            
            // Verify CSV output
            String csvOutput = outputStream.toString();
            assertNotNull(csvOutput, "CSV output should not be null for empty list");
            
            // Should have at least the header row
            assertFalse(csvOutput.isEmpty(), "CSV output should contain at least the header row");
            
            // Verify it has header but no data rows
            String[] lines = csvOutput.split("\n");
            assertTrue(lines.length >= 1, "CSV output should have at least a header row");
            
            if (lines.length > 1) {
                // If more than one line, check second line is empty or whitespace
                assertTrue(lines[1].trim().isEmpty() || lines[1].startsWith(","), 
                          "Second line should be empty or contain only commas if present");
            }
        } catch (Exception e) {
            fail("Exception during CSV generation for empty list: " + e.getMessage());
        }
    }
    
    @Test
    public void testGenerateOutput_EmptyList_TXT() {
        // Create empty animal list
        List<IAnimal> emptyList = new ArrayList<>();
        outputStream = new ByteArrayOutputStream(); // Fresh stream
        
        try {
            // Generate TXT output with empty list
            generator.generateOutput(emptyList, OutputFormat.TXT, outputStream);
            
            // Verify TXT output 
            String txtOutput = outputStream.toString();
            
            // For TXT, we expect "No animals found."
            assertNotNull(txtOutput, "TXT output should not be null for empty list");
            assertFalse(txtOutput.isEmpty(), "TXT output should not be empty for empty list");
            assertEquals("No animals found.\n", txtOutput.replace("\r\n", "\n"), 
                    "TXT output should show 'No animals found.' for empty list");
        } catch (Exception e) {
            fail("Exception during TXT generation for empty list: " + e.getMessage());
        }
    }
} 