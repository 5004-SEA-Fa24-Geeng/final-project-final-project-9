package Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;


public class PostedAnimal {

    @CsvBindByName(column = "Species")
    private String species;

    @CsvBindByName(column = "Pattern")
    private String pattern;

    @CsvBindByName(column = "AnimalColor")
    private String animalColor;

    @CsvBindByName(column = "AnimalAge")
    private String animalAge;

    @CsvBindByName(column = "WitnessedTime")
    //private Integer[] witnessedTime;//几月几号之前
    private String witnessedTime;

    @CsvBindByName(column = "LoDescription")
    private String loDescription;//离我最近

    @CsvBindByName(column = "Location")
    private String location;//

    @JsonIgnore
    @CsvBindByName(column = "IfAdded")
    private boolean ifAdded = false;//

    @JsonIgnore
    @CsvBindByName(column = "Witness")
    private int witnessNo;

    @JsonIgnore
    @CsvBindByName(column = "Image")
    private final String image;

    @CsvBindByName(column = "Description")
    private final String description;

    @CsvBindByName(column = "Gender")
    private String gender;

    @CsvBindByName(column = "AnimalSize")
    private String animalSize;

    @CsvBindByName(column = "AnimalType")
    private String animalType;

    @JsonIgnore
    @CsvBindByName(column = "Number")
    private double number;

    public PostedAnimal() {
        this.image = "";
        this.description = "";
    }

    public PostedAnimal(double number,
                         String animalType,//dog, cat, bird,rabbit,hamster,Guinea pig,hedgedog,turtle *
                         String species,//* 在选择type之后，切换成属于type的species选项
                         String animalSize,//microsize,small,medium,big*+
                         String gender,//female,male,unclear*
                         String pattern,//*
                         String animalColor,//*
                         String animalAge,// baby, youny, adult, old*+
                         //Integer[] witnessedTime,//25-03-29-15-30*+
                         String witnessedTime,
                         String loDescription, //location description 比如xx建筑门口
                         String location,//seattle, bellevue, redmond,kirkland,everett,tacoma,renton,kent,lynnwood,bothell*+
                         boolean ifAdded,//比如"Seattle Downtown", Seattle:downtown， bellrown, capitol hill, ud, slu,queen anne, ballard,fremont,west sea*
                         //bellevue: downtown, clyde, hill, medina,lake hills,crossroads,bridle trails...
                         int witnessNo,//user
                         String image,
                         String description) {
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
        this.witnessNo = witnessNo;
        this.image = image;
        this.description = description;
        this.gender = gender;
        this.animalSize = animalSize;
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

    public String getAnimalAge() {
        return animalAge;
    }

    //public Integer[] getWitnessedTime() {
        //return witnessedTime;
    //}
    public String getWitnessedTime() {
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

    public int getWitnessNo() {
        return witnessNo;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public String getAnimalSize() {
        return animalSize;
    }

}
