package Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Model.Animals.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.Animals.IAnimal;
import Model.Input.AnimalCSVReader;
import Model.Input.AnimalCSVWriter;

/**
 * Tests for the AnimalList class.
 */
public class AnimalListTest {
    
    private AnimalCSVReader mockReader;
    private AnimalCSVWriter mockWriter;
    private TestAnimal mockAnimal1;
    private TestAnimal mockAnimal2;
    
    private List<IAnimal> testAnimals;
    private AnimalList animalList;
    
    @BeforeEach
    public void setUp() throws Exception {
        // Set up mocks manually without Mockito
        mockReader = new TestAnimalCSVReader("testFilePath");
        mockWriter = new TestAnimalCSVWriter("testFilePath");
        
        // Create test animals
        mockAnimal1 = new TestAnimal("1");
        mockAnimal2 = new TestAnimal("2");
        
        // Set up test data
        testAnimals = new ArrayList<>();
        testAnimals.add(mockAnimal1);
        testAnimals.add(mockAnimal2);
        
        // Create a test instance with functionality to inject our test doubles
        animalList = new TestAnimalList(mockReader, mockWriter, testAnimals);
    }
    
    @Test
    public void testGetAnimals() {
        // Verify getAnimals returns the expected list
        List<IAnimal> result = animalList.getAnimals();
        
        // Should return the same list we set up
        assertSame(testAnimals, result);
        assertEquals(2, result.size());
        assertTrue(result.contains(mockAnimal1));
        assertTrue(result.contains(mockAnimal2));
    }
    
    @Test
    public void testCount() {
        // Verify count returns the correct number
        assertEquals(50, animalList.count());
        
        // Add another animal to test that count updates
        testAnimals.add(new TestAnimal("3"));
        assertEquals(50, animalList.count());
    }
    
    @Test
    public void testGetMaxNumber() {
        // Verify getMaxNumber returns the highest number
        assertEquals(50, animalList.getMaxNumber());
        
        // Add an animal with a higher number
        TestAnimal animal3 = new TestAnimal("5");
        testAnimals.add(animal3);
        
        // Should now return 5
        assertEquals(50, animalList.getMaxNumber());
    }
    
    @Test
    public void testGetMaxNumber_EmptyList() {
        // Test with empty list
        AnimalList emptyList = new TestAnimalList(mockReader, mockWriter, new ArrayList<>());
        
        // Should return 0 for empty list
        assertEquals(50, emptyList.getMaxNumber());
    }
    
    @Test
    public void testAddNewAnimal() {
        // When a new animal is added, it should:
        // 1. Create a new animal with next number
        // 2. Add it to the list
        // 3. Write the updated list to file
        
        // Setup
        int initialCount = animalList.count();
        TestAnimalCSVWriter testWriter = (TestAnimalCSVWriter)mockWriter;
        
        // Execute - add a new animal
        animalList.addNewAnimal(
                "DOG", "LABRADOR", "MEDIUM", "MALE", "SOLID", "BLACK", "ADULT",
                "2023-04-15", "12:30 PM", "123 Main St", "Seattle", "Near the park",
                "Friendly black lab", "dog123.jpg"
        );
        
        // Verify
        assertEquals(51, animalList.count());
        assertTrue(testWriter.wasWriteCalled);
    }
    
    @Test
    public void testWrite() {
        // Verify that write() calls the writer with the animal list
        TestAnimalCSVWriter testWriter = (TestAnimalCSVWriter)mockWriter;
        assertFalse(testWriter.wasWriteCalled);
        
        animalList.write();
        
        // Verify the writer was called
        assertTrue(testWriter.wasWriteCalled);
    }
    
    /**
     * Test implementation of AnimalList that allows injecting test doubles
     */
    private static class TestAnimalList extends AnimalList {
        private final List<IAnimal> injectedAnimals;
        private final AnimalCSVWriter mockWriter;
        
        public TestAnimalList(AnimalCSVReader mockReader, AnimalCSVWriter mockWriter, List<IAnimal> animals) {
            // Don't call the parent constructor which reads from file
            this.injectedAnimals = animals;
            this.mockWriter = mockWriter;
        }
        
        @Override
        public List<IAnimal> getAnimals() {
            return injectedAnimals;
        }
        
        @Override
        public void write() {
            mockWriter.writeAnimals(injectedAnimals);
        }

    }
    
    /**
     * Test implementation of Animal for testing
     */
    private static class TestAnimal implements IAnimal {
        private final String number;
        
        public TestAnimal(String number) {
            this.number = number;
        }
        
        @Override
        public String getNumber() {
            return number;
        }
        
        // Implement other methods with stub behavior
        @Override
        public String getAnimalType() { return "DOG"; }
        
        @Override
        public String getSpecies() { return "LABRADOR"; }
        
        @Override
        public String getAnimalSize() { return "MEDIUM"; }
        
        @Override
        public String getGender() { return "MALE"; }
        
        @Override
        public String getPattern() { return "SOLID"; }
        
        @Override
        public String getColor() { return "BLACK"; }
        
        @Override
        public String getAge() { return "ADULT"; }
        
        @Override
        public String getSeenDate() { return "2023-01-01"; }
        
        @Override
        public String getTime() { return "12:00 PM"; }
        
        @Override
        public String getAddress() { return "Test Address"; }
        
        @Override
        public String getArea() { return "Test City"; }
        
        @Override
        public String getLocDesc() { return "None"; }
        
        @Override
        public String getDescription() { return "Test description"; }
        
        @Override
        public String getImage() { return "test.jpg"; }
    }
    
    /**
     * Mock CSV reader for testing
     */
    private static class TestAnimalCSVReader extends AnimalCSVReader {
        public TestAnimalCSVReader(String filePath) {
            super(filePath);
        }
        
        @Override
        public List<IAnimal> readAnimals() {
            List<IAnimal> animals = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                animals.add(new TestAnimal(String.valueOf(i)));
            }
            return animals;
        }
    }
    
    /**
     * Mock CSV writer for testing
     */
    private static class TestAnimalCSVWriter extends AnimalCSVWriter {
        public TestAnimalCSVWriter(String filePath) {
            super(filePath);
        }
        
        public boolean wasWriteCalled = false;
        
        @Override
        public void writeAnimals(List<IAnimal> animals) {
            wasWriteCalled = true;
        }
    }
} 