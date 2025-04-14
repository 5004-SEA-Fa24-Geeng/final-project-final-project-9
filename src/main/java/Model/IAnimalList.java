package Model;

import Model.Animals.IAnimal;


import java.util.List;


/**
 * An interface for animal list that store all animals as list.
 */
public interface IAnimalList {
    /** source of recorded animals. */
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
     * Creates a new animal instance with the specified attributes.
     *
     * @param type The type of animal
     * @param species The species of animal
     * @param size The size of animal
     * @param gender The gender of animal
     * @param pattern The pattern of animal
     * @param color The color of animal
     * @param age The age of animal
     * @param seenDate The date when animal was seen
     * @param seenTime The time when animal was seen
     * @param area The city where animal was seen
     * @param address The address where animal was seen
     * @param locDesc The location description
     * @param description The general description
     * @param fileSrc the file source
     * @return A new animal instance
     */
    void addNewAnimal(String type, String species, String size, String gender, String pattern, String color,
                      String age, String seenDate, String seenTime, String address, String area, String locDesc,
                      String description, String fileSrc);


    /**
     * Write all data to csv database.
     */
    void write();

}