package Controller;

import Model.PostedAnimal;

import java.util.List;

public interface IController {

    /**
     *
     * @return
     */
    //List<Operation> inputParse;


    /**
     * default sort on time
     * @param operation
     * @return
     */
    List<PostedAnimal> filter(List<Operation> operation);

    /**
     * search box, like black big husky, default sort on time
     * @param operation
     * @return
     */
    List<PostedAnimal> search(List<Operation> operation);

    /**
     * //ascending, descending
     * @param operation
     * @return
     */
    List<PostedAnimal> sort(List<Operation> operation);

    /**
     *
     * @param listAnimal
     */
    void outputGen(List<PostedAnimal> listAnimal);


}
