package Model;

public interface IAnimalFilter {


    /**
     * Filter the animals based on the filterOn and filterStr. 
     * Sort on TYPE, SPECIES, SIZE, GENDER, PATTERN, COLOR, AGE, SEENDATE, AREA.
     * @param filterOn the filter on column
     * @param filterStr the filter string
     */
    void filter(String filterOn, String filterStr);

    void filterOnType(String filterStr);

    void filterOnSpecies(String filterStr);

    void filterOnSize(String filterStr);

    void filterOnGender(String filterStr);

    void filterOnPattern(String filterStr);

    void filterOnColor(String filterStr);

    void filterOnAge(String filterStr);

    /**
     * Filter the animals based on the filterOn and filterStr. 
     * within 1 week, within 2 weeks, within 1 month, within 3 months
     * @param filterStr the filter string
     */
    void filterOnSeenDate(String filterStr);

    void filterOnArea(String filterStr);

    void sortOnDate(boolean asc);

    void reset();

}
