package Model.Animals;

import Model.AnimalInfo.*;

public class Animal implements IAnimal {
    private final String type;
    private final String species;
    private final String size;
    private final String gender;
    private final String pattern;
    private final String color;
    private final String age;
    private final String seenDate;
    private final String time;
    private final String address;
    private final String area;
    private final String locDesc;
    private final String description;
    private final String image;
    private final String number;

    public Animal(String type, String species, String size, String gender, String pattern, String color,
                 String age, String address, String area, String time, String seenDate, String description,
                 String locDesc, String number) {
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
        //this.image = imgSrc + type.toLowerCase() + "/" + number + ".img";
        this.image = "data/image/"+number+".jpg";

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