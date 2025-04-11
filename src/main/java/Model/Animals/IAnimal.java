package Model.Animals;

import Model.AnimalInfo.*;
import Model.AnimalInfo.Species.*;


public interface IAnimal {
    /** The source of animal images. */
    String imgSrc = "data/animalImage/"; // e.g.   .../animalImage/dog/50.img



    static IAnimal newAnimal(String type, String species, String size, String gender, String pattern, String color,
                             String age, String seenDate, String seenTime, String address, String area, String locDesc,
                             String description, int number) {

        AnimalType animal = AnimalType.fromString(type);
        if (animal == null) {
            throw new IllegalArgumentException("Wrong animal type.");
        }
        return switch (animal) {
            case DOG -> new Dog(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case CAT -> new Cat(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case RABBIT -> new Rabbit(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case BIRD -> new Bird(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case HAMSTER -> new Hamster(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case DUCK -> new Duck(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case HEDGEHOG -> new Hedgehog(animal, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            default -> null;
        };
    }

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

    AnimalType getAnimalType();
    void setAnimalType(String type);

    Species getSpecies();
    void setSpecies(String species);

    Size getAnimalSize();
    void setAnimalSize(String size);

    Gender getGender();
    void setGender(String gender);


    Pattern getPattern();
    void setPattern(String pattern);

    Color getAnimalColor();
    void setAnimalColor(String color);

    Age getAnimalAge();
    void setAnimalAge(String age);

    String getSeenDate();
    void setSeenDate(String seenDate);

    String getSeenTime();
    void setSeenTime(String seenTime);

    String getAddress();
    void setAddress(String address);

    Area getArea();
    void setArea(String Area);

    String getLocDesc();
    void setLocDesc(String LocDesc);

    String getDescription();
    void setDescription(String desc);

    int getNumber();

    String getImage();



}
