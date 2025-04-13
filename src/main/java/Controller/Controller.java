package Controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import Model.AnimalFilter;
import Model.AnimalList;
import Model.Animals.Animal;
import Model.Animals.IAnimal;
import View.IView;

/**
 * Controller class that handles the interaction between the view and model.
 * It processes user inputs from the view and updates the model accordingly.
 * It also manages the filtering and sorting of animal data.
 */
public class Controller implements IController {
    /** The view component of the application. */
    private IView view;

    /** The model containing the list of animals. */
    private AnimalList model;

    /** The filter used to filter and sort animals. */
    private AnimalFilter filter;


    /**
     * Constructs a new Controller with the specified model and filter.
     *
     * @param model The animal list model
     * @param filter The animal filter
     */
    public Controller(AnimalList model, AnimalFilter filter) {
        this.model = model;
        this.filter = filter;
    }

    /**
     * Sets the view component for this controller.
     *
     * @param view The view to be set
     */
    @Override
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Handles the filtering of animals based on specified criteria.
     *
     * @param filterType The type of filter to apply
     * @param filterValue The value to filter by
     */
    @Override
    public void handleFilter(String filterType, String filterValue) {
        try {
            filter.filter(filterType, filterValue);
            updateView();
        } catch (Exception e) {
            view.showError("Filter failed: " + e.getMessage());
        }
    }

    /**
     * Handles the sorting of animals by date.
     *
     * @param ascending True for ascending order, false for descending
     */
    @Override
    public void handleSort(boolean ascending) {
        try {
            filter.sortOnDate(ascending);
            updateView();
        } catch (Exception e) {
            view.showError("Sort failed: " + e.getMessage());
        }
    }

    /**
     * Resets all filters to their default state.
     */
    @Override
    public void handleReset() {
        try {
            filter.reset();
            updateView();
            view.showSuccess("All filters have been reset");
        } catch (Exception e) {
            view.showError("Reset failed: " + e.getMessage());
        }
    }

    /**
     * Displays the map view with filtered animals.
     */
    @Override
    public void handleMapDisplay() {
        try {
            view.displayMap(filter.getFilteredAnimals());
        } catch (Exception e) {
            view.showError("Failed to display map: " + e.getMessage());
        }
    }

    /**
     * Returns the current list of filtered animals.
     *
     * @return List of filtered animals
     */
    @Override
    public List<IAnimal> getFilteredAnimals() {
        return filter.getFilteredAnimals();
    }

    /**
     * Initializes the controller and updates the view.
     */
    @Override
    public void initialize() {
        updateView();
    }

    /**
     * Updates the view with the current filtered animals.
     */
    private void updateView() {
        view.displayAnimals(filter.getFilteredAnimals());
    }

    /**
     * Creates a new animal instance with the specified attributes.
     *
     * @param type The type of animal
     * @param breed The species of animal
     * @param size The size of animal
     * @param gender The gender of animal
     * @param pattern The pattern of animal
     * @param color The color of animal
     * @param age The age of animal
     * @param date The date when animal was seen
     * @param time The time when animal was seen
     * @param city The city where animal was seen
     * @param address The address where animal was seen
     * @param locDesc The location description
     * @param description The general description
     * @param image The image path
     * @return A new animal instance
     */
    @Override
    public IAnimal createAnimal(
            String type, String breed, String size, String gender,
            String pattern, String color, String age, String date,
            String time, String city, String address, String locDesc,
            String description, String image) {
        return new Animal(type, breed, size, gender, pattern, color, age,
                address, city, time, date, description, locDesc, image);
    }

    /**
     * Adds a new animal to the model and updates the view.
     *
     * @param animal The animal to be added
     */
    @Override
    public void addAnimal(IAnimal animal) {
        model.addNewAnimal(
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
                animal.getDescription()
        );
        updateView();
    }

    /**
     * Load filtered animals based on the filter criteria
     * @param filterCriteria The filter criteria to apply
     */
    public void loadFilteredAnimals(String filterCriteria) {
        model.loadFilteredAnimals(filterCriteria);
        updateView();
    }

    @Override
    public void exportData(String format) {
        try {
            String fileName = "animal_data." + format;
            List<IAnimal> animals = model.getAnimals();
            
            switch (format) {
                case "xml":
                    exportToXML(animals, fileName);
                    break;
                case "json":
                    exportToJSON(animals, fileName);
                    break;
                case "txt":
                    exportToTXT(animals, fileName);
                    break;
                case "csv":
                    exportToCSV(animals, fileName);
                    break;
                default:
                    view.showError("Unsupported export format: " + format);
                    return;
            }
            view.showSuccess("Data exported successfully to " + fileName);
        } catch (Exception e) {
            view.showError("Failed to export data: " + e.getMessage());
        }
    }

    private void exportToXML(List<IAnimal> animals, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<animals>");
            for (IAnimal animal : animals) {
                writer.println("  <animal>");
                writer.println("    <type>" + animal.getAnimalType() + "</type>");
                writer.println("    <breed>" + animal.getSpecies() + "</breed>");
                writer.println("    <size>" + animal.getAnimalSize() + "</size>");
                writer.println("    <gender>" + animal.getGender() + "</gender>");
                writer.println("    <pattern>" + animal.getPattern() + "</pattern>");
                writer.println("    <color>" + animal.getColor() + "</color>");
                writer.println("    <age>" + animal.getAge() + "</age>");
                writer.println("    <date>" + animal.getSeenDate() + "</date>");
                writer.println("    <time>" + animal.getTime() + "</time>");
                writer.println("    <area>" + animal.getArea() + "</area>");
                writer.println("    <address>" + animal.getAddress() + "</address>");
                writer.println("    <locationDesc>" + animal.getLocDesc() + "</locationDesc>");
                writer.println("    <description>" + animal.getDescription() + "</description>");
                writer.println("  </animal>");
            }
            writer.println("</animals>");
        }
    }

    private void exportToJSON(List<IAnimal> animals, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("{");
            writer.println("  \"animals\": [");
            for (int i = 0; i < animals.size(); i++) {
                IAnimal animal = animals.get(i);
                writer.println("    {");
                writer.println("      \"type\": \"" + animal.getAnimalType() + "\",");
                writer.println("      \"breed\": \"" + animal.getSpecies() + "\",");
                writer.println("      \"size\": \"" + animal.getAnimalSize() + "\",");
                writer.println("      \"gender\": \"" + animal.getGender() + "\",");
                writer.println("      \"pattern\": \"" + animal.getPattern() + "\",");
                writer.println("      \"color\": \"" + animal.getColor() + "\",");
                writer.println("      \"age\": \"" + animal.getAge() + "\",");
                writer.println("      \"date\": \"" + animal.getSeenDate() + "\",");
                writer.println("      \"time\": \"" + animal.getTime() + "\",");
                writer.println("      \"area\": \"" + animal.getArea() + "\",");
                writer.println("      \"address\": \"" + animal.getAddress() + "\",");
                writer.println("      \"locationDesc\": \"" + animal.getLocDesc() + "\",");
                writer.println("      \"description\": \"" + animal.getDescription() + "\"");
                writer.println("    }" + (i < animals.size() - 1 ? "," : ""));
            }
            writer.println("  ]");
            writer.println("}");
        }
    }

    private void exportToTXT(List<IAnimal> animals, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (IAnimal animal : animals) {
                writer.println("Animal Information:");
                writer.println("Type: " + animal.getAnimalType());
                writer.println("Breed: " + animal.getSpecies());
                writer.println("Size: " + animal.getAnimalSize());
                writer.println("Gender: " + animal.getGender());
                writer.println("Pattern: " + animal.getPattern());
                writer.println("Color: " + animal.getColor());
                writer.println("Age: " + animal.getAge());
                writer.println("Date Seen: " + animal.getSeenDate());
                writer.println("Time Seen: " + animal.getTime());
                writer.println("Area: " + animal.getArea());
                writer.println("Address: " + animal.getAddress());
                writer.println("Location Description: " + animal.getLocDesc());
                writer.println("Description: " + animal.getDescription());
                writer.println("----------------------------------------");
            }
        }
    }

    private void exportToCSV(List<IAnimal> animals, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header
            writer.println("Type,Breed,Size,Gender,Pattern,Color,Age,Date,Time,Area,Address,LocationDesc,Description");
            
            // Write data
            for (IAnimal animal : animals) {
                writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    escapeCSV(animal.getAnimalType()),
                    escapeCSV(animal.getSpecies()),
                    escapeCSV(animal.getAnimalSize()),
                    escapeCSV(animal.getGender()),
                    escapeCSV(animal.getPattern()),
                    escapeCSV(animal.getColor()),
                    escapeCSV(animal.getAge()),
                    escapeCSV(animal.getSeenDate()),
                    escapeCSV(animal.getTime()),
                    escapeCSV(animal.getArea()),
                    escapeCSV(animal.getAddress()),
                    escapeCSV(animal.getLocDesc()),
                    escapeCSV(animal.getDescription())
                ));
            }
        }
    }

    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        value = value.replace("\"", "\"\"");
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = "\"" + value + "\"";
        }
        return value;
    }
} 