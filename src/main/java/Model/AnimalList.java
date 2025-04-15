package Model;

import Model.Animals.Animal;
import Model.Animals.IAnimal;
import Model.Input.AnimalCSVReader;
import Model.Input.AnimalCSVWriter;


import java.util.List;

/**
 * A class to store the original list of all animals.
 */
public class AnimalList implements IAnimalList {
    /** Original list of animals. */
    private final List<IAnimal> animalList;

    /**
     * Constructor for animal list.
     * Load all animals from CSV file.
     */
    public AnimalList() {
        AnimalCSVReader reader = new AnimalCSVReader(DATABASE);
        animalList = reader.readAnimals();
    }

    @Override
    public List<IAnimal> getAnimals() {
        return animalList;
    }

    @Override
    public int count() {
        return animalList.size();
    }


    @Override
    public int getMaxNumber() {
        return animalList.stream()
                .map(animal -> Integer.parseInt(animal.getNumber()))
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public void addNewAnimal(String type, String species, String size, String gender, String pattern, String color,
                           String age, String seenDate, String seenTime, String address, String area, String locDesc,
                           String description, String fileSrc) {
        try {
            String newNumber = String.valueOf(getMaxNumber() + 1);
            animalList.add(new Animal(type, species, size, gender, pattern, color, age, address,
                    area, seenTime, seenDate, description, locDesc, newNumber, fileSrc));
            write();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write() {
        AnimalCSVWriter writer = new AnimalCSVWriter(IAnimalList.DATABASE);
        writer.writeAnimals(animalList);
    }
}