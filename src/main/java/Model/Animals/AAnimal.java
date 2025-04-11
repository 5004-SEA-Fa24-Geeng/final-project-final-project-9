package Model.Animals;

import Model.AnimalInfo.*;
import Model.AnimalInfo.Species.Species;

public abstract class AAnimal implements IAnimal {
    private AnimalType type;
    private Species species;
    private Size size;
    private Gender gender;
    private Pattern pattern;
    private Color color;
    private Age age;
    private String seenDate;
    private String seenTime;//几月几号之前
    private Area area;
    private String address; //
    private String locDesc; //离我最近
    private String description;
    private final String image;
    private final int number;

    public AAnimal(AnimalType type, String species, String size, String gender, String pattern, String color, String age,
                   String seenDate, String seenTime, String address, String area, String locDesc, String description,
                   int number) {
        this.type = type;
        this.species = Species.getSpeciesByType(this.type, species);
        this.size = Size.fromString(size);
        this.gender = Gender.fromString(gender);
        this.pattern = Pattern.fromString(pattern);
        this.color = Color.fromString(color);
        this.age = Age.fromString(age);
        this.seenDate = seenDate;
        this.seenTime = seenTime;
        this.address = address;
        this.area = Area.fromString(area);
        this.locDesc = locDesc;
        this.description = description;
        this.number = number;
        this.image = IAnimal.imgSrc + type.name().toLowerCase() + number + ".img";

    }

    @Override
    public AnimalType getAnimalType() {
        return type;
    }

    @Override
    public void setAnimalType(String type) {
        this.type = AnimalType.fromString(type);
    }

    @Override
    public Species getSpecies() {
        return species;
    }

    @Override
    public void setSpecies(String species) {
        this.species = Species.getSpeciesByType(this.type, species);
    }

    @Override
    public Size getAnimalSize() {
        return size;
    }

    @Override
    public void setAnimalSize(String size) {
        this.size = Size.fromString(size);
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(String gender) {
        this.gender = Gender.fromString(gender);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = Pattern.fromString(pattern);
    }

    @Override
    public Color getAnimalColor() {
        return color;
    }

    @Override
    public void setAnimalColor(String color) {
        this.color = Color.fromString(color);
    }

    @Override
    public Age getAnimalAge() {
        return age;
    }

    @Override
    public void setAnimalAge(String age) {
        this.age = Age.fromString(age);
    }

    @Override
    public String getSeenDate() { return seenDate;};

    @Override
    public void setSeenDate(String seenDate) {
        this.seenDate = seenDate;
    }

    @Override
    public String getSeenTime() {
        return seenTime;
    }

    @Override
    public void setSeenTime(String seenTime) {
        this.seenTime = seenTime;
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
    public Area getArea() { return area; }

    @Override
    public void setArea(String area) {
        this.area = Area.fromString(area);
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
    public String getImage() {
        return image;
    }

    @Override
    public int getNumber() {
        return number;
    }
}
