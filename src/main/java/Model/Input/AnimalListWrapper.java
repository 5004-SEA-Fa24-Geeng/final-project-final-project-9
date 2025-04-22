package Model.Input;

import java.util.List;
import Model.Animals.IAnimal;

/**
 * A simple wrapper class for a list of IAnimal objects. Useful for serialization or transferring
 * animal lists as a single object.
 */
public class AnimalListWrapper {
    /** The list of animals wrapped by this class. */
    private List<IAnimal> animals;

    /**
     * Default constructor for AnimalListWrapper.
     */
    public AnimalListWrapper() {

    }

    /**
     * Constructs an AnimalListWrapper with the specified list of animals.
     *
     * @param animals the list of animals to wrap
     */
    public AnimalListWrapper(List<IAnimal> animals) {
        this.animals = animals;
    }

    /**
     * Gets the list of animals.
     *
     * @return the list of animals
     */
    public List<IAnimal> getAnimals() {
        return animals;
    }

    /**
     * Sets the list of animals.
     *
     * @param animals the new list of animals
     */
    public void setAnimals(List<IAnimal> animals) {
        this.animals = animals;
    }
}
