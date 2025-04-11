package Model;

import Model.Animals.IAnimal;

import java.util.List;

public class AnimalList implements IAnimalList {
    /** Original list of animals. */
    private List<IAnimal> animalList;

    /** Filtered list for display in GUI. */
    private List<IAnimal> filtered;

    /**
     * Constructor for animal list.
     * Load csv data to list.
     */
    public AnimalList() {
        animalList = IAnimalList.readFromCsv();
        filtered = List.copyOf(animalList);
    }

    @Override
    public List<IAnimal> getAnimals() {
        return animalList;
    }

    @Override
    public List<IAnimal> getFiltered() {
        return filtered;
    }

    @Override
    public int count() {
        return animalList.size();
    }

    @Override
    public int getMaxNumber() {
        return animalList.stream().map(IAnimal::getNumber).max(Integer::compareTo).orElse(0);
    }

    @Override
    public void addNewAnimal(String type, String species, String size, String gender, String pattern, String color,
                             String age, String seenDate, String seenTime, String address, String area, String locDesc,
                             String description) {
        try {
            animalList.add(IAnimal.newAnimal(type, species, size, gender, pattern, color, age, seenDate,
                    seenTime, address, area, locDesc, description, getMaxNumber() + 1));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resetFiltered() {
        filtered = List.copyOf(animalList);
    }

    @Override
    public void write() {
        return;
    }
}
