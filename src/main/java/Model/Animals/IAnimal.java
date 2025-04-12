package Model.Animals;

public interface IAnimal {
    /** The source of animal images. */
    String imgSrc = "data/animalImage/"; // e.g.   .../animalImage/dog/50.img

    String getAnimalType();
    String getSpecies();
    String getAnimalSize();
    String getGender();
    String getPattern();
    String getColor();
    String getAge();
    String getSeenDate();
    String getTime();
    String getArea();
    String getAddress();
    String getLocDesc();
    String getDescription();
    String getNumber();
    String getImage();
}