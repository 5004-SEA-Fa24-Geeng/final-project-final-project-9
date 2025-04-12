package Model;

import Model.Animals.Animal;
import Model.Animals.IAnimal;
import Model.output.AnimalCSVReader;
import Model.output.AnimalCSVWriter;

import java.util.ArrayList;
import java.util.List;

public class AnimalList implements IAnimalList {
    /** Original list of animals. */
    private List<IAnimal> animalList;

    /**
     * Constructor for animal list.
     * Load all animals from CSV file.
     */
    public AnimalList() {
        AnimalCSVReader reader = new AnimalCSVReader(DATABASE);
        animalList = reader.readAnimals();
    }

    /**
     * Load filtered animals from CSV file.
     * @param filterCriteria the criteria to filter animals
     */
    @Override
    public void loadFilteredAnimals(String filterCriteria) {
        AnimalCSVReader reader = new AnimalCSVReader(DATABASE);
        List<IAnimal> allAnimals = reader.readAnimals();
        
        // Clear current list
        animalList.clear();
        
        // Add animals that match the filter criteria
        for (IAnimal animal : allAnimals) {
            if (matchesFilter(animal, filterCriteria)) {
                animalList.add(animal);
            }
        }
    }

    /**
     * Check if an animal matches the filter criteria.
     * @param animal the animal to check
     * @param criteria the filter criteria
     * @return true if the animal matches the criteria
     */
    private boolean matchesFilter(IAnimal animal, String criteria) {
        // Add your filter logic here
        // For example:
        return animal.getAnimalType().equalsIgnoreCase(criteria) ||
               animal.getSpecies().equalsIgnoreCase(criteria) ||
               animal.getAnimalSize().equalsIgnoreCase(criteria) ||
               animal.getGender().equalsIgnoreCase(criteria) ||
               animal.getPattern().equalsIgnoreCase(criteria) ||
               animal.getColor().equalsIgnoreCase(criteria) ||
               animal.getAge().equalsIgnoreCase(criteria) ||
               animal.getArea().equalsIgnoreCase(criteria);
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
            animalList.add(new Animal(type, species, size, gender, pattern, color, age, address,
                    area, seenTime, seenDate, description, locDesc, newNumber));
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