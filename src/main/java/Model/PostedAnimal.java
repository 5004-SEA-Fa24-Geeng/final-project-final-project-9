package Model;

public class PostedAnimal {
    static String imgSrc = "./data/animalImage/"; //  .../animalImage/ dog|cat|...

    private String type;
    private String species;
    private String size;
    private String gender;
    private String pattern;
    private String color;
    private String age;
    private Integer[] witnessedTime;//几月几号之前
    private String location; //
    private String locDesc; //离我最近
    private String description;
    private String image;
    private String number;  // used for image indexing
    private double latitude;  // 添加经纬度信息
    private double longitude;
//    private boolean ifAdded ??= false;
//    private UserWit witness;

    public PostedAnimal(String type, String species, String size, String gender, String pattern, String color, String age,
                        Integer[] witnessedTime,//25-03-29-15-30*+
                        String location,//seattle, bellevue, redmond,kirkland,everett,tacoma,renton,kent,lynnwood,bothell*+
                        String locDesc, //location description 比如xx建筑门口
                        String description,
                        String image,
                        String number,
                        double latitude,
                        double longitude) {
//                      UserWit witness,
//                      boolean ifAdded,
        this.type = type;
        this.species = species;
        this.size = size;
        this.gender = gender;
        this.pattern = pattern;
        this.color = color;
        this.age = age;
        this.witnessedTime = witnessedTime;
        this.location = location;
        this.locDesc = locDesc;
        this.description = description;
        this.image = image;
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
//        this.witness = witness;
//        this.ifAdded = ifAdded;
    }


    public String getAnimalType() {
        return type;
    }

    public String getSpecies() {
        return species;
    }

    public String getAnimalSize() {
        return size;
    }

    public String getGender() {
        return gender;
    }

    public String getPattern() {
        return pattern;
    }

    public String getAnimalColor() { return color;}

    public String getAnimalAge() {
        return age;
    }

    public Integer[] getWitnessedTime() {
        return witnessedTime;
    }

    public String getLocation() {
        return location;
    }

    public String getloDescription() {
        return locDesc;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getNumber() {
        return number;
    }

    public void setCoordinates(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }
    
    public double[] getCoordinates() {
        return new double[]{latitude, longitude};
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

//    public String getWitness() {
//        return witness;
//    }
//    public void setIfAdded(boolean tf) {
//        ifAdded = tf;
//    }
//    public boolean getIfAdded() {
//        return ifAdded;
//    }
}
