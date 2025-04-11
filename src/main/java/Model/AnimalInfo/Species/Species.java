package Model.AnimalInfo.Species;

import Model.AnimalInfo.AnimalType;

public interface Species {
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
