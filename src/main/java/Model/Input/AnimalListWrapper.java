package Model.Input;

import java.util.List;
import Model.Animals.IAnimal;

public class AnimalListWrapper {
    private List<IAnimal> animals;

    public AnimalListWrapper() {

    }

    public AnimalListWrapper(List<IAnimal> animals) {
        this.animals = animals;
    }

    public List<IAnimal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<IAnimal> animals) {
        this.animals = animals;
    }
}