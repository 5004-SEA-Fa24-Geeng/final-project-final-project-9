package Model;

import Model.Animals.IAnimal;
import Model.output.AnimalCSVWriter;

import java.util.List;

public class AnimalList implements IAnimalList {
    /** Original list of animals. */
    private List<IAnimal> animalList;

    /**
     * Constructor for animal list.
     * Load csv data to list.
     */
    public AnimalList() {
        animalList = IAnimalList.readFromCsv();
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
                             String description) {
        try {
            String newNumber = String.valueOf(getMaxNumber() + 1);
            animalList.add(IAnimal.newAnimal(type, species, size, gender, pattern, color, age, seenDate,
                    seenTime, address, area, locDesc, description, newNumber));
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
