package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import Model.Animals.AnimalData;
import Model.Animals.IAnimal;


/**
 * IA concrete animal filter class that contains the filtered list of animals.
 */
public class AnimalFilter implements IAnimalFilter {
    /** The filtered list of animals for GUI display on list and map. */
    private List<IAnimal> filtered;

    /** The AnimalList Object used for initialize filtered list and reset. */
    private final AnimalList originalList;

    /**
     * Constructs a new {@code Filter} with the specified set of animals.
     * It also creates a copy of the original set to allow filtering and modifications without
     * affecting the original data.
     *
     * @param animalList original AnimalList Object.
     */
    public AnimalFilter(AnimalList animalList) {
        this.originalList = animalList;
        filtered = new CopyOnWriteArrayList<>(originalList.getAnimals());
    }

    @Override
    public void filter(String filterOn, String filterStr) {
        AnimalData data = AnimalData.fromString(filterOn);
        if (data == null) {
            return;
        }

        List<IAnimal> newFiltered = filtered;
        switch (data) {
            case TYPE -> newFiltered = filterOnType(filterStr);
            case SPECIES -> newFiltered = filterOnSpecies(filterStr);
            case SIZE -> newFiltered = filterOnSize(filterStr);
            case GENDER -> newFiltered = filterOnGender(filterStr);
            case PATTERN -> newFiltered = filterOnPattern(filterStr);
            case COLOR -> newFiltered = filterOnColor(filterStr);
            case AGE -> newFiltered = filterOnAge(filterStr);
            case SEENDATE -> newFiltered = filterOnSeenDate(filterStr);
            case AREA -> newFiltered = filterOnArea(filterStr);
            default -> { }
        }
        filtered = new CopyOnWriteArrayList<>(newFiltered);
    }


    /**
     * Filter the list on animal type.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnType(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getAnimalType().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on animal breed.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnSpecies(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getSpecies().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on animal size.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnSize(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getAnimalSize().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on animal gender.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnGender(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getGender().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on animal pattern.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnPattern(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getPattern().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on animal color.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnColor(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getColor().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on animal age.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnAge(String filterStr) {
        return filtered.stream()
                .filter(animal -> animal.getAge().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }


    /**
     * Filter the list on city.
     * @param filterStr filter matching value
     * @return the filtered list
     */
    private List<IAnimal> filterOnArea(String filterStr) {
        return filtered.stream()
                .filter(animal -> {
                    String animalArea = animal.getArea().trim().toUpperCase();
                    String filterArea = filterStr.trim().toUpperCase();
                    return animalArea.equals(filterArea);
                })
                .collect(Collectors.toList());
    }


    /**
     * Filter the animals based on the filterOn and filterStr.
     * within 1 week, within 2 weeks, within 1 month, within 3 months
     * @param filterStr the filter string
     * @return the list of filtered animal
     */
    private List<IAnimal> filterOnSeenDate(String filterStr) {
        long currentTime = System.currentTimeMillis();
        long timeRange = switch (filterStr.toLowerCase()) {
            case "within 1 week" -> 7 * 24 * 60 * 60 * 1000L;  // 7 days in milliseconds
            case "within 2 weeks" -> 14 * 24 * 60 * 60 * 1000L;  // 14 days in milliseconds
            case "within 1 month" -> 30 * 24 * 60 * 60 * 1000L;  // 30 days in milliseconds
            case "within 3 months" -> 90 * 24 * 60 * 60 * 1000L;  // 90 days in milliseconds
            default -> Long.MAX_VALUE;  // Invalid Input
        };

        return filtered.stream()
                .filter(animal -> {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Date seenDate = sdf.parse(animal.getSeenDate());
                        long timeDiff = currentTime - seenDate.getTime();
                        return timeDiff <= timeRange;
                    } catch (ParseException e) {
                        return false;  // invalid date format
                    }
                })
                .collect(Collectors.toList());
    }


    @Override
    public void sortOnDate(boolean asc) {
        filtered = filtered.stream()
                .sorted(Sorts.sortByDate(asc))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        filtered = List.copyOf(originalList.getAnimals());
    }



    @Override
    public List<IAnimal> getFilteredAnimals() {
        return filtered;
    }
}
