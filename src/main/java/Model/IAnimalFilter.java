package Model;

import java.util.List;
import Model.Animals.IAnimal;

/**
 * An Animal filter that contains the filtered list of animals.
 */
public interface IAnimalFilter {

    /**
     * Get the filtered list of animals
     * @return the filtered list.
     */
    List<IAnimal> getFilteredAnimals();


    /**
     * Filter the animals based on the filterOn and filterStr.
     * Sort on TYPE, SPECIES, SIZE, GENDER, PATTERN, COLOR, AGE, SEENDATE, AREA.
     * @param filterOn the filter on column
     * @param filterStr the filter string
     */
    void filter(String filterOn, String filterStr);


    /**
     * Sort the filtered list by date.
     * @param asc ascending or not
     */
    void sortOnDate(boolean asc);


    /**
     * Rest the filtered list to original list.
     */
    void reset();

}