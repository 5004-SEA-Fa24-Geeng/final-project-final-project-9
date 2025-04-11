package Model.output;

import java.util.List;

import Model.PostedAnimal;

public class AnimalListWrapper {
    private List<PostedAnimal> animals;

    public AnimalListWrapper() {
        // 默认构造函数，用于XML反序列化
    }

    public AnimalListWrapper(List<PostedAnimal> animals) {
        this.animals = animals;
    }

    public List<PostedAnimal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<PostedAnimal> animals) {
        this.animals = animals;
    }
} 