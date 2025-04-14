package Model.AnimalInfo.Species;

import Model.AnimalInfo.AnimalType;

/**
 * Interface representing different species of animals.
 * This interface provides a factory method to create species-specific enums based on animal type.
 */
public interface Species {
    /**
     * Factory method to get the appropriate species enum based on animal type and species name.
     * @param type The type of animal (e.g., DOG, CAT)
     * @param species The name of the species/breed
     * @return The corresponding Species enum value for the given type and species
     */
    static Species getSpeciesByType(AnimalType type, String species) {
        return switch (type) {
            case DOG -> Dogs.fromString(species);
            case CAT -> Cats.fromString(species);
            case BIRD -> Birds.fromString(species);
            case DUCK -> Ducks.fromString(species);
            case GOOSE -> Geese.fromString(species);
            case RABBIT -> Rabbits.fromString(species);
            case HAMSTER -> Hamsters.fromString(species);
            case HEDGEHOG -> Hedgehogs.fromString(species);
        };
    }
}