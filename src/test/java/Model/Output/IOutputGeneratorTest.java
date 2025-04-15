package Model.Output;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import Model.Animals.Animal;
import Model.Animals.IAnimal;

/**
 * Tests for the IOutputGenerator interface using a test implementation.
 */
public class IOutputGeneratorTest {

    // A test implementation of IOutputGenerator for testing
    private static class TestOutputGenerator implements IOutputGenerator {
        private OutputFormat lastFormat;
        private int generateOutputCallCount = 0;

        @Override
        public void generateOutput(List<IAnimal> animals, OutputFormat format, OutputStream outputStream) {
            generateOutputCallCount++;
            lastFormat = format;
            
            try {
                // Write simple output for testing
                String output = "TestOutput: Format=" + format + ", Animals=" + animals.size();
                outputStream.write(output.getBytes());
            } catch (Exception e) {
                fail("Exception during test output generation: " + e.getMessage());
            }
        }
        
        public OutputFormat getLastFormat() {
            return lastFormat;
        }
        
        public int getGenerateOutputCallCount() {
            return generateOutputCallCount;
        }
    }

    @Test
    public void testGenerateOutput() {
        // Create test implementation
        TestOutputGenerator generator = new TestOutputGenerator();
        
        // Create test animals
        List<IAnimal> animals = new ArrayList<>();
        animals.add(new Animal(
                "DOG", "LABRADOR", "MEDIUM", "MALE", "SOLID", "BLACK", "ADULT",
                "123 Main St", "Seattle", "12:30 PM", "2023-04-15",
                "Friendly black lab", "Near the park", "12345", "dog123.jpg"
        ));
        
        // Create output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // Call generateOutput
        generator.generateOutput(animals, OutputFormat.JSON, outputStream);
        
        // Verify method was called correctly
        assertEquals(1, generator.getGenerateOutputCallCount());
        assertEquals(OutputFormat.JSON, generator.getLastFormat());
        
        // Verify output
        String output = outputStream.toString();
        assertEquals("TestOutput: Format=JSON, Animals=1", output);
        
        // Try with a different format
        outputStream.reset();
        generator.generateOutput(animals, OutputFormat.XML, outputStream);
        
        // Verify second call
        assertEquals(2, generator.getGenerateOutputCallCount());
        assertEquals(OutputFormat.XML, generator.getLastFormat());
        
        // Verify second output
        output = outputStream.toString();
        assertEquals("TestOutput: Format=XML, Animals=1", output);
    }

    @Test
    public void testGenerateOutput_EmptyList() {
        // Create test implementation
        TestOutputGenerator generator = new TestOutputGenerator();
        
        // Create empty animal list
        List<IAnimal> animals = new ArrayList<>();
        
        // Create output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // Call generateOutput with empty list
        generator.generateOutput(animals, OutputFormat.CSV, outputStream);
        
        // Verify method was called correctly
        assertEquals(1, generator.getGenerateOutputCallCount());
        assertEquals(OutputFormat.CSV, generator.getLastFormat());
        
        // Verify output
        String output = outputStream.toString();
        assertEquals("TestOutput: Format=CSV, Animals=0", output);
    }
} 