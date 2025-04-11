package Model.InputModel;

import Model.Animals.IAnimal;

import java.util.Set;
import java.util.stream.Stream;

public class AnimalFilter {
    /** The original set of animals */
    private Set<IAnimal> animals;
    /** A copy of the games set for internal operations. */
    //private Set<IAnimal> copy;

    /**
     * Constructs a new {@code Filter} with the specified set of animals.
     * It also creates a copy of the original set to allow filtering and modifications without
     * affecting the original data.
     *
     * @param animals games The set of {@link IAnimal} objects that this planner will manage.
     *              A copy of this set is also created to support filtering operations.
     */
    public AnimalFilter(Set<IAnimal> animals) {
        this.animals = animals;
        //this.copy = new HashSet<>(animals);
    }


    //public Stream<IAnimal> filter(String[] filter) {
    //    return filter(filter, "number", true);
    //}

    //public Stream<IAnimal> filter(String[] filter, String sortOn) {
    //    return filter(filter, sortOn, true);
    //}


    public Stream<IAnimal> filter(String filters) {
        String[] filter = filters.split(",");
        Stream<IAnimal> stream = animals.stream();
        int i;

        for (i = 0; i < filter.length; i++) {
            int finalI = i;
            if (!filter[i].equals("Not Sure")) {
                if (i==9) {
                    stream = stream.filter(IAnimal -> Filters.filterDate(IAnimal, filter[finalI]));
                }
                else if(i==7|i==8){
                    //seattle bellevue
                    stream = stream.filter(IAnimal -> Filters.filterLocation(IAnimal, finalI, filter[finalI]));
                }
                else {
                    stream = stream.filter(IAnimal -> Filters.filter(IAnimal, finalI, filter[finalI]));
                }
            }
        }


        return stream; //sorted(Comparators.comparator(sortOn, ascending));
    }


    //public void reset() {
    //    this.animals = new HashSet<>(copy);
    //}

}

