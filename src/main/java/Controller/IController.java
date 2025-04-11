package Controller;

import java.util.List;

public interface IController {

    /**
     *
     * @return
     */
    //List<Operation> inputParse;


    /**
     * Filter animals based on given operations
     * @param operation List of filtering operations
     * @return Filtered list of animals
     */
    List<PostedAnimal> filter(List<Operation> operation);

    /**
     * Search for animals based on given operations
     * @param operation List of search operations
     * @return List of matching animals
     */
    List<PostedAnimal> search(List<Operation> operation);

    /**
     * Sorts animals based on given operations
     * @param operation List of sorting operations
     * @return Sorted list of animals
     */
    List<PostedAnimal> sort(List<Operation> operation);

    /**
     * Generate output for the given list of animals
     * @param listAnimal List of animals to generate output for
     */
    void outputGen(List<PostedAnimal> listAnimal);

    /**
     * Get animals within a specific area
     * @param lat Center latitude
     * @param lon Center longitude
     * @param radius Search radius in kilometers
     * @return List of animals within the specified area
     */
    List<PostedAnimal> getAnimalsInArea(double lat, double lon, double radius);

    /**
     * Update an animal's location
     * @param animalId Animal identifier
     * @param lat New latitude
     * @param lon New longitude
     */
    void updateAnimalLocation(String animalId, double lat, double lon);

    /**
     * Display animals on the map
     * @param animals List of animals to display
     */
    void displayOnMap(List<PostedAnimal> animals);
}
