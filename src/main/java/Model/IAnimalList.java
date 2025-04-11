package Model;

import Model.Animals.IAnimal;
import Model.output.AnimalCSVReader;
import Model.output.AnimalCSVWriter;

import java.util.List;

public interface IAnimalList {
    String DATABASE = "data/posted_animals.csv";

    /**
     * Get the list of animals.
     * @return the list of animals
     */
    List<IAnimal> getAnimals();

    /**
     * Count the number of posted animals.
     * @return the number of animals
     */
    int count();

    /**
     * Get the max number of existing animals and use it to add new animals.
     * @return the max animal number
     */
    int getMaxNumber();

    /**
     * Add/Report a new posted animal to list,
     */
    void addNewAnimal(String type, String species, String size, String gender, String pattern, String color,
                      String age, String seenDate, String seenTime, String address, String area, String locDesc,
                      String description);


    /**
     * Write all data to csv database.
     */
    void write();

    /**
     * read csv animal data into list of IAnimals
     * @return the list of animals
     */
    static List<IAnimal> readFromCsv() {
        AnimalCSVReader reader = new AnimalCSVReader(DATABASE);
        return reader.readAnimals();
    }


}
