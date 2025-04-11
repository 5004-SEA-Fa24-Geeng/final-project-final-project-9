package Model;

import Model.Animals.IAnimal;

import java.util.List;

public interface IAnimalList {
    String DATABASE = "data/posted_animals.csv";

    /**
     * Get the list of animals.
     * @return the list of animals
     */
    List<IAnimal> getAnimals();


    /**
     * Get the filtered list of animals for display.
     * @return the filtered list.
     */
    List<IAnimal> getFiltered();

    /**
     * Count the number of posted animals.
     * @return the number of animals
     */
    int count();

    /**
     * Get the max number of existing animals and use it for creat new animals.
     * @return the max animal number
     */
    int getMaxNumber();

    /**
     * Add a new posted animal to list,
     */
    void addNewAnimal(String type, String species, String size, String gender, String pattern, String color,
                      String age, String seenDate, String seenTime, String address, String area, String locDesc,
                      String description);



    /**
     * Reset the filtered list.
     */
    void resetFiltered();

    /**
     * Write all data to csv database.
     */
    void write();

    /**
     * read csv animal data into list of IAnimals
     * @return the list of animals
     */
    static List<IAnimal> readFromCsv() {
        return List.of();
    }


}
