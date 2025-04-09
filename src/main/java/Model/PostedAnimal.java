package Model;


public class PostedAnimal {


    private String species;
    private String pattern;
    private String animalColor;
    private int animalAge;
    private Integer[] witnessedTime;//几月几号之前
    private String loDescription;//离我最近
    private String location;//
    private boolean ifAdded = false;//
    private String witness;
    private final String image;
    private final String description;
    private int gender;
    private int animalSize;
    private String animalType;
    private double number;
    private double latitude;  // 新增：纬度
    private double longitude; // 新增：经度

    public PostedAnimal(double number,
                         String animalType,//dog, cat, bird,rabbit,hamster,Guinea pig,hedgedog,turtle *
                         String species,//* 在选择type之后，切换成属于type的species选项
                         int animalSize,//microsize,small,medium,big*+
                         int gender,//female,male,unclear*
                         String pattern,//*
                         String animalColor,//*
                         int animalAge,// baby, youny, adult, old*+
                         Integer[] witnessedTime,//25-03-29-15-30*+
                         String loDescription, //location description 比如xx建筑门口
                         String location,//seattle, bellevue, redmond,kirkland,everett,tacoma,renton,kent,lynnwood,bothell*+
                         boolean ifAdded,//比如"Seattle Downtown", Seattle:downtown， bellrown, capitol hill, ud, slu,queen anne, ballard,fremont,west sea*
                         //bellevue: downtown, clyde, hill, medina,lake hills,crossroads,bridle trails...
                         String witness,
                         String image,
                         String description,
                         double latitude,   // 新增：纬度参数
                         double longitude) { // 新增：经度参数
        this.number = number;
        this.animalType = animalType;
        this.species = species;
        this.pattern = pattern;
        this.animalColor = animalColor;
        this.animalAge = animalAge;
        this.witnessedTime = witnessedTime;
        this.loDescription = loDescription;
        this.location = location;
        this.ifAdded = ifAdded;
        this.witness = witness;
        this.image = image;
        this.description = description;
        this.gender = gender;
        this.animalSize = animalSize;
        this.latitude = latitude;    // 新增：设置纬度
        this.longitude = longitude;  // 新增：设置经度
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getNumber() {
        return number;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getSpecies() {
        return species;
    }

    public String getPattern() {
        return pattern;
    }

    public String getAnimalColor() {
        return animalColor;
    }

    public int getAnimalAge() {
        return animalAge;
    }

    public Integer[] getWitnessedTime() {
        return witnessedTime;
    }

    public String getloDescription() {
        return loDescription;
    }

    public String getLocation() {
        return location;
    }

    public boolean getIfAdded() {
        return ifAdded;
    }

    public void setIfAdded(boolean tf) {
        ifAdded = tf;
    }

    public String getWitness() {
        return witness;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getGender() {
        return gender;
    }

    public int getAnimalSize() {
        return animalSize;
    }

}
