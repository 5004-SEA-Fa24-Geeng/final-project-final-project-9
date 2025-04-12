package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
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
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ListSelectionModel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import Controller.IController;
import Model.AnimalInfo.Age;
import Model.AnimalInfo.AnimalType;
import Model.AnimalInfo.Area;
import Model.AnimalInfo.Gender;
import Model.AnimalInfo.Pattern;
import Model.AnimalInfo.Size;
import Model.Animals.IAnimal;
import Model.Sorts;

public class View extends JFrame implements IView {
    private final IController controller;
    private final JTabbedPane tabbedPane;
    private final JPanel listPanel;
    private final JPanel mapPanel;
    private final JList<IAnimal> animalList;
    private final DefaultListModel<IAnimal> listModel;
    private final JPanel filterPanel;
    private final JComboBox<String> typeComboBox;
    private final JComboBox<String> breedComboBox;
    private final JComboBox<String> sizeComboBox;
    private final JComboBox<String> genderComboBox;
    private final JComboBox<String> patternComboBox;
    private final JComboBox<String> colorComboBox;
    private final JComboBox<String> ageComboBox;
    private final JComboBox<String> cityComboBox;
    private final JComboBox<String> dateRangeComboBox;
    private final JButton filterButton;
    private final JButton resetButton;
    private final JButton reportButton;
    private final JXMapViewer mapViewer;
    private final JComboBox<String> sortComboBox;
    private final JButton sortButton;
    private final JList<IAnimal> selectedAnimalList;
    private final DefaultListModel<IAnimal> selectedListModel;
    private final JButton addSelectedButton;
    private final Set<IAnimal> selectedAnimals;

    public View(IController controller) {
        this.controller = controller;
        this.controller.setView(this);

        // Initialize components
        tabbedPane = new JTabbedPane();
        listPanel = new JPanel(new BorderLayout());
        mapPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        animalList = new JList<>(listModel);
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
        sortButton = new JButton("Sort by Date");

        // Initialize selected animal list
        selectedListModel = new DefaultListModel<>();
        selectedAnimalList = new JList<>(selectedListModel);
        selectedAnimalList.setCellRenderer(new AnimalListCellRenderer());

        // Initialize selected animal collection
        selectedAnimals = new HashSet<>();
        addSelectedButton = new JButton("Add Selected to List");
        addSelectedButton.addActionListener(e -> {
            if (!selectedAnimals.isEmpty()) {
                for (IAnimal animal : selectedAnimals) {
                    if (!selectedListModel.contains(animal)) {
                        selectedListModel.addElement(animal);
                    }
                }
                JOptionPane.showMessageDialog(this, "Added " + selectedAnimals.size() + " animals to the list");
                selectedAnimals.clear();
                animalList.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Please select animals first");
            }
        });

        // Set up window
        setTitle("Animal Finder");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize panels
        initializeListPanel();
        initializeMapPanel();
        initializeFilterPanel();

        // Add components to window
        tabbedPane.addTab("List View", listPanel);
        tabbedPane.addTab("Map View", mapPanel);
        add(tabbedPane, BorderLayout.CENTER);
        add(reportButton, BorderLayout.NORTH);

        // Add action listeners
        reportButton.addActionListener(e -> showReportDialog());
        filterButton.addActionListener(e -> applyFilters());
        resetButton.addActionListener(e -> controller.handleReset());
        sortButton.addActionListener(e -> {
            String sortOrder = (String) sortComboBox.getSelectedItem();
            if (sortOrder != null && !sortOrder.isEmpty()) {
                boolean ascending = "Ascending".equals(sortOrder);
                List<IAnimal> sortedAnimals = controller.getFilteredAnimals();
                sortedAnimals = sortedAnimals.stream()
                    .sorted(Sorts.sortByDate(ascending))
                    .collect(Collectors.toList());
                displayAnimals(sortedAnimals);
            }
        });

        // Initialize view
        controller.initialize();
    }

    private void initializeListPanel() {
        listPanel.add(filterPanel, BorderLayout.WEST);

        animalList.setCellRenderer(new AnimalListCellRenderer());
        animalList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = animalList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        showAnimalDetails(listModel.getElementAt(index));
                    }
                }
            }
        });

        listPanel.add(new JScrollPane(animalList), BorderLayout.CENTER);

        // Add selected animal list to list panel
        JPanel selectedPanel = new JPanel(new BorderLayout());
        selectedPanel.add(new JLabel("Selected Animals:"), BorderLayout.NORTH);
        selectedPanel.add(new JScrollPane(selectedAnimalList), BorderLayout.CENTER);
        listPanel.add(selectedPanel, BorderLayout.SOUTH);

        // Add button to list panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addSelectedButton);
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

    private void initializeMapPanel() {
        // Set up map
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Set initial map position
        GeoPosition seattle = new GeoPosition(47.6062, -122.3321);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(seattle);

        // Add mouse controls
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        mapPanel.add(mapViewer, BorderLayout.CENTER);
    }

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

        // Add filter options to panel
        filterPanel.add(new JLabel("Animal Type:"));
        filterPanel.add(typeComboBox);
        filterPanel.add(new JLabel("Breed:"));
        filterPanel.add(breedComboBox);
        filterPanel.add(new JLabel("Size:"));
        filterPanel.add(sizeComboBox);
        filterPanel.add(new JLabel("Gender:"));
        filterPanel.add(genderComboBox);
        filterPanel.add(new JLabel("Pattern:"));
        filterPanel.add(patternComboBox);
        filterPanel.add(new JLabel("Color:"));
        filterPanel.add(colorComboBox);
        filterPanel.add(new JLabel("Age:"));
        filterPanel.add(ageComboBox);
        filterPanel.add(new JLabel("City:"));
        filterPanel.add(cityComboBox);
        filterPanel.add(new JLabel("Date Range:"));
        filterPanel.add(dateRangeComboBox);

        // Add sort components to filter panel
        filterPanel.add(new JLabel("Sort by Date:"));
        filterPanel.add(sortComboBox);
        filterPanel.add(sortButton);

        // Add buttons
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);
    }

    private String[] combineArrays(String[] first, String[] second) {
        String[] result = new String[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private void updateBreedOptions() {
        String type = (String) typeComboBox.getSelectedItem();
        breedComboBox.removeAllItems();
        breedComboBox.addItem(""); // Add empty option first

        if (type != null && !type.isEmpty()) {
            String[] breeds = switch (type) {
                case "DOG" -> new String[]{"Labrador", "German Shepherd", "Golden Retriever", "Bulldog", "Poodle"};
                case "CAT" -> new String[]{"Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal"};
                case "BIRD" -> new String[]{"Parrot", "Canary", "Finch", "Cockatiel", "Budgie"};
                case "RABBIT" -> new String[]{"Holland Lop", "Mini Rex", "Netherland Dwarf", "Lionhead", "Flemish Giant"};
                case "HAMSTER" -> new String[]{"Syrian", "Dwarf", "Roborovski", "Chinese", "Campbell's"};
                case "GUINEA_PIG" -> new String[]{"American", "Abyssinian", "Peruvian", "Silkie", "Teddy"};
                case "HEDGEHOG" -> new String[]{"African Pygmy", "European", "Long-eared", "Desert", "Indian"};
                case "TURTLE" -> new String[]{"Red-eared Slider", "Box Turtle", "Painted Turtle", "Map Turtle", "Musk Turtle"};
                default -> new String[]{};
            };
            for (String breed : breeds) {
                breedComboBox.addItem(breed);
            }
        }
    }

    private <T extends Enum<T>> String[] getEnumValues(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].name();
        }
        return result;
    }

    private void showAnimalDetails(IAnimal animal) {
        JDialog dialog = new JDialog(this, "Animal Details", true);
        dialog.setLayout(new GridLayout(0, 2));

        dialog.add(new JLabel("Type:"));
        dialog.add(new JLabel(animal.getAnimalType()));
        dialog.add(new JLabel("Breed:"));
        dialog.add(new JLabel(animal.getSpecies()));
        dialog.add(new JLabel("Size:"));
        dialog.add(new JLabel(animal.getAnimalSize()));
        dialog.add(new JLabel("Gender:"));
        dialog.add(new JLabel(animal.getGender()));
        dialog.add(new JLabel("Pattern:"));
        dialog.add(new JLabel(animal.getPattern()));
        dialog.add(new JLabel("Color:"));
        dialog.add(new JLabel(animal.getColor()));
        dialog.add(new JLabel("Age:"));
        dialog.add(new JLabel(animal.getAge()));
        dialog.add(new JLabel("Date Seen:"));
        dialog.add(new JLabel(animal.getSeenDate()));
        dialog.add(new JLabel("Time Seen:"));
        dialog.add(new JLabel(animal.getTime()));
        dialog.add(new JLabel("City:"));
        dialog.add(new JLabel(animal.getArea()));
        dialog.add(new JLabel("Address:"));
        dialog.add(new JLabel(animal.getAddress()));
        dialog.add(new JLabel("Location Description:"));
        dialog.add(new JLabel(animal.getLocDesc()));
        dialog.add(new JLabel("Description:"));
        dialog.add(new JLabel(animal.getDescription()));

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

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

        // Add empty option to breed combo box
        breedComboBox.addItem("");

        // Update breed options when type changes
        typeComboBox.addActionListener(e -> {
            String type = (String) typeComboBox.getSelectedItem();
            breedComboBox.removeAllItems();
            breedComboBox.addItem(""); // Add empty option first

            if (type != null && !type.isEmpty()) {
                String[] breeds = switch (type) {
                    case "DOG" -> new String[]{"Labrador", "German Shepherd", "Golden Retriever", "Bulldog", "Poodle"};
                    case "CAT" -> new String[]{"Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal"};
                    case "BIRD" -> new String[]{"Parrot", "Canary", "Finch", "Cockatiel", "Budgie"};
                    case "RABBIT" -> new String[]{"Holland Lop", "Mini Rex", "Netherland Dwarf", "Lionhead", "Flemish Giant"};
                    case "HAMSTER" -> new String[]{"Syrian", "Dwarf", "Roborovski", "Chinese", "Campbell's"};
                    case "GUINEA_PIG" -> new String[]{"American", "Abyssinian", "Peruvian", "Silkie", "Teddy"};
                    case "HEDGEHOG" -> new String[]{"African Pygmy", "European", "Long-eared", "Desert", "Indian"};
                    case "TURTLE" -> new String[]{"Red-eared Slider", "Box Turtle", "Painted Turtle", "Map Turtle", "Musk Turtle"};
                    default -> new String[]{};
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

        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imageLabel.setText(selectedFile.getName());
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
                    dateField.getText(),
                    timeField.getText(),
                    (String) cityComboBox.getSelectedItem(),
                    addressField.getText(),
                    locDescArea.getText(),
                    descriptionArea.getText(),
                    imageLabel.getText()
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
    public void displayMap(List<IAnimal> animals) {
        Set<Waypoint> waypoints = new HashSet<>();
        for (IAnimal animal : animals) {
            try {
                GeoPosition position = getGeoPosition(animal.getArea());
                waypoints.add(new DefaultWaypoint(position));
            } catch (Exception e) {
                System.err.println("Error getting position for " + animal.getArea() + ": " + e.getMessage());
            }
        }

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);
    }

    private GeoPosition getGeoPosition(String area) {
        return switch (area) {
            case "SEATTLE" -> new GeoPosition(47.6062, -122.3321);
            case "BELLEVUE" -> new GeoPosition(47.6101, -122.2015);
            case "REDMOND" -> new GeoPosition(47.6740, -122.1215);
            case "KIRKLAND" -> new GeoPosition(47.6769, -122.2060);
            case "EVERETT" -> new GeoPosition(47.9789, -122.2021);
            case "TACOMA" -> new GeoPosition(47.2529, -122.4443);
            case "RENTON" -> new GeoPosition(47.4829, -122.2171);
            case "KENT" -> new GeoPosition(47.3809, -122.2348);
            case "LYNNWOOD" -> new GeoPosition(47.8279, -122.3053);
            case "BOTHELL" -> new GeoPosition(47.7601, -122.2054);
            default -> new GeoPosition(47.6062, -122.3321); // Default to Seattle
        };
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

    private void applyFilters() {
        // Get all filter values
        String type = (String) typeComboBox.getSelectedItem();
        String breed = (String) breedComboBox.getSelectedItem();
        String size = (String) sizeComboBox.getSelectedItem();
        String gender = (String) genderComboBox.getSelectedItem();
        String pattern = (String) patternComboBox.getSelectedItem();
        String color = (String) colorComboBox.getSelectedItem();
        String age = (String) ageComboBox.getSelectedItem();
        String city = (String) cityComboBox.getSelectedItem();
        String dateRange = (String) dateRangeComboBox.getSelectedItem();

        // Reset all filters first
        controller.handleReset();

        // Apply only non-empty filters
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
        if (city != null && !city.isEmpty()) {
            controller.handleFilter("AREA", city);
        }
        if (dateRange != null && !dateRange.isEmpty()) {
            controller.handleFilter("SEENDATE", dateRange);
        }
    }

    private class AnimalListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel();
            if (value instanceof IAnimal animal) {
                StringBuilder text = new StringBuilder();
                text.append("Type: ").append(animal.getAnimalType()).append(" | ");
                text.append("Breed: ").append(animal.getSpecies()).append(" | ");
                text.append("Size: ").append(animal.getAnimalSize()).append(" | ");
                text.append("Gender: ").append(animal.getGender()).append(" | ");
                text.append("Pattern: ").append(animal.getPattern()).append(" | ");
                text.append("Color: ").append(animal.getColor()).append(" | ");
                text.append("Age: ").append(animal.getAge()).append(" | ");
                text.append("Date: ").append(animal.getSeenDate()).append(" | ");
                text.append("Time: ").append(animal.getTime()).append(" | ");
                text.append("Area: ").append(animal.getArea()).append(" | ");
                text.append("Address: ").append(animal.getAddress());
                label.setText(text.toString());
            }

            panel.add(label, BorderLayout.CENTER);

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
}