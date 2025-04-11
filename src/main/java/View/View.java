package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.swing.event.MouseInputListener;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    private final JButton sortButton;
    private final JButton resetButton;
    private final JButton reportButton;
    private final JXMapViewer mapViewer;

    {
        // Initialize all final fields
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
        sortButton = new JButton("Sort");
        resetButton = new JButton("Reset");
        reportButton = new JButton("Report Animal");
        mapViewer = new JXMapViewer();
    }

    public View(IController controller) {
        this.controller = controller;
        this.controller.setView(this);

        // Set up window
        setTitle("Animal Finder");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create tabs
        tabbedPane.addTab("List View", listPanel);
        tabbedPane.addTab("Map View", mapPanel);

        // Add tabs to main window
        add(tabbedPane, BorderLayout.CENTER);

        // Add report button
        reportButton.addActionListener(e -> showReportDialog());
        add(reportButton, BorderLayout.NORTH);

        // Initialize views
        initializeListPanel();
        initializeMapPanel();
        initializeFilterPanel();

        // Initialize view
        controller.initialize();
    }

    private void initializeListPanel() {
        // Create filter panel
        listPanel.add(filterPanel, BorderLayout.WEST);
        
        // Set up animal list
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
    }

    private void initializeMapPanel() {
        // Set up map
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Set map center and zoom level
        GeoPosition seattle = new GeoPosition(47.6062, -122.3321);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(seattle);

        // Add mouse event listeners
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        
        // Add map to panel
        mapPanel.add(mapViewer, BorderLayout.CENTER);
        
        // Create filter panel
        JPanel mapFilterPanel = new JPanel();
        mapFilterPanel.setLayout(new BoxLayout(mapFilterPanel, BoxLayout.Y_AXIS));
        
        // Set up filter options
        JComboBox<String> typeComboBox = new JComboBox<>(getEnumValues(AnimalType.class));
        JComboBox<String> breedComboBox = new JComboBox<>();
        JComboBox<String> sizeComboBox = new JComboBox<>(getEnumValues(Size.class));
        JComboBox<String> genderComboBox = new JComboBox<>(getEnumValues(Gender.class));
        JComboBox<String> patternComboBox = new JComboBox<>(getEnumValues(Pattern.class));
        JComboBox<String> colorComboBox = new JComboBox<>(getEnumValues(Model.AnimalInfo.Color.class));
        JComboBox<String> ageComboBox = new JComboBox<>(getEnumValues(Age.class));
        JComboBox<String> cityComboBox = new JComboBox<>(getEnumValues(Area.class));
        JComboBox<String> dateRangeComboBox = new JComboBox<>(new String[]{
            "within 1 week", "within 2 weeks", "within 1 month", "within 3 months"
        });
        
        // Add type selection listener
        typeComboBox.addActionListener(e -> updateBreedOptions(breedComboBox, (String) typeComboBox.getSelectedItem()));
        
        // Add filter options to panel
        mapFilterPanel.add(new JLabel("Animal Type:"));
        mapFilterPanel.add(typeComboBox);
        mapFilterPanel.add(new JLabel("Breed:"));
        mapFilterPanel.add(breedComboBox);
        mapFilterPanel.add(new JLabel("Size:"));
        mapFilterPanel.add(sizeComboBox);
        mapFilterPanel.add(new JLabel("Gender:"));
        mapFilterPanel.add(genderComboBox);
        mapFilterPanel.add(new JLabel("Pattern:"));
        mapFilterPanel.add(patternComboBox);
        mapFilterPanel.add(new JLabel("Color:"));
        mapFilterPanel.add(colorComboBox);
        mapFilterPanel.add(new JLabel("Age:"));
        mapFilterPanel.add(ageComboBox);
        mapFilterPanel.add(new JLabel("City:"));
        mapFilterPanel.add(cityComboBox);
        mapFilterPanel.add(new JLabel("Date Range:"));
        mapFilterPanel.add(dateRangeComboBox);
        
        // Add sort and reset buttons
        JButton sortButton = new JButton("Sort");
        JButton resetButton = new JButton("Reset");
        sortButton.addActionListener(e -> controller.handleSort(true));
        resetButton.addActionListener(e -> controller.handleReset());
        
        mapFilterPanel.add(sortButton);
        mapFilterPanel.add(resetButton);
        
        mapPanel.add(mapFilterPanel, BorderLayout.WEST);
    }

    private void initializeFilterPanel() {
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        
        // Set up filter options
        typeComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(AnimalType.class)));
        sizeComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Size.class)));
        genderComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Gender.class)));
        patternComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Pattern.class)));
        colorComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Model.AnimalInfo.Color.class)));
        ageComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Age.class)));
        cityComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Area.class)));
        dateRangeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
            "within 1 week", "within 2 weeks", "within 1 month", "within 3 months"
        }));
        
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
        
        // Add sort and reset buttons
        sortButton.addActionListener(e -> controller.handleSort(true));
        resetButton.addActionListener(e -> controller.handleReset());
        
        filterPanel.add(sortButton);
        filterPanel.add(resetButton);
    }

    private void updateBreedOptions() {
        String selectedType = (String) typeComboBox.getSelectedItem();
        breedComboBox.removeAllItems();
        if (selectedType != null) {
            updateBreedOptions(breedComboBox, selectedType);
        }
    }

    private void updateBreedOptions(JComboBox<String> breedComboBox, String type) {
        breedComboBox.removeAllItems();
        if (type != null) {
            switch (type) {
                case "DOG":
                    breedComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                        "LABRADOR", "GERMAN_SHEPHERD", "GOLDEN_RETRIEVER", "BULLDOG", "BEAGLE"
                    }));
                    break;
                case "CAT":
                    breedComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                        "SIAMESE", "PERSIAN", "MAINE_COON", "RAGDOLL", "BENGAL"
                    }));
                    break;
                case "BIRD":
                    breedComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                        "PARROT", "CANARY", "COCKATIEL", "BUDGIE", "FINCH"
                    }));
                    break;
                case "RABBIT":
                    breedComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                        "DUTCH", "LIONHEAD", "MINI_REX", "HOLLAND_LOP", "DWARF"
                    }));
                    break;
                case "OTHER":
                    breedComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                        "HAMSTER", "GUINEA_PIG", "FERRET", "TURTLE", "SNAKE"
                    }));
                    break;
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
        dialog.setLayout(new BorderLayout());
        
        // Create details panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2));
        detailsPanel.add(new JLabel("Type:"));
        detailsPanel.add(new JLabel(animal.getAnimalType()));
        detailsPanel.add(new JLabel("Breed:"));
        detailsPanel.add(new JLabel(animal.getSpecies()));
        detailsPanel.add(new JLabel("Size:"));
        detailsPanel.add(new JLabel(animal.getAnimalSize()));
        detailsPanel.add(new JLabel("Gender:"));
        detailsPanel.add(new JLabel(animal.getGender()));
        detailsPanel.add(new JLabel("Pattern:"));
        detailsPanel.add(new JLabel(animal.getPattern()));
        detailsPanel.add(new JLabel("Color:"));
        detailsPanel.add(new JLabel(animal.getColor()));
        detailsPanel.add(new JLabel("Age:"));
        detailsPanel.add(new JLabel(animal.getAge()));
        detailsPanel.add(new JLabel("Address:"));
        detailsPanel.add(new JLabel(animal.getAddress()));
        detailsPanel.add(new JLabel("City:"));
        detailsPanel.add(new JLabel(animal.getArea()));
        detailsPanel.add(new JLabel("Time:"));
        detailsPanel.add(new JLabel(animal.getTime()));
        detailsPanel.add(new JLabel("Date:"));
        detailsPanel.add(new JLabel(animal.getSeenDate()));
        detailsPanel.add(new JLabel("Description:"));
        detailsPanel.add(new JLabel(animal.getDescription()));
        detailsPanel.add(new JLabel("Location Description:"));
        detailsPanel.add(new JLabel(animal.getLocDesc()));
        
        // Add image
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(animal.getImage());
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            imageLabel.setText("Image not available");
        }
        
        // Add edit button
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> showEditDialog(animal));
        
        // Add components to dialog
        dialog.add(detailsPanel, BorderLayout.CENTER);
        dialog.add(imageLabel, BorderLayout.EAST);
        dialog.add(editButton, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showEditDialog(IAnimal animal) {
        JDialog dialog = new JDialog(this, "Edit Animal Information", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        
        // Create form fields
        JComboBox<String> typeField = new JComboBox<>(getEnumValues(AnimalType.class));
        JComboBox<String> breedField = new JComboBox<>();
        JComboBox<String> sizeField = new JComboBox<>(getEnumValues(Size.class));
        JComboBox<String> genderField = new JComboBox<>(getEnumValues(Gender.class));
        JComboBox<String> patternField = new JComboBox<>(getEnumValues(Pattern.class));
        JComboBox<String> colorField = new JComboBox<>(getEnumValues(Model.AnimalInfo.Color.class));
        JComboBox<String> ageField = new JComboBox<>(getEnumValues(Age.class));
        JTextField dateField = new JTextField(animal.getSeenDate());
        JTextField timeField = new JTextField(animal.getTime());
        JComboBox<String> cityField = new JComboBox<>(getEnumValues(Area.class));
        JTextField addressField = new JTextField(animal.getAddress());
        JTextField locDescField = new JTextField(animal.getLocDesc());
        JTextArea descriptionField = new JTextArea(animal.getDescription());
        JButton imageButton = new JButton("Upload Image");
        
        // Set current values
        typeField.setSelectedItem(animal.getAnimalType());
        breedField.setSelectedItem(animal.getSpecies());
        sizeField.setSelectedItem(animal.getAnimalSize());
        genderField.setSelectedItem(animal.getGender());
        patternField.setSelectedItem(animal.getPattern());
        colorField.setSelectedItem(animal.getColor());
        ageField.setSelectedItem(animal.getAge());
        cityField.setSelectedItem(animal.getArea());
        
        // Add type selection listener
        typeField.addActionListener(e -> updateBreedOptions(breedField, (String) typeField.getSelectedItem()));
        
        // Add form fields to panel
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Breed:"));
        formPanel.add(breedField);
        formPanel.add(new JLabel("Size:"));
        formPanel.add(sizeField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Pattern:"));
        formPanel.add(patternField);
        formPanel.add(new JLabel("Color:"));
        formPanel.add(colorField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Date (MM/DD/YYYY):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Time (HH:MM):"));
        formPanel.add(timeField);
        formPanel.add(new JLabel("City:"));
        formPanel.add(cityField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Location Description:"));
        formPanel.add(locDescField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Image:"));
        formPanel.add(imageButton);
        
        // Add submit button
        JButton submitButton = new JButton("Save");
        submitButton.addActionListener(e -> {
            // Update animal information
            animal.setAnimalType((String) typeField.getSelectedItem());
            animal.setSpecies((String) breedField.getSelectedItem());
            animal.setAnimalSize((String) sizeField.getSelectedItem());
            animal.setGender((String) genderField.getSelectedItem());
            animal.setPattern((String) patternField.getSelectedItem());
            animal.setColor((String) colorField.getSelectedItem());
            animal.setAge((String) ageField.getSelectedItem());
            animal.setSeenDate(dateField.getText());
            animal.setTime(timeField.getText());
            animal.setArea((String) cityField.getSelectedItem());
            animal.setAddress(addressField.getText());
            animal.setLocDesc(locDescField.getText());
            animal.setDescription(descriptionField.getText());
            
            // Update view
            updateView();
            dialog.dispose();
        });
        
        // Add components to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showReportDialog() {
        JDialog dialog = new JDialog(this, "Report Animal", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        
        // Create form fields
        JComboBox<String> typeField = new JComboBox<>(getEnumValues(AnimalType.class));
        JComboBox<String> breedField = new JComboBox<>();
        JComboBox<String> sizeField = new JComboBox<>(getEnumValues(Size.class));
        JComboBox<String> genderField = new JComboBox<>(getEnumValues(Gender.class));
        JComboBox<String> patternField = new JComboBox<>(getEnumValues(Pattern.class));
        JComboBox<String> colorField = new JComboBox<>(getEnumValues(Model.AnimalInfo.Color.class));
        JComboBox<String> ageField = new JComboBox<>(getEnumValues(Age.class));
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JComboBox<String> cityField = new JComboBox<>(getEnumValues(Area.class));
        JTextField addressField = new JTextField();
        JTextField locDescField = new JTextField();
        JTextArea descriptionField = new JTextArea();
        JButton imageButton = new JButton("Upload Image");
        String[] imagePath = new String[1];
        
        // Add type selection listener
        typeField.addActionListener(e -> updateBreedOptions(breedField, (String) typeField.getSelectedItem()));
        
        // Add image upload listener
        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath[0] = selectedFile.getAbsolutePath();
                imageButton.setText(selectedFile.getName());
            }
        });
        
        // Add form fields to panel
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeField);
        formPanel.add(new JLabel("Breed:"));
        formPanel.add(breedField);
        formPanel.add(new JLabel("Size:"));
        formPanel.add(sizeField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Pattern:"));
        formPanel.add(patternField);
        formPanel.add(new JLabel("Color:"));
        formPanel.add(colorField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Date (MM/DD/YYYY):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("Time (HH:MM):"));
        formPanel.add(timeField);
        formPanel.add(new JLabel("City:"));
        formPanel.add(cityField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Location Description:"));
        formPanel.add(locDescField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Image:"));
        formPanel.add(imageButton);
        
        // Add submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Create new animal object
            String type = (String) typeField.getSelectedItem();
            String breed = (String) breedField.getSelectedItem();
            String size = (String) sizeField.getSelectedItem();
            String gender = (String) genderField.getSelectedItem();
            String pattern = (String) patternField.getSelectedItem();
            String color = (String) colorField.getSelectedItem();
            String age = (String) ageField.getSelectedItem();
            String date = dateField.getText();
            String time = timeField.getText();
            String city = (String) cityField.getSelectedItem();
            String address = addressField.getText();
            String locDesc = locDescField.getText();
            String description = descriptionField.getText();
            String image = imagePath[0];
            
            // Create animal object
            IAnimal animal = controller.createAnimal(
                type, breed, size, gender, pattern, color, age,
                date, time, city, address, locDesc, description, image
            );
            
            // Add animal to list
            controller.addAnimal(animal);
            
            // Update view
            updateView();
            dialog.dispose();
        });
        
        // Add components to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(submitButton, BorderLayout.SOUTH);
        
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
        // Clear all markers from the map
        mapViewer.removeAll();
        
        // Create marker set
        Set<Waypoint> waypoints = new HashSet<>();
        
        // Add marker for each animal
        for (IAnimal animal : animals) {
            try {
                // Get animal location information
                String address = animal.getAddress();
                String area = animal.getArea();
                
                // Use geocoding service to get coordinates
                // Note: This needs to be implemented with a geocoding service to convert address to coordinates
                // Here we use a simple example coordinate
                double lat = 47.6062; // Seattle's latitude
                double lon = -122.3321; // Seattle's longitude
                
                // Create marker
                GeoPosition pos = new GeoPosition(lat, lon);
                DefaultWaypoint waypoint = new DefaultWaypoint(pos);
                waypoints.add(waypoint);
                
                // Create marker renderer
                WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
                waypointPainter.setWaypoints(waypoints);
                
                // Add marker to map
                mapViewer.setOverlayPainter(waypointPainter);
                
                // Set map center and zoom level
                mapViewer.setAddressLocation(pos);
                mapViewer.setZoom(7);
                
            } catch (Exception e) {
                showError("Unable to display animal location on map: " + e.getMessage());
            }
        }
        
        // Update map display
        mapViewer.repaint();
    }

    @Override
    public void showFilterOptions() {
        filterPanel.setVisible(true);
    }

    @Override
    public void showSortOptions() {
        sortButton.setVisible(true);
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
    public boolean getSortOrder() {
        return true; // Default ascending order
    }

    @Override
    public void updateView() {
        revalidate();
        repaint();
    }

    private static class AnimalListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                    boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof IAnimal) {
                IAnimal animal = (IAnimal) value;
                setText(String.format("%s - %s - %s", 
                    animal.getAnimalType(),
                    animal.getSpecies(),
                    animal.getArea()));
            }
            return this;
        }
    }

    private void updateFilterValues() {
        String selectedFilter = getSelectedFilter();
        if (selectedFilter == null) {
            return;
        }

        // Update filter value options based on selected filter type
        switch (selectedFilter) {
            case "type":
                typeComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(AnimalType.class)));
                break;
            case "species":
                updateBreedOptions();
                break;
            case "size":
                sizeComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Size.class)));
                break;
            case "gender":
                genderComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Gender.class)));
                break;
            case "pattern":
                patternComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Pattern.class)));
                break;
            case "color":
                colorComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Model.AnimalInfo.Color.class)));
                break;
            case "age":
                ageComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Age.class)));
                break;
            case "area":
                cityComboBox.setModel(new DefaultComboBoxModel<>(getEnumValues(Area.class)));
                break;
            case "date":
                dateRangeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                    "within 1 week", "within 2 weeks", "within 1 month", "within 3 months"
                }));
                break;
            default:
                // If filter type unknown, clear all filter value options
                typeComboBox.removeAllItems();
                breedComboBox.removeAllItems();
                sizeComboBox.removeAllItems();
                genderComboBox.removeAllItems();
                patternComboBox.removeAllItems();
                colorComboBox.removeAllItems();
                ageComboBox.removeAllItems();
                cityComboBox.removeAllItems();
                dateRangeComboBox.removeAllItems();
                break;
        }
    }
} 