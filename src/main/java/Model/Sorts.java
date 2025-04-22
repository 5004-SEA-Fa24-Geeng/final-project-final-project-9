package Model;

import Model.Animals.IAnimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Utility class for sorting animals based on different criteria.
 */
public final class Sorts {
    /** Set date format. */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Private constructor to prevent instantiation.
     */
    private Sorts() { }

    /**
     * Creates a comparator for sorting animals by date.
     * @param ascending true for ascending order, false for descending
     * @return Comparator for sorting animals by date
     */
    public static Comparator<IAnimal> sortByDate(boolean ascending) {
        return (o1, o2) -> {
            try {
                Date date1 = DATE_FORMAT.parse(o1.getSeenDate());
                Date date2 = DATE_FORMAT.parse(o2.getSeenDate());
                int compare = date1.compareTo(date2);
                return ascending ? compare : -compare;
            } catch (ParseException e) {
                return 0; // Keep original order if date parsing fails
            }
        };
    }

    /**
     * Creates a comparator for sorting animals by type.
     * @param ascending true for ascending order, false for descending
     * @return Comparator for sorting animals by type
     */
    public static Comparator<IAnimal> sortByType(boolean ascending) {
        return (o1, o2) -> {
            int compare = o1.getAnimalType().compareTo(o2.getAnimalType());
            return ascending ? compare : -compare;
        };
    }

    /**
     * Creates a comparator for sorting animals by size.
     * @param ascending true for ascending order, false for descending
     * @return Comparator for sorting animals by size
     */
    public static Comparator<IAnimal> sortBySize(boolean ascending) {
        return (o1, o2) -> {
            int compare = o1.getAnimalSize().compareTo(o2.getAnimalSize());
            return ascending ? -compare : compare;
        };
    }

    /**
     * Creates a comparator for sorting animals by age.
     * @param ascending true for ascending order, false for descending
     * @return Comparator for sorting animals by age
     */
    public static Comparator<IAnimal> sortByAge(boolean ascending) {
        return (o1, o2) -> {
            int compare = o1.getAge().compareTo(o2.getAge());
            return ascending ? compare : -compare;
        };
    }

    /**
     * Creates a comparator for sorting animals by area.
     * @param ascending true for ascending order, false for descending
     * @return Comparator for sorting animals by area
     */
    public static Comparator<IAnimal> sortByArea(boolean ascending) {
        return (o1, o2) -> {
            int compare = o1.getArea().compareTo(o2.getArea());
            return ascending ? compare : -compare;
        };
    }

    /**
     * Creates a comparator for sorting animals by species.
     * @param ascending true for ascending order, false for descending
     * @return Comparator for sorting animals by species
     */
    public static Comparator<IAnimal> sortBySpecies(boolean ascending) {
        return (o1, o2) -> {
            int compare = o1.getSpecies().compareTo(o2.getSpecies());
            return ascending ? compare : -compare;
        };
    }
}
