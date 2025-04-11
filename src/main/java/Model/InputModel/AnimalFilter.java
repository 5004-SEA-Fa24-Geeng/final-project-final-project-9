package Model.InputModel;

import java.util.Set;
import java.util.stream.Stream;

import Model.PostedAnimal;

public class AnimalFilter {
    /** The original set of board games. */
    private Set<PostedAnimal> animals;
    /** A copy of the games set for internal operations. */
    //private Set<PostedAnimal> copy;

    /**
     * Constructs a new {@code Filter} with the specified set of animals.
     * It also creates a copy of the original set to allow filtering and modifications without
     * affecting the original data.
     *
     * @param animals games The set of {@link PostedAnimal} objects that this planner will manage.
     *              A copy of this set is also created to support filtering operations.
     */
    public AnimalFilter(Set<PostedAnimal> animals) {
        this.animals = animals;
        //this.copy = new HashSet<>(animals);
    }


    //public Stream<postedAnimal> filter(String[] filter) {
    //    return filter(filter, "number", true);
    //}

    //public Stream<postedAnimal> filter(String[] filter, String sortOn) {
    //    return filter(filter, sortOn, true);
    //}


    public Stream<PostedAnimal> filter(String filters) {
        if (filters == null || filters.isEmpty()) {
            return animals.stream();
        }

        String[] filter = filters.split(",");
        Stream<PostedAnimal> stream = animals.stream();

        for (int i = 0; i < filter.length; i++) {
            if (!filter[i].equals("Not Sure") && !filter[i].isEmpty()) {
                final int finalI = i;
                if (i == 9) { // Date filter
                    stream = stream.filter(animal -> Filters.filterDate(animal, filter[finalI]));
                }
                else if (i == 7 || i == 8) { // Location filters
                    stream = stream.filter(animal -> Filters.filterLocation(animal, finalI, filter[finalI]));
                }
                else { // Basic filters
                    stream = stream.filter(animal -> Filters.filter(animal, finalI, filter[finalI]));
                }
            }
        }

        return stream; //sorted(Comparators.comparator(sortOn, ascending));
    }


    //public void reset() {
    //    this.animals = new HashSet<>(copy);
    //}

}

