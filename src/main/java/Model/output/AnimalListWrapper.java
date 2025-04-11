package Model.output;

import java.util.List;
import Model.Animals.IAnimal;

public class AnimalListWrapper {
    private List<IAnimal> animals;

    public AnimalListWrapper() {
        // 默认构造函数，用于XML反序列化
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