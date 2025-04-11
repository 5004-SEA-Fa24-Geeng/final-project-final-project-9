package Model.InputModel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import Model.PostedAnimal;
import Model.TestDataGenerator;

public final class Filters {

    public static boolean filter(PostedAnimal animal, int i, String value) {
        switch (i+1) {
            case 1:
                return animal.getAnimalType().equals(value);
            case 2:
                return animal.getSpecies().equals(value);
            case 3:
                return animal.getAnimalSize() == Integer.parseInt(value);
            case 4:
                return animal.getGender() == Integer.parseInt(value);
            case 5:
                return animal.getPattern().equals(value);
            case 6:
                return animal.getAnimalColor().equals(value);
            case 7:
                return animal.getAnimalAge() == Integer.parseInt(value);
            default:
                return false;
        }
    }

    public static boolean filterDate(PostedAnimal animal, String value) {
        LocalDate today = LocalDate.now();
        LocalDate witnessedDate = convertToLocalDate(animal.getWitnessedTime());
        switch (value) {
            case "within one week":
                return ChronoUnit.DAYS.between(witnessedDate, today) <= 7;
            case "within two weeks":
                return ChronoUnit.DAYS.between(witnessedDate, today) <= 14;
            case "within one month":
                return ChronoUnit.DAYS.between(witnessedDate, today) <= 30;
            case "within three months":
                return ChronoUnit.DAYS.between(witnessedDate, today) <= 90;
            default:
                return false;
        }
    }

    public static boolean filterLocation(PostedAnimal animal, int index, String value) {
        // 使用TestDataGenerator中的城市位置数据作为参考点
        double[] cityCoords = getCityCoordinates(value);
        if (cityCoords == null) {
            return false;
        }
        
        double distance = TestDataGenerator.calculateDistance(
            cityCoords[0], cityCoords[1],
            animal.getLatitude(), animal.getLongitude()
        );
        
        // 根据不同的距离范围进行过滤
        switch (index) {
            case 7: // 城市范围
                return distance <= 5.0; // 5公里范围内认为是在城市内
            case 8: // 区域范围
                return distance <= 2.0; // 2公里范围内认为是在区域内
            default:
                return false;
        }
    }

    private static double[] getCityCoordinates(String cityName) {
        switch (cityName.toLowerCase()) {
            case "seattle":
                return new double[]{47.6062, -122.3321};
            case "bellevue":
                return new double[]{47.6101, -122.2015};
            case "redmond":
                return new double[]{47.6740, -122.1215};
            case "kirkland":
                return new double[]{47.6769, -122.2060};
            case "everett":
                return new double[]{47.9789, -122.2021};
            case "tacoma":
                return new double[]{47.2529, -122.4443};
            case "renton":
                return new double[]{47.4829, -122.2171};
            case "kent":
                return new double[]{47.3809, -122.2348};
            case "lynnwood":
                return new double[]{47.8279, -122.3055};
            case "bothell":
                return new double[]{47.7623, -122.2054};
            default:
                return null;
        }
    }

    private static LocalDate convertToLocalDate(Integer[] date) {
        return LocalDate.of(date[0], date[1], date[2]);
    }
}
