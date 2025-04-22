package view;

import java.util.List;

import model.animals.IAnimal;

public interface IView {
    /**
     * Display the list of animals.
     * @param animals List of animals to display
     */
    void displayAnimals(List<IAnimal> animals);

    /**
     * Display the map view.
     * @param animals List of animals to display on the map
     */
    void displayMap(List<IAnimal> animals);

    /**
     * Display filter options.
     */
    void showFilterOptions();

    /**
     * Display sort options.
     */
    void showSortOptions();

    /**
     * Display error message.
     * @param message Error message
     */
    void showError(String message);

    /**
     * Display success message.
     * @param message Success message
     */
    void showSuccess(String message);

    /**
     * Get the currently selected filter.
     * @return Filter condition
     */
    String getSelectedFilter();

    /**
     * Get the currently selected filter value.
     * @return Filter value
     */
    String getFilterValue();

    /**
     * Get the current sort order.
     * @return true for ascending order, false for descending
     */
    boolean getSortOrder();

    /**
     * Update the view state.
     */
    void updateView();
}
