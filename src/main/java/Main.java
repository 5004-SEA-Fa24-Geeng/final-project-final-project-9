import controller.Controller;
import controller.IController;
import model.AnimalFilter;
import model.AnimalList;
import view.IView;
import view.View;

/**
 * Main class that serves as the entry point for the Animal Tracking application.
 * This class initializes the application components and starts the user interface.
 */
public final class Main {
    /**
     * Private constructor to prevent instantiation.
     */
    private Main() {

    }

    /**
     * The main method that starts the application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize model components
        AnimalList animalList = new AnimalList();
        AnimalFilter animalFilter = new AnimalFilter(animalList);

        // Initialize controller
        IController controller = new Controller(animalList, animalFilter);

        // Initialize and start the view
        IView view = new View(controller);
        controller.setView(view);
        controller.initialize();

        // Make the view visible
        ((javax.swing.JFrame) view).setVisible(true);
    }
}
