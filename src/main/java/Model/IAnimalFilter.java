package Model;

import java.util.List;
import Model.Animals.IAnimal;

public interface IAnimalFilter {


    /**
     * Filter the animals based on the filterOn and filterStr.
     * Sort on TYPE, SPECIES, SIZE, GENDER, PATTERN, COLOR, AGE, SEENDATE, AREA.
     * @param filterOn the filter on column
     * @param filterStr the filter string
     */
    void filter(String filterOn, String filterStr);

    List<IAnimal> filterOnType(String filterStr);

    List<IAnimal> filterOnSpecies(String filterStr);

    List<IAnimal> filterOnSize(String filterStr);

    List<IAnimal> filterOnGender(String filterStr);

    List<IAnimal> filterOnPattern(String filterStr);

    List<IAnimal> filterOnColor(String filterStr);

    List<IAnimal> filterOnAge(String filterStr);

    /**
     * Filter the animals based on the filterOn and filterStr.
     * within 1 week, within 2 weeks, within 1 month, within 3 months
     * @param filterStr the filter string
     */
    List<IAnimal> filterOnSeenDate(String filterStr);

    List<IAnimal> filterOnArea(String filterStr);

    void sortOnDate(boolean asc);

    void reset();

}