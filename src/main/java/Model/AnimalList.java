package Model;

import Model.Animals.Animal;
import Model.Animals.IAnimal;
import Model.output.AnimalCSVWriter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnimalList implements IAnimalList {
    /** Thread-safe list of animals. */
    private final List<IAnimal> animalList;

    /**
     * Constructor for animal list.
     * Load csv data to list.
     */
    public AnimalList() {
        animalList = new CopyOnWriteArrayList<>(IAnimalList.readFromCsv());
    }

    @Override
    public List<IAnimal> getAnimals() {
        return List.copyOf(animalList);
    }

    @Override
    public int count() {
        return animalList.size();
    }

    @Override
    public int getMaxNumber() {
        return animalList.stream()
                .mapToInt(animal -> Integer.parseInt(animal.getNumber()))
                .max()
                .orElse(0);
    }

    @Override
    public void addNewAnimal(String type, String species, String size, String gender, String pattern, String color,
                             String age, String seenDate, String seenTime, String address, String area, String locDesc,
                             String description) {
        String newNumber = String.valueOf(getMaxNumber() + 1);
        animalList.add(new Animal(type, species, size, gender, pattern, color, age, address,
                area, seenTime, seenDate, description, locDesc, newNumber));
        write();
    }

    @Override
    public void write() {
        AnimalCSVWriter writer = new AnimalCSVWriter(IAnimalList.DATABASE);
        writer.writeAnimals(animalList);
    }
}