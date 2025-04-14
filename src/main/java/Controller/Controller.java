package Controller;

import java.io.*;
import java.util.List;

import Model.AnimalFilter;
import Model.AnimalList;
import Model.Animals.Animal;
import Model.Animals.IAnimal;
import Model.Formatter.AnimalOutputGenerator;
import Model.Formatter.IOutputGenerator;
import Model.Formatter.OutputFormat;
import View.IView;

/**
 * Controller class that handles the interaction between the view and model.
 * It processes user inputs from the view and updates the model accordingly.
 * It also manages the filtering and sorting of animal data.
 */
public class Controller implements IController {
    /**
     * The view component of the application.
     */
    private IView view;

    /**
     * The model containing the list of animals.
     */
    private AnimalList model;

    /**
     * The filter used to filter and sort animals.
     */
    private AnimalFilter filter;

    private final IOutputGenerator generator = new AnimalOutputGenerator();


    /**
     * Constructs a new Controller with the specified model and filter.
     *
     * @param model  The animal list model
     * @param filter The animal filter
     */
    public Controller(AnimalList model, AnimalFilter filter) {
        this.model = model;
        this.filter = filter;
    }


    @Override
    public void setView(IView view) {
        this.view = view;
    }


    @Override
    public void handleFilter(String filterType, String filterValue) {
        try {
            filter.filter(filterType, filterValue);
            updateView();
        } catch (Exception e) {
            view.showError("Filter failed: " + e.getMessage());
        }
    }


    @Override
    public void handleSort(boolean ascending) {
        try {
            filter.sortOnDate(ascending);
            updateView();
        } catch (Exception e) {
            view.showError("Sort failed: " + e.getMessage());
        }
    }


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


    @Override
    public void resetFilteredAnimals() {
        filter.reset();
    }


    @Override
    public void handleMapDisplay() {
        try {
            view.displayMap(filter.getFilteredAnimals());
        } catch (Exception e) {
            view.showError("Failed to display map: " + e.getMessage());
        }
    }


    @Override
    public List<IAnimal> getFilteredAnimals() {
        return filter.getFilteredAnimals();
    }


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


    @Override
    public IAnimal createAnimal(
            String type, String breed, String size, String gender,
            String pattern, String color, String age, String date,
            String time, String city, String address, String locDesc,
            String description, String fileSrc) {
        String number = String.valueOf(model.getMaxNumber() + 1);
        return new Animal(type, breed, size, gender, pattern, color, age,
                address, city, time, date, description, locDesc, number, fileSrc);
    }

    @Override
    public String getNextAnimalNumberAsString() {
        return String.valueOf(model.getMaxNumber() + 1);
    }


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
                animal.getAddress(),
                animal.getArea(),
                animal.getTime(),
                animal.getSeenDate(),
                animal.getDescription(),
                animal.getLocDesc(),
                animal.getImage()
        );
        updateView();
    }


    @Override
    public void exportData(String format) {
        try (OutputStream fos = new FileOutputStream(EXPORT_TO + format)) {
            exportData(model.getAnimals(), format, fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void exportData(List<IAnimal> list, String format) {
        try (OutputStream fos = new FileOutputStream(EXPORT_TO + format)) {
            exportData(list, format, fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void exportData(List<IAnimal> list, String format, OutputStream os) {
        try {
            generator.generateOutput(list, OutputFormat.fromString(format), os);
            view.showSuccess("Data exported successfully to " + format.toUpperCase() + " !");
        } catch (Exception e) {
            view.showError("Failed to export data: " + e.getMessage());
        }
    }
}