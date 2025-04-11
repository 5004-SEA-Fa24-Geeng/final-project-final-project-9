package Model.Animals;

import Model.AnimalInfo.*;

public interface IAnimal {
    /** The source of animal images. */
    String imgSrc = "data/animalImage/"; // e.g.   .../animalImage/dog/50.img

    static IAnimal newAnimal(String type, String species, String size, String gender, String pattern, String color,
                             String age, String seenDate, String seenTime, String address, String area, String locDesc,
                             String description, String number) {

        AnimalType animal = AnimalType.fromString(type);
        if (animal == null) {
            throw new IllegalArgumentException("Wrong animal type.");
        }
        return switch (animal) {
            case DOG -> new Dog(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case CAT -> new Cat(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case RABBIT -> new Rabbit(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case BIRD -> new Bird(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case HAMSTER -> new Hamster(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case DUCK -> new Duck(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            case HEDGEHOG -> new Hedgehog(type, species, size, gender, pattern, color, age, seenDate, seenTime,
                    address, area, locDesc, description, number);
            default -> null;
        };
    }

    String getAnimalType();
    void setAnimalType(String type);

    String getSpecies();
    void setSpecies(String species);

    String getAnimalSize();
    void setAnimalSize(String size);

    String getGender();
    void setGender(String gender);

    String getPattern();
    void setPattern(String pattern);

    String getColor();
    void setAnimalColor(String color);

    String getAge();
    void setAnimalAge(String age);

    String getSeenDate();
    void setSeenDate(String seenDate);

    String getTime();
    void setSeenTime(String seenTime);

    String getAddress();
    void setAddress(String address);

    String getArea();
    void setArea(String Area);

    String getLocDesc();
    void setLocDesc(String LocDesc);

    String getDescription();
    void setDescription(String desc);

    String getNumber();

    String getImage();
}
