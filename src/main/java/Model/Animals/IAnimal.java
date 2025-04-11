package Model.Animals;

import Model.AnimalInfo.*;

public interface IAnimal {
    /** Number for next animal. Increment by 1 when new animal is added. */
    int animalNumber = 51; // Already have 50 animals in database, so next is 51

    /** The source of animal images. */
    String imgSrc = "./data/animalImage/"; // e.g.   .../animalImage/dog/50.img



    default IAnimal newAnimal(String type, String species, String size, String gender, String pattern, String color,
                              String age, String seenDate, String seenTime, String address, String area, String locDesc,
                              String description) {

        AnimalType animal = AnimalType.fromString(type);
        switch (animal) {
            case DOG:
                return new Dog(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            case CAT:
                return new Cat(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            case RABBIT:
                return new Rabbit(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            case BIRD:
                return new Bird(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            case HAMSTER:
                return new Hamster(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            case DUCK:
                return new Duck(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            case HEDGEHOG:
                return new Hedgehog(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                        address, area, locDesc, description, animalNumber);
            default:
                return null;
        }
    }

    AnimalType getAnimalType();

    String getSpecies();

    Size getAnimalSize();

    Gender getGender();

    Pattern getPattern();

    Color getAnimalColor();

    Age getAnimalAge();

    String getSeenDate();

    String getSeenTime();

    String getAddress();

    Area getArea();

    String getloDescription();

    String getDescription();

    public String getImage();

    public int getNumber();



}
