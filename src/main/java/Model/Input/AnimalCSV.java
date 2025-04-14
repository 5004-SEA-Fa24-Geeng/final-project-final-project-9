package Model.Input;

import Model.Animals.Animal;
import Model.Animals.IAnimal;
import com.opencsv.bean.CsvBindByName;

public class AnimalCSV {
    @CsvBindByName(column = "AnimalType")
    private String animalType;

    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "AnimalSize")
    private String animalSize;

    @CsvBindByName(column = "Gender")
    private String gender;

    @CsvBindByName(column = "Pattern")
    private String pattern;

    @CsvBindByName(column = "Color")
    private String color;

    @CsvBindByName(column = "Age")
    private String age;

    @CsvBindByName(column = "Address")
    private String address;

    @CsvBindByName(column = "City")
    private String city;

    @CsvBindByName(column = "Time")
    private String time;

    @CsvBindByName(column = "Date")
    private String date;

    @CsvBindByName(column = "AnimalDescription")
    private String description;

    @CsvBindByName(column = "LocationDescription")
    private String locDesc;

    @CsvBindByName(column = "Number")
    private String number;

    @CsvBindByName(column = "Image")
    private String image;

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