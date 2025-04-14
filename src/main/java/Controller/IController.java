package Controller;

import Model.Animals.IAnimal;
import View.IView;

import java.io.OutputStream;
import java.util.List;


public interface IController {
    /** Export data to file. */
    String EXPORT_TO = "animal_data.";

    /**
     * Set View.
     * @param view an instance of View
     */
    void setView(IView view);


    /**
     * Handle filter request.
     * @param filterType filter on
     * @param filterValue filter value
     */
    void handleFilter(String filterType, String filterValue);


    /**
     * Handle sort request.
     * @param ascending ascending or not
     */
    void handleSort(boolean ascending);


    /**
     * Handle reset request.
     */
    void handleReset();


    /**
     * Resets all filters without updating the view.
     * Used whenever filters apply to achieve the effect of unfilter.
     */
    void resetFilteredAnimals();


    /**
     * Handle map display request.
     */
    void handleMapDisplay();


    /**
     * Get the current filtered list of animals.
     * @return the filtered list
     */
    List<IAnimal> getFilteredAnimals();


    /**
     * Initialize the application.
     */
    void initialize();


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
     * @param fileSrc the file source
     * @return A new animal instance
     */
    IAnimal createAnimal(
            String type, String breed, String size, String gender,
            String pattern, String color, String age, String date,
            String time, String city, String address, String locDesc,
            String description, String fileSrc
    );


    /**
     * Get the number for next animal.
     * @return the number as string
     */
    String getNextAnimalNumberAsString();

    /**
     * Adds a new animal to the model and updates the view.
     *
     * @param animal The animal to be added
     */
    void addAnimal(IAnimal animal);


    /**
     * Export animal data to a file in the specified format.
     * Default to all animals and FileOutputStream
     * @param format the format to export (xml, json, txt, csv)
     */
    void exportData(String format);


    /**
     * Export animal data to a file in the specified format.
     * Default to FileOutputStream
     * @param list list of animals to export
     * @param format the format to export (xml, json, txt, csv)
     */
    void exportData(List<IAnimal> list, String format);



    /**
     * Export animal data to a file in the specified format.
     * @param format the format to export (xml, json, txt, csv)
     */
    void exportData(List<IAnimal> list, String format, OutputStream os);
}