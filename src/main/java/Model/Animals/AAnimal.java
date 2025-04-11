package Model.Animals;

import Model.AnimalInfo.*;

public abstract class AAnimal implements IAnimal {
    private AnimalType type;
    private String species;
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
    private String image;
    private int number;

    public AAnimal(String type, String species, String size, String gender, String pattern, String color, String age,
                   String seenDate, String seenTime, String address, String area, String locDesc, String description,
                   int number) {
        this.type = AnimalType.fromString(type);
        this.species = species;
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
        this.image = IAnimal.imgSrc + type.toLowerCase() + number + ".img";

    }

    public AnimalType getAnimalType() {
        return type;
    }

    @Override
    public String getSpecies() {
        return species;
    }

    @Override
    public Size getAnimalSize() {
        return size;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Color getAnimalColor() {
        return color;
    }

    @Override
    public Age getAnimalAge() {
        return age;
    }

    @Override
    public String getSeenDate() { return seenDate;};

    @Override
    public String getSeenTime() {
        return seenTime;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Area getArea() { return area; }

    @Override
    public String getloDescription() {
        return locDesc;
    }

    @Override
    public String getDescription() {
        return description;
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
