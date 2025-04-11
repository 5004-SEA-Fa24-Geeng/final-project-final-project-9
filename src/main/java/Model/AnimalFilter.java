package Model;

import Model.Animals.IAnimal;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
     *
     */
    public AnimalFilter(AnimalList animalList) {
        this.originalList = animalList;
        filtered = originalList.getAnimals();
    }


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


    @Override
    public void reset() {
        filtered = originalList.getAnimals();
    }
}

