package Controller;

import java.util.List;

import Model.FilterCriteria;
import Model.PostedAnimal;

public interface IController {

    /**
     *
     * @return
     */
    //List<Operation> inputParse;


    /**
     * Filter animals based on given criteria
     * @param criteria List of filter criteria
     * @return Filtered list of animals
     */
    List<PostedAnimal> filter(List<FilterCriteria> criteria);

    /**
     * Search animals based on given criteria (e.g., "black big husky")
     * Default sort by time
     * @param criteria List of search criteria
     * @return Search results
     */
    List<PostedAnimal> search(List<FilterCriteria> criteria);

    /**
     * Sort animals based on given criteria
     * @param criteria List of sort criteria
     * @return Sorted list of animals
     */
    List<PostedAnimal> sort(List<FilterCriteria> criteria);

    /**
     * Generate output for the given list of animals
     * @param listAnimal List of animals to generate output for
     */
    void outputGen(List<PostedAnimal> listAnimal);


}
