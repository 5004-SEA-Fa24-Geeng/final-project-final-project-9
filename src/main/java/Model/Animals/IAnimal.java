package Model.Animals;

/**
 * Interface representing an animal with its various attributes and characteristics.
 * This interface defines the contract for animal objects in the system.
 */
public interface IAnimal {
    /** The source of animal images. */
    String IMG_SRC = "data/image/"; // e.g.   .../image/50.img


    /**
     * Gets the type of the animal.
     * @return The animal type
     */
    String getAnimalType();

    /**
     * Gets the species of the animal.
     * @return The animal species
     */
    String getSpecies();


    /**
     * Gets the size of the animal.
     * @return The animal size
     */
    String getAnimalSize();

    /**
     * Gets the gender of the animal.
     * @return The animal gender
     */
    String getGender();

    /**
     * Gets the pattern of the animal.
     * @return The animal pattern
     */
    String getPattern();

    /**
     * Gets the color of the animal.
     * @return The animal color
     */
    String getColor();

    /**
     * Gets the age of the animal.
     * @return The animal age
     */
    String getAge();

    /**
     * Gets the date when the animal was seen.
     * @return The date of sighting
     */
    String getSeenDate();

    /**
     * Gets the time when the animal was seen.
     * @return The time of sighting
     */
    String getTime();

    /**
     * Gets the area where the animal was seen.
     * @return The area of sighting
     */
    String getArea();

    /**
     * Gets the address where the animal was seen.
     * @return The address of sighting
     */
    String getAddress();

    /**
     * Gets the location description where the animal was seen.
     * @return The location description
     */
    String getLocDesc();

    /**
     * Gets the general description of the animal.
     * @return The animal description
     */
    String getDescription();

    /**
     * Gets the identification number of the animal.
     * @return The animal number
     */
    String getNumber();

    /**
     * Gets the image file path of the animal.
     * @return The image file path
     */
    String getImage();
}
