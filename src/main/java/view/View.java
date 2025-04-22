/**
 * view.java
 *
 * This file defines the view class, which serves as the graphical user interface (GUI) for
 * the Stray Animal Spotter application.
 * The view class is responsible for rendering the main window, including the list and map views of animals,
 * filter and sort controls,
 * animal detail dialogs, and reporting functionality. It interacts with the controller to
 * update the display in response to user actions.
 *
 * Main Components:
 * - List view: Displays a list of animals with filtering, sorting, and selection features.
 * - Map view: Shows animal locations on an interactive map with clickable markers.
 * - Filter Panel: Allows users to filter animals by type,
 * breed, size, gender, pattern, color, age, city, and date range.
 * - Reporting: Provides dialogs for users to report new animal sightings, including image uploads.
 * - Export: Allows exporting selected animal data in various formats.
 *
 * The view class implements IView and Species, and uses Swing components for the GUI.
 *
 * Author: [Your Name]
 * Date: [Date]
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import Model.AnimalInfo.Species.*;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;

import Controller.IController;
import Model.AnimalInfo.Age;
import Model.AnimalInfo.AnimalType;
import Model.AnimalInfo.Area;
import Model.AnimalInfo.Gender;
import Model.AnimalInfo.Pattern;
import Model.AnimalInfo.Size;
import Model.Animals.IAnimal;

public class View extends JFrame implements IView, Species {
    /**
     * The controller to interact with business logic.
     */
    private final IController controller;
    /**
     * Tabbed pane containing list and map views.
     */
    private final JTabbedPane tabbedPane;
    /**
     * Panel displaying the list view of animals.
     */
    private final JPanel listPanel;
    /**
     * Panel displaying the map view of animals.
     */
    private final JPanel mapPanel;
    /**
     * List component displaying animals.
     */
    private final JList<IAnimal> animalList;
    /**
     * Model for the animal list.
     */
    private final DefaultListModel<IAnimal> listModel;
    /**
     * Model for the selected animal list.
     */
    private final DefaultListModel<IAnimal> selectedListModel;
    /**
     * List component displaying selected animals.
     */
    private final JList<IAnimal> selectedAnimalList;
    /**
     * Panel containing filter controls.
     */
    private final JPanel filterPanel;
    /**
     * Combo box for selecting animal type.
     */
    private final JComboBox<String> typeComboBox;
    /**
     * Combo box for selecting animal breed.
     */
    private final JComboBox<String> breedComboBox;
    /**
     * Combo box for selecting animal size.
     */
    private final JComboBox<String> sizeComboBox;
    /**
     * Combo box for selecting animal gender.
     */
    private final JComboBox<String> genderComboBox;
    /**
     * Combo box for selecting animal pattern.
     */
    private final JComboBox<String> patternComboBox;
    /**
     * Combo box for selecting animal color.
     */
    private final JComboBox<String> colorComboBox;
    /**
     * Combo box for selecting animal age.
     */
    private final JComboBox<String> ageComboBox;
    /**
     * Combo box for selecting animal city.
     */
    private final JComboBox<String> cityComboBox;
    /**
     * Combo box for selecting date range.
     */
    private final JComboBox<String> dateRangeComboBox;
    /**
     * Button to apply filters.
     */
    private final JButton filterButton;
    /**
     * Button to reset filters.
     */
    private final JButton resetButton;
    /**
     * Button to report a new animal sighting.
     */
    private final JButton reportButton;
    /**
     * Map viewer component for displaying animal locations.
     */
    private final JXMapViewer mapViewer;
    /**
     * Combo box for selecting sort order.
     */
    private final JComboBox<String> sortComboBox;
    /**
     * Button to sort the animal list.
     */
    private final JButton sortButton;
    /**
     * Set of currently selected animals.
     */
    private final Set<IAnimal> selectedAnimals;
    /**
     * Map from 2D point to animal for quick lookup on map.
     */
    private final Map<Point2D, IAnimal> pointToAnimalMap = new HashMap<>();
    /**
     * Map from geographic position to animal for map view.
     */
    private final Map<GeoPosition, IAnimal> positionToAnimalMap = new HashMap<>();
    /**
     * Cache mapping addresses to geographic positions.
     */
    private final Map<String, GeoPosition> geocodingCache = new HashMap<>();

    /**
     * Constructs the view and initializes all GUI components.
     * Sets up the main window, panels, and listeners.
     * @param controller the controller to interact with business logic
     */
    public View(IController controller) {
        this.controller = controller;
        this.controller.setView(this);
        // Initialize components
        tabbedPane = new JTabbedPane();
        listPanel = new JPanel(new BorderLayout());
        mapPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        animalList = new JList<>(listModel);
        selectedListModel = new DefaultListModel<>();
        selectedAnimalList = new JList<>(selectedListModel);
        filterPanel = new JPanel();
        typeComboBox = new JComboBox<>();
        breedComboBox = new JComboBox<>();
        sizeComboBox = new JComboBox<>();
        genderComboBox = new JComboBox<>();
        patternComboBox = new JComboBox<>();
        colorComboBox = new JComboBox<>();
        ageComboBox = new JComboBox<>();
        cityComboBox = new JComboBox<>();
        dateRangeComboBox = new JComboBox<>();
        filterButton = new JButton("Apply Filter");
        resetButton = new JButton("Reset");
        reportButton = new JButton("Report Animal");
        mapViewer = new JXMapViewer();
        sortComboBox = new JComboBox<>(new String[]{"", "Ascending", "Descending"});
        sortButton = new JButton("Sort");
        sortButton.setMaximumSize(new Dimension(200, 100));
        selectedAnimals = new HashSet<>();

        // Set up window
        setTitle("STRAY ANIMAL SPOTTER");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize panels
        initializeListPanel();
        initializeMapPanel();
        initializeFilterPanel();

        // Add components to window
        tabbedPane.addTab("List view", listPanel);
        tabbedPane.addTab("Map view", mapPanel);
        add(tabbedPane, BorderLayout.CENTER);
        add(reportButton, BorderLayout.NORTH);

        // Add action listeners
        reportButton.addActionListener(e -> showReportDialog());
        filterButton.addActionListener(e -> applyFilters());
        resetButton.addActionListener(e -> controller.handleReset());
        sortButton.addActionListener(e -> {
            String sortOrder = (String) sortComboBox.getSelectedItem();
            if (sortOrder != null) {
                if (sortOrder.isEmpty()) {
                    controller.handleSort();  // default on ascending
                } else {
                    boolean ascending = "Ascending".equals(sortOrder);
                    controller.handleSort(ascending);
                }
            }
        });

        // Initialize view
        controller.initialize();

        // Initialize selectedAnimalList in the constructor
        selectedAnimalList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
                JPanel panel = new JPanel(new BorderLayout(0, 0));  // Set layout manager gap to 0
                panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // Remove outer margin
                
                JPanel infoPanel = new JPanel(new BorderLayout(0, 0));  // Set layout manager gap to 0
                infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // Remove inner margin
                JLabel label = new JLabel();

                if (value instanceof IAnimal animal) {
                    String text = String.format("%s %s | %s %s | %s %s | %s %s | %s %s | %s %s",
                            "Type:", animal.getAnimalType(),
                            "Breed:", animal.getSpecies(),
                            "Date:", animal.getSeenDate(),
                            "Time:", animal.getTime(),
                            "Area:", animal.getArea(),
                            "Address:", animal.getAddress());
                    label.setText(text);
                    
                    // Create button panel with minimal spacing
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  // 设置按钮间距为0
                    
                    // Create delete button
                    JButton deleteButton = new JButton("Delete");
                    deleteButton.setMargin(new Insets(0, 2, 0, 2));  // Reduce button padding
                    deleteButton.addActionListener(e -> {
                        selectedListModel.removeElement(animal);  // 只从选中列表中删除
                    });
                    
                    // Add button to button panel
                    buttonPanel.add(deleteButton);
                    
                    // Add label and button panel to info panel
                    infoPanel.add(label, BorderLayout.CENTER);
                    infoPanel.add(buttonPanel, BorderLayout.EAST);
                }

                panel.add(infoPanel, BorderLayout.CENTER);

                if (isSelected) {
                    panel.setBackground(list.getSelectionBackground());
                    panel.setForeground(list.getSelectionForeground());
                } else {
                    panel.setBackground(list.getBackground());
                    panel.setForeground(list.getForeground());
                }
                
                return panel;
            }
        });
        
        // Set selection mode and other properties
        selectedAnimalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedAnimalList.setVisibleRowCount(3);  // Reduce visible rows from 6 to 3
        
        // Add mouse click event listener
        selectedAnimalList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = selectedAnimalList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Rectangle cellBounds = selectedAnimalList.getCellBounds(index, index);
                        if (cellBounds != null) {
                            // Get click position relative to list item
                            int relativeX = e.getX() - cellBounds.x;

                            if (relativeX < cellBounds.width * 0.95) {
                                // If click is on the left area (<95% width), show details
                                IAnimal animal = selectedListModel.getElementAt(index);
                                showAnimalDetails(animal);
                            } else if (relativeX > cellBounds.width * 0.95) {
                                // If click is on the right area (>95% width), remove the item
                                selectedListModel.remove(index);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Initializes the list panel, including the animal list, selected animal list,
     * mouse listeners for selection and actions, and associated buttons.
     */
    private void initializeListPanel() {
        // Set preferred and maximum size for filterPanel
        filterPanel.setPreferredSize(new Dimension(250, 0));
        filterPanel.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        listPanel.add(filterPanel, BorderLayout.WEST);

        animalList.setCellRenderer(new AnimalListCellRenderer());
        animalList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = animalList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Rectangle cellBounds = animalList.getCellBounds(index, index);
                        if (cellBounds != null) {
                            // Get click position relative to list item
                            int relativeX = e.getX() - cellBounds.x;
                            
                            // If click is on the left area (<70% width), show details
                            if (relativeX < cellBounds.width * 0.7) {
                                showAnimalDetails(listModel.getElementAt(index));
                            }
                        }
                    }
                }
            }
        });

        animalList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = animalList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Rectangle cellBounds = animalList.getCellBounds(index, index);
                        if (cellBounds != null) {
                            // Get click position relative to list item
                            int relativeX = e.getX() - cellBounds.x;

                            // If click is on the right area (>90% width), add to list
                            if (relativeX > cellBounds.width * 0.9 && relativeX < cellBounds.width) {
                                if (!selectedAnimals.isEmpty()) {
                                    for (IAnimal animal : selectedAnimals) {
                                        if (!selectedListModel.contains(animal)) {
                                            selectedListModel.addElement(animal);
                                        }
                                    }
                                    selectedAnimals.clear();
                                    animalList.clearSelection();
                                }
                            }
                            // If click is in the left area (80%~90% width), show details
                            if (relativeX > cellBounds.width * 0.8 && relativeX < cellBounds.width * 0.9) {
                                showAnimalDetails(listModel.getElementAt(index));
                            }
                        }
                    }
                }
            }
        });

        listPanel.add(new JScrollPane(animalList), BorderLayout.CENTER);

        // Add selected animal list to list panel
        JPanel selectedPanel = new JPanel(new BorderLayout());
        selectedPanel.add(new JLabel("Selected Animals:"), BorderLayout.NORTH);
        
        // Set selectedAnimalList display rows and preferred size
        selectedAnimalList.setVisibleRowCount(3);  // Reduce visible rows from 6 to 3
        JScrollPane selectedScrollPane = new JScrollPane(selectedAnimalList);
        selectedScrollPane.setPreferredSize(new Dimension(0, 100));  // Reduce height from 300 pixels to 100 pixels
        
        selectedPanel.add(selectedScrollPane, BorderLayout.CENTER);
        listPanel.add(selectedPanel, BorderLayout.SOUTH);

        // Add button to list panel
        JPanel buttonPanel = new JPanel();
        //buttonPanel.add(addSelectedButton);
        listPanel.add(buttonPanel, BorderLayout.NORTH);

        // Set list selection mode
        animalList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        animalList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedAnimals.clear();
                selectedAnimals.addAll(animalList.getSelectedValuesList());
            }
        });
    }

    /**
     * Initializes the filter panel with combo boxes and filter options for animal attributes.
     * Sets up listeners for filter changes and populates filter options.
     */
    private void initializeFilterPanel() {
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

        // Initialize combo boxes with empty first option
        String[] emptyOption = {""};
        String[] typeOptions = combineArrays(emptyOption, getEnumValues(AnimalType.class));
        String[] sizeOptions = combineArrays(emptyOption, getEnumValues(Size.class));
        String[] genderOptions = combineArrays(emptyOption, getEnumValues(Gender.class));
        String[] patternOptions = combineArrays(emptyOption, getEnumValues(Pattern.class));
        String[] colorOptions = combineArrays(emptyOption, getEnumValues(Model.AnimalInfo.Color.class));
        String[] ageOptions = combineArrays(emptyOption, getEnumValues(Age.class));
        String[] cityOptions = combineArrays(emptyOption, getEnumValues(Area.class));
        String[] dateRangeOptions = combineArrays(emptyOption, new String[]{
                "within 1 week", "within 2 weeks", "within 1 month", "within 3 months"
        });

        typeComboBox.setModel(new DefaultComboBoxModel<>(typeOptions));
        sizeComboBox.setModel(new DefaultComboBoxModel<>(sizeOptions));
        genderComboBox.setModel(new DefaultComboBoxModel<>(genderOptions));
        patternComboBox.setModel(new DefaultComboBoxModel<>(patternOptions));
        colorComboBox.setModel(new DefaultComboBoxModel<>(colorOptions));
        ageComboBox.setModel(new DefaultComboBoxModel<>(ageOptions));
        cityComboBox.setModel(new DefaultComboBoxModel<>(cityOptions));
        dateRangeComboBox.setModel(new DefaultComboBoxModel<>(dateRangeOptions));
        breedComboBox.setModel(new DefaultComboBoxModel<>(emptyOption));

        // Add type selection listener
        typeComboBox.addActionListener(e -> updateBreedOptions());

        // Create containers for labels and combo boxes
        JPanel[] filterRows = new JPanel[9];  // 9 filter conditions
        for (int i = 0; i < filterRows.length; i++) {
            filterRows[i] = new JPanel();
            filterRows[i].setLayout(new BoxLayout(filterRows[i], BoxLayout.Y_AXIS));
            filterRows[i].setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // Create left-aligned labels
        JLabel[] labels = new JLabel[]{
            new JLabel("Animal Type:"),
            new JLabel("Breed:"),
            new JLabel("Size:"),
            new JLabel("Gender:"),
            new JLabel("Pattern:"),
            new JLabel("Color:"),
            new JLabel("Age:"),
            new JLabel("City:"),
            new JLabel("Date Range:")
        };

        // Set labels left-aligned
        for (JLabel label : labels) {
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // Set combo boxes left-aligned and set max width
        JComboBox<?>[] comboBoxes = {
            typeComboBox, breedComboBox, sizeComboBox, genderComboBox,
            patternComboBox, colorComboBox, ageComboBox, cityComboBox, dateRangeComboBox
        };
        for (JComboBox<?> comboBox : comboBoxes) {
            comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            comboBox.setMaximumSize(new Dimension(200, comboBox.getPreferredSize().height));
        }

        // Add labels and combo boxes to their containers
        for (int i = 0; i < filterRows.length; i++) {
            filterRows[i].add(labels[i]);
            filterRows[i].add(comboBoxes[i]);
            filterPanel.add(filterRows[i]);
        }

        // Add buttons with left alignment
        filterButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterButton.setMaximumSize(new Dimension(200, 100));
        resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        resetButton.setMaximumSize(new Dimension(200, 100));
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);

        // Add sort components to filter panel
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));
        sortPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel sortLabel = new JLabel("Sort by Date:");
        sortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortComboBox.setMaximumSize(new Dimension(200, sortComboBox.getPreferredSize().height));
        
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);
        sortPanel.add(sortButton);
        filterPanel.add(sortPanel);

        // Add export functionality
        JPanel exportPanel = new JPanel();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel exportLabel = new JLabel("Export as:");
        exportLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JComboBox<String> exportFormatComboBox = new JComboBox<>(new String[]{"", "TXT", "XML", "JSON", "CSV"});
        exportFormatComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        exportFormatComboBox.setMaximumSize(new Dimension(200, exportFormatComboBox.getPreferredSize().height));

        JButton exportButton = getExportJButton(exportFormatComboBox);
        JButton addAllButton = getAddAllJButton();

        exportPanel.add(exportLabel);
        exportPanel.add(exportFormatComboBox);
        exportPanel.add(exportButton);
        exportPanel.add(addAllButton);
        filterPanel.add(exportPanel);
    }

    /**
     * Creates and returns a JButton for exporting selected animal data in the chosen format.
     * @param exportFormatComboBox the combo box for selecting export format
     * @return the export JButton
     */
    private JButton getExportJButton(JComboBox<String> exportFormatComboBox) {
        JButton exportButton = new JButton("Export");
        exportButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        exportButton.setMaximumSize(new Dimension(200, 100));

        exportButton.addActionListener(e -> {
            String format = (String) exportFormatComboBox.getSelectedItem();
            if (format != null) {
                // Get animals from selectedAnimalList instead of selectedAnimals
                List<IAnimal> animalsToExport = new ArrayList<>();
                for (int i = 0; i < selectedListModel.size(); i++) {
                    animalsToExport.add(selectedListModel.getElementAt(i));
                }
                if (format.isEmpty()) {
                    format = "TXT";
                }
                if (animalsToExport.isEmpty()) {
                    controller.exportData(format.toLowerCase());
                } else {
                    controller.exportData(animalsToExport, format.toLowerCase());
                }
            }
        });

        return exportButton;
    }

    /**
     * Creates and returns a JButton for adding all filtered animals to the selected list.
     * @return the add all JButton
     */
    private JButton getAddAllJButton() {
        JButton addAllButton = new JButton("Add all");
        addAllButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addAllButton.setMaximumSize(new Dimension(200, 100));

        addAllButton.addActionListener(e -> {
            selectedListModel.addAll(controller.getFilteredAnimals());
        });

        return addAllButton;
    }

    /**
     * Combines two string arrays into one.
     * @param first the first array
     * @param second the second array
     * @return the combined array
     */
    private String[] combineArrays(String[] first, String[] second) {
        String[] result = new String[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * Updates the breed options in the breedComboBox based on the selected animal type.
     */
    private void updateBreedOptions() {
        String type = (String) typeComboBox.getSelectedItem();
        breedComboBox.removeAllItems();
        breedComboBox.addItem(""); // Add empty option first

        if (type != null && !type.isEmpty()) {
            String[] breeds = switch (type) {
                case "DOG" -> getEnumValues(Dogs.class);
                case "CAT" -> getEnumValues(Cats.class);
                case "BIRD" -> getEnumValues(Birds.class);
                case "RABBIT" -> getEnumValues(Rabbits.class);
                case "HAMSTER" -> getEnumValues(Hamsters.class);
                case "HEDGEHOG" -> getEnumValues(Hedgehogs.class);
                case "GOOSE" -> getEnumValues(Geese.class);
                case "DUCK" -> getEnumValues(Ducks.class);
                default -> null;
            };
            for (String breed : breeds) {
                breedComboBox.addItem(breed);
            }
        }
    }

    /**
     * Returns an array of string names for all values of the specified enum class.
     * @param enumClass the enum class
     * @param <T> the enum type
     * @return array of enum value names
     */
    private <T extends Enum<T>> String[] getEnumValues(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].name();
        }
        return result;
    }

    /**
     * Displays a dialog with detailed information about the selected animal.
     * @param animal the animal whose details are to be shown
     */
    private void showAnimalDetails(IAnimal animal) {
        JDialog dialog = new JDialog(this, "Animal Details", true);
        dialog.setLayout(new BorderLayout());

        // Create image panel
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(300, 300));
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));

        // Load image
        String imagePath = animal.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Use original image path directly, no modification
                ImageIcon originalIcon = new ImageIcon(imagePath);

                // Calculate scaled size while maintaining aspect ratio
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                int maxSize = 300;

                int scaledWidth;
                int scaledHeight;
                if (originalWidth > originalHeight) {
                    scaledWidth = maxSize;
                    scaledHeight = (int) ((double) originalHeight / originalWidth * maxSize);
                } else {
                    scaledHeight = maxSize;
                    scaledWidth = (int) ((double) originalWidth / originalHeight * maxSize);
                }

                // Scale image with aspect ratio preserved
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                        scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                // Create image label and center it
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imagePanel.add(imageLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                JLabel noImageLabel = new JLabel("No Image Available");
                noImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imagePanel.add(noImageLabel, BorderLayout.CENTER);
            }
        } else {
            JLabel noImageLabel = new JLabel("No Image Available");
            noImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imagePanel.add(noImageLabel, BorderLayout.CENTER);
        }

        // Create info panel
        //JPanel infoPanel = new JPanel(new GridLayout(0, 1, 0, 0)); // Remove horizontal and vertical spacing

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // Remove margin

        // Add animal info
        addInfoField(infoPanel, "Type:", animal.getAnimalType());
        addInfoField(infoPanel, "Breed:", animal.getSpecies());
        addInfoField(infoPanel, "Size:", animal.getAnimalSize());
        addInfoField(infoPanel, "Gender:", animal.getGender());
        addInfoField(infoPanel, "Pattern:", animal.getPattern());
        addInfoField(infoPanel, "Color:", animal.getColor());
        addInfoField(infoPanel, "Age:", animal.getAge());
        addInfoField(infoPanel, "Date Seen:", animal.getSeenDate());
        addInfoField(infoPanel, "Time Seen:", animal.getTime());
        addInfoField(infoPanel, "City:", animal.getArea());
        addInfoField(infoPanel, "Address:", animal.getAddress());
        addInfoField(infoPanel, "Location Description:", animal.getLocDesc());
        addInfoField(infoPanel, "Description:", animal.getDescription());
        //addInfoField(infoPanel, "Image Path:", imagePath); // Add image path info

        // Create scroll pane to contain info panel
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Animal Information"));

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // Add components to dialog
        dialog.add(imagePanel, BorderLayout.WEST);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog size and position
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    /**
     * Adds a labeled information field to the specified panel.
     * @param panel the panel to add the field to
     * @param label the label for the field
     * @param value the value/content for the field
     */
    private void addInfoField(JPanel panel, String label, String value) {
        // Create a panel to hold label and content
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 0));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fieldPanel.setOpaque(false); // Set panel transparent

        // 创建标签
        JLabel labelComponent = new JLabel(label);
        labelComponent.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // Create text field
        JTextField textField;
        if (label.equals("Description:")) {
            // Create multiline text area for description field
            JTextArea textArea = new JTextArea(value);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setRows(5); // Increase rows to 5
            textArea.setColumns(20); // Reduce columns
            textArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            textArea.setOpaque(true); // Set text area transparent
            textArea.setBackground(Color.WHITE); // Set white background

            // Add text area to scroll pane
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            scrollPane.setPreferredSize(new Dimension(250, 100)); // Increase height to 100
            scrollPane.setOpaque(false); // Set scroll pane transparent
            scrollPane.getViewport().setOpaque(false); // Set viewport transparent

            // Add label and scroll pane to field panel
            fieldPanel.add(labelComponent, BorderLayout.WEST);
            fieldPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            // Create single-line text field for other fields
            textField = new JTextField(value);
            textField.setEditable(false);
            textField.setColumns(20); // Reduce columns
            textField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            textField.setOpaque(false); // Set text field transparent
            textField.setBackground(new Color(0, 0, 0, 0)); // Set transparent background

            // Add label and text field to field panel
            fieldPanel.add(labelComponent, BorderLayout.WEST);
            fieldPanel.add(textField, BorderLayout.CENTER);
        }

        // Add the whole field panel to the main panel
        panel.add(fieldPanel);
    }

    /**
     * Displays a dialog for reporting a new animal sighting, including image upload and attribute selection.
     */
    private void showReportDialog() {
        JDialog dialog = new JDialog(this, "Report Animal", true);
        dialog.setLayout(new GridLayout(0, 2));

        JComboBox<String> typeComboBox = new JComboBox<>(getEnumValues(AnimalType.class));
        JComboBox<String> breedComboBox = new JComboBox<>();
        JComboBox<String> sizeComboBox = new JComboBox<>(getEnumValues(Size.class));
        JComboBox<String> genderComboBox = new JComboBox<>(getEnumValues(Gender.class));
        JComboBox<String> patternComboBox = new JComboBox<>(getEnumValues(Pattern.class));
        JComboBox<String> colorComboBox = new JComboBox<>(getEnumValues(Model.AnimalInfo.Color.class));
        JComboBox<String> ageComboBox = new JComboBox<>(getEnumValues(Age.class));
        JComboBox<String> cityComboBox = new JComboBox<>(getEnumValues(Area.class));
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField addressField = new JTextField();
        JTextArea locDescArea = new JTextArea(3, 20);
        JTextArea descriptionArea = new JTextArea(3, 20);
        JButton imageButton = new JButton("Select Image");
        JLabel imageLabel = new JLabel("No image selected");
        
        // Use an array to store the selected file (arrays are effectively final)
        final File[] selectedImageFile = new File[1];

        // Add empty option to breed combo box
        breedComboBox.addItem("");

        // Update breed options when type changes
        typeComboBox.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            breedComboBox.removeAllItems();
            breedComboBox.addItem(""); // Add empty option first

            if (type != null && !type.isEmpty()) {
                String[] breeds = switch (type) {
                    case "DOG" -> getEnumValues(Dogs.class);
                    case "CAT" -> getEnumValues(Cats.class);
                    case "BIRD" -> getEnumValues(Birds.class);
                    case "RABBIT" -> getEnumValues(Rabbits.class);
                    case "HAMSTER" -> getEnumValues(Hamsters.class);
                    case "HEDGEHOG" -> getEnumValues(Hedgehogs.class);
                    case "GOOSE" -> getEnumValues(Geese.class);
                    case "DUCK" -> getEnumValues(Ducks.class);
                    default -> null;
                };
                for (String breed : breeds) {
                    breedComboBox.addItem(breed);
                }
            }
        });

        dialog.add(new JLabel("Animal Type:"));
        dialog.add(typeComboBox);
        dialog.add(new JLabel("Breed:"));
        dialog.add(breedComboBox);
        dialog.add(new JLabel("Size:"));
        dialog.add(sizeComboBox);
        dialog.add(new JLabel("Gender:"));
        dialog.add(genderComboBox);
        dialog.add(new JLabel("Pattern:"));
        dialog.add(patternComboBox);
        dialog.add(new JLabel("Color:"));
        dialog.add(colorComboBox);
        dialog.add(new JLabel("Age:"));
        dialog.add(ageComboBox);
        dialog.add(new JLabel("Date Seen (MM/DD/YYYY):"));
        dialog.add(dateField);
        dialog.add(new JLabel("Time Seen:"));
        dialog.add(timeField);
        dialog.add(new JLabel("City:"));
        dialog.add(cityComboBox);
        dialog.add(new JLabel("Address:"));
        dialog.add(addressField);
        dialog.add(new JLabel("Location Description:"));
        dialog.add(new JScrollPane(locDescArea));
        dialog.add(new JLabel("Description:"));
        dialog.add(new JScrollPane(descriptionArea));
        dialog.add(new JLabel("Image:"));
        dialog.add(imageButton);

        AtomicReference<String> fileName = new AtomicReference<>();
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                selectedImageFile[0] = fileChooser.getSelectedFile();
                imageLabel.setText(selectedImageFile[0].getName());
                
                // Create data/image directory if it doesn't exist
                File imageDir = new File(IAnimal.IMG_SRC);

                // Copy the selected file to data/image directory
                try {
                    String extension = selectedImageFile[0].getName().substring(selectedImageFile[0]
                            .getName().lastIndexOf("."));
                    String newFileName = controller.getNextAnimalNumberAsString() + extension;
                    fileName.set(newFileName);
                    File destFile = new File(imageDir, newFileName);
                    
                    // Copy the file
                    Files.copy(selectedImageFile[0].toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    imageLabel.setText("Image saved as: " + newFileName);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Error saving image: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            IAnimal animal = controller.createAnimal(
                    (String) typeComboBox.getSelectedItem(),
                    (String) breedComboBox.getSelectedItem(),
                    (String) sizeComboBox.getSelectedItem(),
                    (String) genderComboBox.getSelectedItem(),
                    (String) patternComboBox.getSelectedItem(),
                    (String) colorComboBox.getSelectedItem(),
                    (String) ageComboBox.getSelectedItem(),
                    (String) cityComboBox.getSelectedItem(),
                    addressField.getText(),
                    timeField.getText(),
                    dateField.getText(),
                    descriptionArea.getText(),
                    locDescArea.getText(),
                    IAnimal.IMG_SRC + fileName.toString()
            );
            controller.addAnimal(animal);
            dialog.dispose();
        });

        dialog.add(submitButton);
        dialog.add(new JButton("Cancel"));

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    public void displayAnimals(List<IAnimal> animals) {
        listModel.clear();
        for (IAnimal animal : animals) {
            listModel.addElement(animal);
        }
    }

    @Override
    public void showFilterOptions() {
        filterPanel.setVisible(true);
    }

    @Override
    public void showSortOptions() {
        sortComboBox.setVisible(true);
        sortButton.setVisible(true);
    }

    @Override
    public boolean getSortOrder() {
        String sortOrder = (String) sortComboBox.getSelectedItem();
        return "Ascending".equals(sortOrder);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String getSelectedFilter() {
        return (String) typeComboBox.getSelectedItem();
    }

    @Override
    public String getFilterValue() {
        return (String) breedComboBox.getSelectedItem();
    }

    @Override
    public void updateView() {
        displayAnimals(controller.getFilteredAnimals());
    }

    /**
     * Applies the selected filter criteria to the animal list and updates the view.
     */
    private void applyFilters() {
        String type = (String) typeComboBox.getSelectedItem();
        String breed = (String) breedComboBox.getSelectedItem();
        String size = (String) sizeComboBox.getSelectedItem();
        String gender = (String) genderComboBox.getSelectedItem();
        String pattern = (String) patternComboBox.getSelectedItem();
        String color = (String) colorComboBox.getSelectedItem();
        String age = (String) ageComboBox.getSelectedItem();
        String area = (String) cityComboBox.getSelectedItem();
        String dateRange = (String) dateRangeComboBox.getSelectedItem();

        // Remove all filters
        controller.resetFilteredAnimals();

        // Apply filters in sequence
        if (type != null && !type.isEmpty()) {
            controller.handleFilter("TYPE", type);
        }
        if (breed != null && !breed.isEmpty()) {
            controller.handleFilter("SPECIES", breed);
        }
        if (size != null && !size.isEmpty()) {
            controller.handleFilter("SIZE", size);
        }
        if (gender != null && !gender.isEmpty()) {
            controller.handleFilter("GENDER", gender);
        }
        if (pattern != null && !pattern.isEmpty()) {
            controller.handleFilter("PATTERN", pattern);
        }
        if (color != null && !color.isEmpty()) {
            controller.handleFilter("COLOR", color);
        }
        if (age != null && !age.isEmpty()) {
            controller.handleFilter("AGE", age);
        }
        if (area != null && !area.isEmpty()) {
            controller.handleFilter("AREA", area);
        }
        if (dateRange != null && !dateRange.isEmpty()) {
            controller.handleFilter("SEENDATE", dateRange);
        }

        
        // Update map view to display filtered animals - but don't automatically switch to map view
        List<IAnimal> filteredAnimals = controller.getFilteredAnimals();
        System.out.println("Filtered animals count: " + filteredAnimals.size());
    }

    private static final class AnimalListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(0, 0));  // Set layout manager gap to 0
            panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // Remove outer margin
            
            JPanel infoPanel = new JPanel(new BorderLayout(0, 0));  // Set layout manager gap to 0
            infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // Remove inner margin
            JLabel label = new JLabel();

            if (value instanceof IAnimal animal) {
                String text = String.format("%s %s | %s %s | %s %s | %s %s | %s %s | %s %s",
                        "Type:", animal.getAnimalType(),
                        "Breed:", animal.getSpecies(),
                        "Date:", animal.getSeenDate(),
                        "Time:", animal.getTime(),
                        "Area:", animal.getArea(),
                        "Address:", animal.getAddress());
                label.setText(text);
//                label.setText(text.toString());
                
                // Create button panel with minimal spacing
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  // 设置按钮间距为0
                
                // Create Add to List button
                JButton addToListButton = new JButton("Add to List");
                addToListButton.setMargin(new Insets(0, 2, 0, 2));  // Reduce button padding

                // Create view Details button
                JButton viewDetailsButton = new JButton("view Details");
                viewDetailsButton.setMargin(new Insets(0, 2, 0, 2));  // Reduce button padding

                // Add button to button panel
                buttonPanel.add(viewDetailsButton);
                buttonPanel.add(addToListButton);

                // Add label and button panel to info panel
                infoPanel.add(label, BorderLayout.CENTER);
                infoPanel.add(buttonPanel, BorderLayout.EAST);
            }

            panel.add(infoPanel, BorderLayout.CENTER);

            if (isSelected) {
                panel.setBackground(list.getSelectionBackground());
                panel.setForeground(list.getSelectionForeground());
            } else {
                panel.setBackground(list.getBackground());
                panel.setForeground(list.getForeground());
            }

            return panel;
        }
    }



    private void initializeMapPanel() {
        // Set up map
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Set initial map position
        GeoPosition seattle = new GeoPosition(47.6062, -122.3321);
        mapViewer.setZoom(3);
        mapViewer.setAddressLocation(seattle);

        // Add mouse controls
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        mapPanel.add(mapViewer, BorderLayout.CENTER);
        
        // Add refresh map button
        JButton refreshMapButton = new JButton("Refresh Map");
        refreshMapButton.addActionListener(e -> {
            List<IAnimal> animals = controller.getFilteredAnimals();
            System.out.println("Refreshing map, loading animal count: " + animals.size());
            displayMap(animals);
        });
        
        // Add a control panel
        JPanel mapControlPanel = new JPanel();
        mapControlPanel.setLayout(new BoxLayout(mapControlPanel, BoxLayout.Y_AXIS));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(refreshMapButton);
        
        // Add legend
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Color legend
        legendPanel.add(new JLabel("Color Legend: "));
        
        JLabel dogLabel = new JLabel("Dogs");
        dogLabel.setForeground(new Color(65, 105, 225));
        legendPanel.add(dogLabel);
        
        JLabel catLabel = new JLabel("Cats");
        catLabel.setForeground(new Color(220, 20, 60));
        legendPanel.add(catLabel);
        
        JLabel birdLabel = new JLabel("Birds");
        birdLabel.setForeground(new Color(50, 205, 50));
        legendPanel.add(birdLabel);
        
        JLabel rabbitLabel = new JLabel("Rabbits");
        rabbitLabel.setForeground(new Color(255, 165, 0));
        legendPanel.add(rabbitLabel);

        JLabel hamsterLabel = new JLabel("Hamsters");
        hamsterLabel.setForeground(new Color(255, 215, 0));
        legendPanel.add(hamsterLabel);

        JLabel hedgehogLabel = new JLabel("Hedgehogs");
        hedgehogLabel.setForeground(new Color(139, 69, 19));
        legendPanel.add(hedgehogLabel);


        JLabel gooseLabel = new JLabel("Geese");
        gooseLabel.setForeground(new Color(0, 191, 255));
        legendPanel.add(gooseLabel);

        JLabel duckLabel = new JLabel("Ducks");
        duckLabel.setForeground(new Color(34, 139, 34));
        legendPanel.add(duckLabel);

        // Add map info text
        JLabel mapInfoLabel = new JLabel("Map shows all animals matching your filters. "
                + "Click on markers to see animal details.");
        mapInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mapControlPanel.add(buttonPanel);
        mapControlPanel.add(legendPanel);
        mapControlPanel.add(mapInfoLabel);
        
        mapPanel.add(mapControlPanel, BorderLayout.NORTH);
        
        // Set tab change listener
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) { // Map view selected
                // Automatically update map when switching to map view
                System.out.println("Switching to map view");
                controller.handleMapDisplay();
            }
        });
    }

    @Override
    public void displayMap(List<IAnimal> animals) {
        System.out.println("Starting map display, total animals: " + animals.size());
        Set<WaypointWithInfo> waypoints = new HashSet<>();

        // Clear both mappings first
        pointToAnimalMap.clear();
        positionToAnimalMap.clear();

        // Debug output for each animal type
        System.out.println("---- Animal types in this display ----");
        for (IAnimal animal : animals) {
            System.out.println("Animal Type: " + animal.getAnimalType() + ", Species: " + animal.getSpecies());
        }
        System.out.println("-----------------------------------");

        for (IAnimal animal : animals) {
            String fullAddress = animal.getAddress() + ", " + animal.getArea();
            try {
                // Get geographic position
                GeoPosition position = getGeoPosition(fullAddress);
                if (position != null) {
                    System.out.println("Got coordinates from address: " + position.getLatitude() + ", "
                            + position.getLongitude());
                    waypoints.add(new WaypointWithInfo(position, animal));
                    // Store in position map
                    positionToAnimalMap.put(position, animal);
                }
            } catch (Exception e) {
                System.err.println("Error displaying animal location: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Total map markers: " + waypoints.size());

        // If there are coordinates, set the map center to the first point
        if (!waypoints.isEmpty()) {
            WaypointWithInfo firstWaypoint = waypoints.iterator().next();
            GeoPosition pos = firstWaypoint.getPosition();
            System.out.println("Setting map center to: " + pos.getLatitude() + ", " + pos.getLongitude());
            mapViewer.setAddressLocation(pos);
            mapViewer.setZoom(14); // Zoom to appropriate level
        } else {
            System.out.println("No valid coordinates, map remains unchanged");
        }

        // Use custom marker renderer and painter
        WaypointPainter<WaypointWithInfo> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new AnimalWaypointRenderer());
        mapViewer.setOverlayPainter(waypointPainter);

        // Remove previous click listeners
        for (MouseListener listener : mapViewer.getMouseListeners()) {
            if (listener instanceof MouseAdapter && !(listener instanceof PanMouseInputListener)) {
                mapViewer.removeMouseListener(listener);
            }
        }

        // Add new click listener for map interactions
        final Set<WaypointWithInfo> finalWaypoints = waypoints;
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPoint = e.getPoint();
                System.out.println("地图点击位置: (" + clickPoint.x + ", " + clickPoint.y + ")");

                // Direct comparison of screen coordinates to determine distance between click point and animal markers
                boolean pointClicked = false;

                // Loop through all visible markers
                for (WaypointWithInfo waypoint : finalWaypoints) {
                    try {
                        // Convert geographic coordinates to screen coordinates
                        GeoPosition pos = waypoint.getPosition();

                        // Use viewer's conversion method, not directly using tile factory
                        // This is key - ensure we get the correct screen coordinates relative to the current view
                        Rectangle viewportBounds = mapViewer.getViewportBounds();
                        Point2D point2D = mapViewer.getTileFactory().geoToPixel(pos, mapViewer.getZoom());
                        Point mapPoint = new Point(
                                (int) (point2D.getX() - viewportBounds.getX()),
                                (int) (point2D.getY() - viewportBounds.getY())
                        );

                        // Calculate distance between click point and marker point
                        // (both are now in screen coordinate system)
                        double distance = clickPoint.distance(mapPoint);

                        // Output detailed debug information
                        System.out.println("Animal: " + waypoint.getAnimal().getAnimalType()
                                + ", Screen coordinates: (" + mapPoint.x + ", " + mapPoint.y + ")"
                                + ", Distance to click point: " + distance + " pixels");

                        // If distance is less than 20 pixels, consider it a hit
                        if (distance < 20) {  // Slightly increase threshold to make clicking easier
                            pointClicked = true;
                            showAnimalDetails(waypoint.getAnimal());
                            break;
                        }
                    } catch (Exception ex) {
                        System.err.println("Error processing click: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }

                if (!pointClicked) {
                    System.out.println("No animal marker was clicked. Try clicking closer to a colored dot.");
                }
            }
        });

        // Ensure map repaints
        mapViewer.repaint();
        System.out.println("Map display complete");
    }


    /**
     * Get the latitude and longitude of an animal's address.
     * @param fullAddress the address of the animal
     * @return the GeoPosition of the animal
     */
    private GeoPosition getGeoPosition(String fullAddress) {
        // Check cache first
        if (geocodingCache.containsKey(fullAddress)) {
            System.out.println("Using cached coordinates for: " + fullAddress);
            return geocodingCache.get(fullAddress);
        }

        try {
            System.out.println("Attempting to geocode address: " + fullAddress);
            MapGeocoder.GeoLocation geoLocation = MapGeocoder.getCoordinates(fullAddress);
            if (geoLocation != null) {
                System.out.println("Successfully geocoded address to: "
                        + geoLocation.getLatitude() + ", " + geoLocation.getLongitude());
                GeoPosition position = new GeoPosition(geoLocation.getLatitude(), geoLocation.getLongitude());
                // Cache the result
                geocodingCache.put(fullAddress, position);
                System.out.println("Cached coordinates for: " + fullAddress);
                return position;
            }
            System.err.println("Failed to geocode address: " + fullAddress);
            return null;
        } catch (Exception e) {
            System.err.println("Error getting coordinates for address: " + fullAddress);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Waypoint with animal information.
     */
    public static class WaypointWithInfo extends DefaultWaypoint {
        /**
         * The animal associated with this waypoint.
         */
        private final IAnimal animal;

        /**
         * Constructor.
         * @param position the position of the waypoint
         * @param animal the animal associated with the waypoint
         */
        public WaypointWithInfo(GeoPosition position, IAnimal animal) {
            super(position);
            this.animal = animal;
        }

        /**
         * Get the animals.
         * @return the animals
         */
        public IAnimal getAnimal() {
            return animal;
        }

        @Override
        public String toString() {
            return animal.getAnimalType() + " - " + animal.getSpecies() + " at " + animal.getAddress();
        }
    }


    /**
     * Animal waypoint renderer that colors markers based on animal type.
     */
    private static class AnimalWaypointRenderer implements WaypointRenderer<WaypointWithInfo> {
        /**
         * Mapping of animal types to colors for rendering.
         */
        private final Map<String, Color> typeColors = new HashMap<>();

        AnimalWaypointRenderer() {
            // Initialize color mapping
            typeColors.put("DOG", new Color(65, 105, 225)); // Blue
            typeColors.put("CAT", new Color(220, 20, 60));  // Red
            typeColors.put("BIRD", new Color(50, 205, 50)); // Green
            typeColors.put("RABBIT", new Color(255, 165, 0)); // Orange
            typeColors.put("HAMSTER", new Color(255, 215, 0)); // Gold
            typeColors.put("HEDGEHOG", new Color(139, 69, 19)); // Brown
            typeColors.put("GOOSE", new Color(0, 191, 255)); // Light blue
            typeColors.put("DUCK", new Color(34, 139, 34)); // Forest green
        }

        @Override
        public void paintWaypoint(Graphics2D g, JXMapViewer map, WaypointWithInfo waypoint) {
            try {
                // Convert geographic coordinates to screen coordinates
                GeoPosition pos = waypoint.getPosition();
                Point2D point = map.getTileFactory().geoToPixel(pos, map.getZoom());

                // Note: No need to convert to viewport coordinates, as Graphics2D is
                // already in the correct coordinate system
                int x = (int) point.getX();
                int y = (int) point.getY();

                // Get the animal directly from the waypoint
                IAnimal animal = waypoint.getAnimal();
                String animalType = animal.getAnimalType();

                // Normalize to uppercase to solve case sensitivity issues
                String normalizedType = animalType.toUpperCase();

                // Simplified debug output to reduce console clutter
                System.out.println("Painting animal: " + animalType + ", type: " + normalizedType
                        + ", position: (" + x + ", " + y + ")");

                // Determine color based on animal type
                Color dotColor = Color.RED; // Default to red
                if (typeColors.containsKey(normalizedType)) {
                    dotColor = typeColors.get(normalizedType);
                } else {
                    System.out.println("No color mapping found for: " + normalizedType + ", using default red");
                }

                // Increase dot size to make it easier to click
                g.setColor(dotColor);
                Ellipse2D dot = new Ellipse2D.Double(x - 8, y - 8, 16, 16);  // 16x16 pixel size
                g.fill(dot);

                // Add black border to enhance visibility
                g.setColor(Color.BLACK);
                g.draw(dot);
            } catch (Exception e) {
                System.err.println("Error drawing waypoint: " + e.getMessage());
            }
        }
    }
}
