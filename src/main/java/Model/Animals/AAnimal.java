package Model.Animals;

import Model.AnimalInfo.*;

public class AAnimal implements IAnimal {
    private String animalType;
    private String species;
    private String animalSize;
    private String gender;
    private String pattern;
    private String color;
    private String age;
    private String address;
    private String city;
    private String time;
    private String date;
    private String description;
    private String locDesc;
    private final String number;
    private final String image;

    public AAnimal(String animalType, String species, String animalSize, String gender,
                 String pattern, String color, String age, String address, String city,
                 String time, String date, String description, String locDesc, String number) {
        this.animalType = animalType;
        this.species = species;
        this.animalSize = animalSize;
        this.gender = gender;
        this.pattern = pattern;
        this.color = color;
        this.age = age;
        this.address = address;
        this.city = city;
        this.time = time;
        this.date = date;
        this.description = description;
        this.locDesc = locDesc;
        this.number = number;
        this.image = imgSrc + animalType.toLowerCase() + "/" + number + ".img";
    }

    @Override
    public String getAnimalType() {
        return animalType;
    }

    @Override
    public void setAnimalType(String type) {
        this.animalType = type;
    }

    @Override
    public String getSpecies() {
        return species;
    }

    @Override
    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String getAnimalSize() {
        return animalSize;
    }

    @Override
    public void setAnimalSize(String size) {
        this.animalSize = size;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setAnimalColor(String color) {
        this.color = color;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public void setAnimalAge(String age) {
        this.age = age;
    }

    @Override
    public String getSeenDate() {
        return date;
    }

    @Override
    public void setSeenDate(String seenDate) {
        this.date = seenDate;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public void setSeenTime(String seenTime) {
        this.time = seenTime;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getArea() {
        return city;
    }

    @Override
    public void setArea(String area) {
        this.city = area;
    }

    @Override
    public String getLocDesc() {
        return locDesc;
    }

    @Override
    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String desc) {
        this.description = desc;
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