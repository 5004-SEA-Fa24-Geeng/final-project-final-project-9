package Model;

public interface IAnimalFilter {


    void filter(String filterOn, String filterStr);

    void filterOnType(String filterStr);

    void filterOnSpecies(String filterStr);

    void filterOnSize(String filterStr);

    void filterOnGender(String filterStr);

    void filterOnPattern(String filterStr);

    void filterOnColor(String filterStr);

    void filterOnAge(String filterStr);

    void filterOnSeenDate(String filterStr);

    void filterOnArea(String filterStr);

    void reset();

}
