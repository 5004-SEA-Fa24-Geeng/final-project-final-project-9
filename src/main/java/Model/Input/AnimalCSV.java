package Model.Input;

import Model.Animals.Animal;
import Model.Animals.IAnimal;
import com.opencsv.bean.CsvBindByName;

/**
 * Represents a row of animal data from a CSV file. Used for mapping CSV columns to Java fields
 * using OpenCSV. Provides a method to convert this data into an IAnimal object.
 */
public class AnimalCSV {
    /**
     * The type of the animal (e.g., Dog, Cat).
     */
    @CsvBindByName(column = "AnimalType")
    private String animalType;

    /**
     * The species of the animal.
     */
    @CsvBindByName(column = "Species")
    private String species;

    /**
     * The size of the animal (e.g., Small, Medium, Large).
     */
    @CsvBindByName(column = "AnimalSize")
    private String animalSize;

    /**
     * The gender of the animal.
     */
    @CsvBindByName(column = "Gender")
    private String gender;

    /**
     * The pattern of the animal's coat.
     */
    @CsvBindByName(column = "Pattern")
    private String pattern;

    /**
     * The color of the animal.
     */
    @CsvBindByName(column = "Color")
    private String color;

    /**
     * The age of the animal.
     */
    @CsvBindByName(column = "Age")
    private String age;

    /**
     * The address where the animal was found or reported.
     */
    @CsvBindByName(column = "Address")
    private String address;

    /**
     * The city where the animal was found or reported.
     */
    @CsvBindByName(column = "City")
    private String city;

    /**
     * The time when the animal was seen or reported.
     */
    @CsvBindByName(column = "Time")
    private String time;

    /**
     * The date when the animal was seen or reported.
     */
    @CsvBindByName(column = "Date")
    private String date;

    /**
     * The description of the animal.
     */
    @CsvBindByName(column = "AnimalDescription")
    private String description;

    /**
     * Additional description of the location where the animal was found.
     */
    @CsvBindByName(column = "LocationDescription")
    private String locDesc;

    /**
     * The contact number or reference number for the animal.
     */
    @CsvBindByName(column = "Number")
    private String number;

    /**
     * The image URL or path for the animal.
     */
    @CsvBindByName(column = "Image")
    private String image;

    /**
     * Converts this CSV data to an IAnimal object.
     *
     * @return an IAnimal object with the data from this CSV row
     */
    public IAnimal toAnimal() {
        return new Animal(
                animalType,
                species,
                animalSize,
                gender,
                pattern,
                color,
                age,
                address,
                city,
                time,
                date,
                description,
                locDesc,
                number,
                image
        );
    }
}
