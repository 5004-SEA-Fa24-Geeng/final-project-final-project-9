package Model.Animals;

/**
 * A concrete implementation of the IAnimal interface representing an animal with its attributes.
 * This class stores and provides access to various characteristics of an animal.
 */
public class Animal implements IAnimal {
    /** The type of the animal (e.g., dog, cat). */
    private final String type;
    /** The species of the animal. */
    private final String species;
    /** The size of the animal (e.g., small, medium, large). */
    private final String size;
    /** The gender of the animal. */
    private final String gender;
    /** The pattern of the animal's coat/fur. */
    private final String pattern;
    /** The color of the animal. */
    private final String color;
    /** The age of the animal. */
    private final String age;
    /** The date when the animal was seen. */
    private final String seenDate;
    /** The time when the animal was seen. */
    private final String time;
    /** The address where the animal was seen. */
    private final String address;
    /** The area/city where the animal was seen. */
    private final String area;
    /** The location description where the animal was seen. */
    private final String locDesc;
    /** The general description of the animal. */
    private final String description;
    /** The identification number of the animal. */
    private final String number;
    /** The image file path of the animal. */
    private final String image;


    /**
     * Creates a new animal instance with the specified attributes.
     *
     * @param type The type of animal
     * @param species The species of animal
     * @param size The size of animal
     * @param gender The gender of animal
     * @param pattern The pattern of animal
     * @param color The color of animal
     * @param age The age of animal
     * @param seenDate The date when animal was seen
     * @param time The time when animal was seen
     * @param area The city where animal was seen
     * @param address The address where animal was seen
     * @param locDesc The location description
     * @param description The general description
     * @param number the number of this animal
     * @param fileSrc the file source of this image
     */
    public Animal(String type, String species, String size, String gender, String pattern, String color,
                 String age, String address, String area, String time, String seenDate, String description,
                 String locDesc, String number, String fileSrc) {
        this.type = type;
        this.species = species;
        this.size = size;
        this.gender = gender;
        this.pattern = pattern;
        this.color = color;
        this.age = age;
        this.address = address;
        this.area = area;
        this.time = time;
        this.seenDate = seenDate;
        this.description = description;
        this.locDesc = locDesc;
        this.number = number;
        this.image = fileSrc;
    }



    @Override
    public String getAnimalType() {
        return type;
    }


    @Override
    public String getSpecies() {
        return species;
    }


    @Override
    public String getAnimalSize() {
        return size;
    }


    @Override
    public String getGender() {
        return gender;
    }


    @Override
    public String getPattern() {
        return pattern;
    }


    @Override
    public String getColor() {
        return color;
    }


    @Override
    public String getAge() {
        return age;
    }


    @Override
    public String getSeenDate() {
        return seenDate;
    }


    @Override
    public String getTime() {
        return time;
    }


    @Override
    public String getArea() {
        return area;
    }


    @Override
    public String getAddress() {
        return address;
    }


    @Override
    public String getLocDesc() {
        return locDesc;
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public String getNumber() {
        return number;
    }


    @Override
    public String getImage() {
        return image;
    }
}
