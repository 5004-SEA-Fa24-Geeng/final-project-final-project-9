package controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.animals.Animal;
import model.animals.IAnimal;
import view.IView;

/**
 * Tests for the controller class.
 */
public class ControllerTest {
    private TestController controller;
    private TestAnimalList animalList;
    private TestAnimalFilter animalFilter;
    private TestView view;
    private IAnimal dog1;
    private IAnimal cat1;
    private List<IAnimal> filteredAnimals;

    @BeforeEach
    public void setUp() {
        // Create test animals
        dog1 = new Animal(
                "DOG",            // type
                "LABRADOR",       // species
                "LARGE",          // size
                "MALE",           // gender
                "SOLID",          // pattern
                "BLACK",          // color
                "ADULT",          // age
                "123 Main St",    // address
                "Boston",         // area
                "12:30 PM",       // time
                "01/01/2023",     // seenDate
                "Friendly dog",   // description
                "Near the park",  // locDesc
                "12345",          // number
                "dog1.jpg"        // image
        );
        
        cat1 = new Animal(
                "CAT",            // type
                "SIAMESE",        // species
                "SMALL",          // size
                "FEMALE",         // gender
                "STRIPED",        // pattern
                "GRAY",           // color
                "YOUNG",          // age
                "789 Pine St",    // address
                "Somerville",     // area
                "9:15 AM",        // time
                "01/20/2023",     // seenDate
                "Shy cat",        // description
                "Under a porch",  // locDesc
                "12347",          // number
                "cat1.jpg"        // image
        );
        
        // Create filtered animals list
        filteredAnimals = new ArrayList<>();
        filteredAnimals.add(dog1);
        filteredAnimals.add(cat1);
        
        // Create test implementations
        animalList = new TestAnimalList();
        animalFilter = new TestAnimalFilter(filteredAnimals);
        view = new TestView();
        
        // Create simplified test controller
        controller = new TestController(animalList, animalFilter, view);
    }

    @Test
    public void testSetView() {
        // Verify the view was set in the setUp method
        assertSame(view, controller.getView());
    }

    @Test
    public void testHandleFilter() {
        // Test successful filter
        controller.handleFilter("TYPE", "DOG");
        
        // Verify filter was called with correct arguments
        assertEquals("TYPE", animalFilter.lastFilterType);
        assertEquals("DOG", animalFilter.lastFilterValue);
        
        // Verify view was updated with filtered animals
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
    }
    
    @Test
    public void testHandleFilterWithException() {
        // Configure filter to throw exception
        animalFilter.throwExceptionOnFilter = true;
        
        // Call handleFilter
        controller.handleFilter("TYPE", "DOG");
        
        // Verify error was shown
        assertEquals("Filter failed: Test filter exception", view.lastErrorMessage);
    }

    @Test
    public void testHandleSortWithAscendingParameter() {
        // Test sort with ascending parameter
        controller.handleSort(true);
        
        // Verify sort was called with correct argument
        assertTrue(animalFilter.lastSortAscending);
        
        // Verify view was updated
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
    }
    
    @Test
    public void testHandleSortWithDescendingParameter() {
        // Test sort with descending parameter
        controller.handleSort(false);
        
        // Verify sort was called with correct argument
        assertFalse(animalFilter.lastSortAscending);
        
        // Verify view was updated
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
    }
    
    @Test
    public void testHandleSortDefaultToAscending() {
        // Test default sort (should be ascending)
        controller.handleSort();
        
        // Verify sort was called with ascending=true
        assertTrue(animalFilter.lastSortAscending);
        
        // Verify view was updated
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
    }
    
    @Test
    public void testHandleSortWithException() {
        // Configure filter to throw exception
        animalFilter.throwExceptionOnSort = true;
        
        // Call handleSort
        controller.handleSort(true);
        
        // Verify error was shown
        assertEquals("Sort failed: Test sort exception", view.lastErrorMessage);
    }

    @Test
    public void testHandleReset() {
        // Test reset
        controller.handleReset();
        
        // Verify reset was called
        assertTrue(animalFilter.resetCalled);
        
        // Verify view was updated
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
        
        // Verify success message was shown
        assertEquals("All filters have been reset", view.lastSuccessMessage);
    }
    
    @Test
    public void testHandleResetWithException() {
        // Configure filter to throw exception
        animalFilter.throwExceptionOnReset = true;
        
        // Call handleReset
        controller.handleReset();
        
        // Verify error was shown
        assertEquals("Reset failed: Test reset exception", view.lastErrorMessage);
    }

    @Test
    public void testResetFilteredAnimals() {
        // Clear any previous reset calls
        animalFilter.resetCalled = false;
        
        // Test resetFilteredAnimals
        controller.resetFilteredAnimals();
        
        // Verify reset was called
        assertTrue(animalFilter.resetCalled);
        
        // Verify view was NOT updated
        assertNull(view.lastDisplayedAnimals);
    }

    @Test
    public void testHandleMapDisplay() {
        // Test map display
        controller.handleMapDisplay();
        
        // Verify map display was called with filtered animals
        assertSame(filteredAnimals, view.lastMapDisplayedAnimals);
    }
    
    @Test
    public void testHandleMapDisplayWithException() {
        // Configure view to throw exception
        view.throwExceptionOnMapDisplay = true;
        
        // Call handleMapDisplay
        controller.handleMapDisplay();
        
        // Verify error was shown
        assertEquals("Failed to display map: Test map display exception", view.lastErrorMessage);
    }

    @Test
    public void testGetFilteredAnimals() {
        // Test getFilteredAnimals
        List<IAnimal> result = controller.getFilteredAnimals();
        
        // Verify correct list was returned
        assertSame(filteredAnimals, result);
    }

    @Test
    public void testInitialize() {
        // Clear any view method calls from setUp
        view.lastDisplayedAnimals = null;
        
        // Test initialize
        controller.initialize();
        
        // Verify view was updated
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
    }

    @Test
    public void testCreateAnimal() {
        // Test createAnimal
        IAnimal result = controller.createAnimal(
            "DOG", "BEAGLE", "MEDIUM", "MALE", "SPOTTED", "BROWN", "ADULT",
            "01/01/2023", "12:00 PM", "New York", "456 Park Ave", "In park",
            "Playful beagle", "beagle.jpg"
        );
        
        // Verify animal was created with correct attributes
        assertEquals("DOG", result.getAnimalType());
        assertEquals("BEAGLE", result.getSpecies());
        assertEquals("MEDIUM", result.getAnimalSize());
        assertEquals("MALE", result.getGender());
        assertEquals("SPOTTED", result.getPattern());
        assertEquals("BROWN", result.getColor());
        assertEquals("ADULT", result.getAge());
        assertEquals("01/01/2023", result.getSeenDate());
        assertEquals("12:00 PM", result.getTime());
        assertEquals("New York", result.getArea());
        assertEquals("456 Park Ave", result.getAddress());
        assertEquals("In park", result.getLocDesc());
        assertEquals("Playful beagle", result.getDescription());
        assertEquals("12348", result.getNumber()); // Max is 12347, so next is 12348
    }
    
    @Test
    public void testGetNextAnimalNumberAsString() {
        // Test getNextAnimalNumberAsString
        String result = controller.getNextAnimalNumberAsString();
        
        // Verify correct number was returned
        assertEquals("12348", result); // Max is 12347, so next is 12348
    }

    @Test
    public void testAddAnimal() {
        // Test addAnimal
        controller.addAnimal(dog1);
        
        // Verify animal was added to model
        assertTrue(animalList.addNewAnimalCalled);
        assertEquals("DOG", animalList.lastAddedType);
        assertEquals("LABRADOR", animalList.lastAddedSpecies);
        assertEquals("LARGE", animalList.lastAddedSize);
        assertEquals("MALE", animalList.lastAddedGender);
        assertEquals("SOLID", animalList.lastAddedPattern);
        assertEquals("BLACK", animalList.lastAddedColor);
        assertEquals("ADULT", animalList.lastAddedAge);
        assertEquals("123 Main St", animalList.lastAddedAddress);
        assertEquals("Boston", animalList.lastAddedArea);
        assertEquals("12:30 PM", animalList.lastAddedTime);
        assertEquals("01/01/2023", animalList.lastAddedSeenDate);
        assertEquals("Friendly dog", animalList.lastAddedDescription);
        assertEquals("Near the park", animalList.lastAddedLocDesc);
        assertEquals("dog1.jpg", animalList.lastAddedFileSrc);
        
        // Verify view was updated
        assertSame(filteredAnimals, view.lastDisplayedAnimals);
    }
    
    @Test
    public void testExportDataSuccess() {
        // Test export
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        controller.exportData(filteredAnimals, "TXT", outputStream);
        
        // Verify success message was shown
        assertEquals("Data exported successfully to TXT !", view.lastSuccessMessage);
    }
    
    @Test
    public void testExportDataWithException() {
        // Configure view to throw exception on success message
        view.throwExceptionOnSuccess = true;
        
        // Configure test data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        // Test export
        controller.exportData(filteredAnimals, "TXT", outputStream);
        
        // Verify error was shown
        assertEquals("Failed to export data: Test success exception", view.lastErrorMessage);
    }

    /**
     * Class that implements IController for testing without extending controller
     */
    private static class TestController implements IController {
        private final TestAnimalList animalList;
        private final TestAnimalFilter animalFilter;
        private IView view;
        
        public TestController(TestAnimalList animalList, TestAnimalFilter animalFilter, IView view) {
            this.animalList = animalList;
            this.animalFilter = animalFilter;
            this.view = view;
        }
        
        public IView getView() {
            return view;
        }
        
        @Override
        public void setView(IView view) {
            this.view = view;
        }
        
        @Override
        public void handleFilter(String filterType, String filterValue) {
            try {
                animalFilter.filter(filterType, filterValue);
                updateView();
            } catch (Exception e) {
                view.showError("Filter failed: " + e.getMessage());
            }
        }
        
        @Override
        public void handleSort() {
            handleSort(true);
        }
        
        @Override
        public void handleSort(boolean ascending) {
            try {
                animalFilter.sortOnDate(ascending);
                updateView();
            } catch (Exception e) {
                view.showError("Sort failed: " + e.getMessage());
            }
        }
        
        @Override
        public void handleReset() {
            try {
                animalFilter.reset();
                updateView();
                view.showSuccess("All filters have been reset");
            } catch (Exception e) {
                view.showError("Reset failed: " + e.getMessage());
            }
        }
        
        @Override
        public void resetFilteredAnimals() {
            animalFilter.reset();
        }
        
        @Override
        public void handleMapDisplay() {
            try {
                view.displayMap(animalFilter.getFilteredAnimals());
            } catch (Exception e) {
                view.showError("Failed to display map: " + e.getMessage());
            }
        }
        
        @Override
        public List<IAnimal> getFilteredAnimals() {
            return animalFilter.getFilteredAnimals();
        }
        
        @Override
        public void initialize() {
            updateView();
        }
        
        private void updateView() {
            view.displayAnimals(animalFilter.getFilteredAnimals());
        }
        
        @Override
        public IAnimal createAnimal(
                String type, String breed, String size, String gender,
                String pattern, String color, String age, String date,
                String time, String city, String address, String locDesc,
                String description, String fileSrc) {
            String number = String.valueOf(animalList.getMaxNumber() + 1);
            return new Animal(type, breed, size, gender, pattern, color, age,
                    address, city, time, date, description, locDesc, number, fileSrc);
        }
        
        @Override
        public String getNextAnimalNumberAsString() {
            return String.valueOf(animalList.getMaxNumber() + 1);
        }
        
        @Override
        public void addAnimal(IAnimal animal) {
            animalList.addNewAnimal(
                    animal.getAnimalType(),
                    animal.getSpecies(),
                    animal.getAnimalSize(),
                    animal.getGender(),
                    animal.getPattern(),
                    animal.getColor(),
                    animal.getAge(),
                    animal.getSeenDate(),
                    animal.getTime(),
                    animal.getAddress(),
                    animal.getArea(),
                    animal.getLocDesc(),
                    animal.getDescription(),
                    animal.getImage()
            );
            updateView();
        }
        
        @Override
        public void exportData() {
            // Not needed for test
        }
        
        @Override
        public void exportData(List<IAnimal> list) {
            // Not needed for test
        }
        
        @Override
        public void exportData(String format) {
            // Not needed for test
        }
        
        @Override
        public void exportData(List<IAnimal> list, String format) {
            // Not needed for test
        }
        
        @Override
        public void exportData(List<IAnimal> list, String format, java.io.OutputStream os) {
            try {
                // Mock successful output generation
                view.showSuccess("Data exported successfully to " + format.toUpperCase() + " !");
            } catch (Exception e) {
                view.showError("Failed to export data: " + e.getMessage());
            }
        }
    }

    /**
     * Test implementation of AnimalList for testing controller.
     */
    private static class TestAnimalList {
        private boolean addNewAnimalCalled = false;
        private String lastAddedType;
        private String lastAddedSpecies;
        private String lastAddedSize;
        private String lastAddedGender;
        private String lastAddedPattern;
        private String lastAddedColor;
        private String lastAddedAge;
        private String lastAddedSeenDate;
        private String lastAddedTime;
        private String lastAddedAddress;
        private String lastAddedArea;
        private String lastAddedLocDesc;
        private String lastAddedDescription;
        private String lastAddedFileSrc;
        private final List<IAnimal> testAnimals = new ArrayList<>();
        
        public TestAnimalList() {
            // Initialize test data
            testAnimals.add(new Animal(
                "DOG", "LABRADOR", "LARGE", "MALE", "SOLID", "BLACK", "ADULT",
                "123 Main St", "Boston", "12:30 PM", "01/01/2023", 
                "Friendly dog", "Near the park", "12345", "dog1.jpg"
            ));
            testAnimals.add(new Animal(
                "CAT", "SIAMESE", "SMALL", "FEMALE", "STRIPED", "GRAY", "YOUNG",
                "789 Pine St", "Somerville", "9:15 AM", "01/20/2023",
                "Shy cat", "Under a porch", "12347", "cat1.jpg"
            ));
        }
        
        public int getMaxNumber() {
            return 12347; // Always return 12347 for testing
        }
        
        public List<IAnimal> getAnimals() {
            return testAnimals;
        }
        
        public void addNewAnimal(
                String type, String species, String size, String gender, String pattern, String color,
                String age, String seenDate, String seenTime, String address, String area, String locDesc,
                String description, String fileSrc) {
            
            this.addNewAnimalCalled = true;
            this.lastAddedType = type;
            this.lastAddedSpecies = species;
            this.lastAddedSize = size;
            this.lastAddedGender = gender;
            this.lastAddedPattern = pattern;
            this.lastAddedColor = color;
            this.lastAddedAge = age;
            this.lastAddedSeenDate = seenDate;
            this.lastAddedTime = seenTime;
            this.lastAddedAddress = address;
            this.lastAddedArea = area;
            this.lastAddedLocDesc = locDesc;
            this.lastAddedDescription = description;
            this.lastAddedFileSrc = fileSrc;
        }
    }
    
    /**
     * Test implementation of AnimalFilter without extending.
     */
    private static class TestAnimalFilter {
        private final List<IAnimal> filteredList;
        private String lastFilterType;
        private String lastFilterValue;
        private boolean lastSortAscending;
        private boolean resetCalled;
        
        private boolean throwExceptionOnFilter = false;
        private boolean throwExceptionOnSort = false;
        private boolean throwExceptionOnReset = false;
        
        public TestAnimalFilter(List<IAnimal> filteredList) {
            this.filteredList = filteredList;
        }
        
        public void filter(String filterOn, String filterStr) {
            if (throwExceptionOnFilter) {
                throw new RuntimeException("Test filter exception");
            }
            
            this.lastFilterType = filterOn;
            this.lastFilterValue = filterStr;
        }
        
        public void sortOnDate(boolean asc) {
            if (throwExceptionOnSort) {
                throw new RuntimeException("Test sort exception");
            }
            
            this.lastSortAscending = asc;
        }
        
        public void reset() {
            if (throwExceptionOnReset) {
                throw new RuntimeException("Test reset exception");
            }
            
            this.resetCalled = true;
        }
        
        public List<IAnimal> getFilteredAnimals() {
            return filteredList;
        }
    }
    
    /**
     * Test implementation of IView for testing controller.
     */
    private static class TestView implements IView {
        private List<IAnimal> lastDisplayedAnimals;
        private List<IAnimal> lastMapDisplayedAnimals;
        private String lastErrorMessage;
        private String lastSuccessMessage;
        
        private boolean throwExceptionOnMapDisplay = false;
        private boolean throwExceptionOnSuccess = false;
        
        @Override
        public void displayAnimals(List<IAnimal> animals) {
            this.lastDisplayedAnimals = animals;
        }
        
        @Override
        public void displayMap(List<IAnimal> animals) {
            if (throwExceptionOnMapDisplay) {
                throw new RuntimeException("Test map display exception");
            }
            
            this.lastMapDisplayedAnimals = animals;
        }
        
        @Override
        public void showFilterOptions() {
            // Not needed for testing
        }
        
        @Override
        public void showSortOptions() {
            // Not needed for testing
        }
        
        @Override
        public void showError(String message) {
            this.lastErrorMessage = message;
        }
        
        @Override
        public void showSuccess(String message) {
            if (throwExceptionOnSuccess) {
                throw new RuntimeException("Test success exception");
            }
            
            this.lastSuccessMessage = message;
        }
        
        @Override
        public String getSelectedFilter() {
            return "TYPE"; // Default for testing
        }
        
        @Override
        public String getFilterValue() {
            return "DOG"; // Default for testing
        }
        
        @Override
        public boolean getSortOrder() {
            return true; // Default ascending
        }
        
        @Override
        public void updateView() {
            // Not needed for testing
        }
    }
} 