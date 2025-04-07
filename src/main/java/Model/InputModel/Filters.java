package Model.InputModel;

import Model.PostedAnimal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public static boolean filterLocation(PostedAnimal animal, String location, String scope) {
        Set<String> loc = new HashSet<>(Arrays.asList(location.split(",")));
        switch (scope) {
            case "within 500m":
                return loc-animal.getLocation()<500;
            default:
                return false;
        }

    }

    private static LocalDate convertToLocalDate(Integer[] date) {
        return LocalDate.of(date[0] + 2000, date[1], date[2]); // 假设年份是 20XX
    }


}
