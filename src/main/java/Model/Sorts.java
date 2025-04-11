package Model;


import Model.Animals.IAnimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


/**
 * Sort
 */
public class Sorts {

    /**
     * Private constructor preventing instansiation.
     */
    private Sorts() {

    }

    /**
     * Default sort。
     * @param asc ascending or not
     * @return lambda expression as comparator
     */
    public static Comparator<IAnimal> sort(boolean asc) {
        return sort(AnimalData.SEENDATE, asc);
    }

    /**
     * Sort for animal data. Currently only for seenDate.
     * @param sortOn the data to sort on
     * @param asc ascending or not
     * @return lambda expression as comparator
     */
    public static Comparator<IAnimal> sort(AnimalData sortOn, boolean asc) {
        return switch (sortOn) {
            case SEENDATE -> (o1, o2) -> {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date1 = sdf.parse(o1.getSeenDate());
                    Date date2 = sdf.parse(o2.getSeenDate());
                    int compare = date1.compareTo(date2);
                    return asc ? compare : -compare;
                } catch (ParseException e) {
                    throw new RuntimeException("Invalid date format.", e);
                }
            };
            default -> (o1, o2) -> 0;
        };
    }
}
