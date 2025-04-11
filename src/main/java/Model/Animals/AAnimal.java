package Model.Animals;

public class AAnimal implements IAnimal {
    private String type;
    private String species;
    private String size;
    private String gender;
    private String pattern;
    private String color;
    private String age;
    private String seenDate;
    private String time;
    private String address;
    private String area;
    private String locDesc;
    private String description;
    private String image;
    private String number;

    public AAnimal(String type, String species, String size, String gender, String pattern, String color,
                  String age, String address, String area, String time, String seenDate, String description,
                  String locDesc, String image) {
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
        this.image = image;
        this.number = "0"; // 默认编号
    }

    @Override
    public String getAnimalType() {
        return type;
    }

    @Override
    public void setAnimalType(String type) {
        this.type = type;
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
        return size;
    }

    @Override
    public void setAnimalSize(String size) {
        this.size = size;
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
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getAge() {
        return age;
    }

    @Override
    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String getSeenDate() {
        return seenDate;
    }

    @Override
    public void setSeenDate(String seenDate) {
        this.seenDate = seenDate;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
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
        return area;
    }

    @Override
    public void setArea(String area) {
        this.area = area;
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
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getNumber() {
        return number;
    }
} 