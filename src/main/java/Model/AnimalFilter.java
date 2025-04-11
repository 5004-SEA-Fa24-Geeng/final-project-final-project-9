package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import Model.Animals.IAnimal;

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
        filtered = originalList.getAnimals();
    }

    @Override
    public void filter(String filterOn, String filterStr) {
        AnimalData data = AnimalData.fromString(filterOn);
        if (data == null) return;
        
        switch (data) {
            case TYPE -> filterOnType(filterStr);
            case SPECIES -> filterOnSpecies(filterStr);
            case SIZE -> filterOnSize(filterStr);
            case GENDER -> filterOnGender(filterStr);
            case PATTERN -> filterOnPattern(filterStr);
            case COLOR -> filterOnColor(filterStr);
            case AGE -> filterOnAge(filterStr);
            case SEENDATE -> filterOnSeenDate(filterStr);
            case AREA -> filterOnArea(filterStr);
            default -> { }
        }
    }

    @Override
    public void filterOnType(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getAnimalType().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnSpecies(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getSpecies().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnSize(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getAnimalSize().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnGender(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getGender().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnPattern(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getPattern().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnColor(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getColor().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnAge(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getAge().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void filterOnSeenDate(String filterStr) {
        long currentTime = System.currentTimeMillis();
        long timeRange = switch (filterStr.toLowerCase()) {
            case "within 1 week" -> 7 * 24 * 60 * 60 * 1000L;  // 7 days in milliseconds
            case "within 2 weeks" -> 14 * 24 * 60 * 60 * 1000L;  // 14 days in milliseconds
            case "within 1 month" -> 30 * 24 * 60 * 60 * 1000L;  // 30 days in milliseconds
            case "within 3 months" -> 90 * 24 * 60 * 60 * 1000L;  // 90 days in milliseconds
            default -> Long.MAX_VALUE;  // Invalid Input
        };

        filtered = filtered.stream()
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
    public void filterOnArea(String filterStr) {
        filtered = filtered.stream()
                .filter(animal -> animal.getArea().equalsIgnoreCase(filterStr))
                .collect(Collectors.toList());
    }

    @Override
    public void sortOnDate(boolean asc) {
        filtered = filtered.stream()
                .sorted(Sorts.sort(asc))
                .collect(Collectors.toList());
    }

    @Override
    public void reset() {
        filtered = originalList.getAnimals();
    }

    /**
     * 获取过滤后的动物列表
     * @return 过滤后的动物列表
     */
    public List<IAnimal> getFilteredAnimals() {
        return filtered;
    }
}

