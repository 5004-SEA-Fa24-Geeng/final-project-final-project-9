package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.io.FileOutputStream;
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
import Model.AnimalInfo.Species.Birds;
import Model.AnimalInfo.Species.Cats;
import Model.AnimalInfo.Species.Dogs;
import Model.AnimalInfo.Species.Ducks;
import Model.AnimalInfo.Species.Geese;
import Model.AnimalInfo.Species.Hamsters;
import Model.AnimalInfo.Species.Hedgehogs;
import Model.AnimalInfo.Species.Rabbits;
import Model.Animals.IAnimal;

public class View extends JFrame implements IView {
    private final IController controller;
    private final JTabbedPane tabbedPane;
    private final JPanel listPanel;
    private final JPanel mapPanel;
    private final JList<IAnimal> animalList;
    private final DefaultListModel<IAnimal> listModel;
    private final DefaultListModel<IAnimal> selectedListModel;
    private final JList<IAnimal> selectedAnimalList;
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
    private final Set<IAnimal> selectedAnimals;
    private Map<Point2D, IAnimal> pointToAnimalMap = new HashMap<>();
    private Map<GeoPosition, IAnimal> positionToAnimalMap = new HashMap<>();
    private final Map<String, GeoPosition> geocodingCache = new HashMap<>();

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
        sortButton = new JButton("Sort by Date");
        //addSelectedButton = new JButton("Add Selected to List");
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
                controller.handleSort(ascending);
            }
        });

        // Initialize view
        controller.initialize();

        // 在构造函数中初始化 selectedAnimalList
        selectedAnimalList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
                JPanel panel = new JPanel(new BorderLayout(0, 0));  // 设置布局管理器的间距为0
                panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除外边距
                
                JPanel infoPanel = new JPanel(new BorderLayout(0, 0));  // 设置布局管理器的间距为0
                infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除内边距
                JLabel label = new JLabel();

                if (value instanceof IAnimal animal) {
                    StringBuilder text = new StringBuilder();
                    text.append("Type: ").append(animal.getAnimalType()).append(" | ");
                    text.append("Breed: ").append(animal.getSpecies()).append(" | ");
                    text.append("Date: ").append(animal.getSeenDate()).append(" | ");
                    text.append("Time: ").append(animal.getTime()).append(" | ");
                    text.append("Area: ").append(animal.getArea()).append(" | ");
                    text.append("Address: ").append(animal.getAddress());
                    label.setText(text.toString());
                    
                    // 创建按钮面板，使用最小间距
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  // 设置按钮间距为0
                    
                    // 创建删除按钮
                    JButton deleteButton = new JButton("Delete");
                    deleteButton.setMargin(new Insets(0, 2, 0, 2));  // 减小按钮内边距
                    deleteButton.addActionListener(e -> {
                        selectedListModel.removeElement(animal);  // 只从选中列表中删除
                    });
                    
                    // 将按钮添加到按钮面板
                    buttonPanel.add(deleteButton);
                    
                    // 将标签和按钮面板添加到信息面板
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
        
        // 设置选择模式和其他属性
        selectedAnimalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedAnimalList.setVisibleRowCount(3);  // 减少显示行数从6行到3行
        
        // 添加鼠标点击事件监听器
        selectedAnimalList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = selectedAnimalList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Rectangle cellBounds = selectedAnimalList.getCellBounds(index, index);
                        if (cellBounds != null) {
                            // 获取点击位置相对于列表项的坐标
                            int relativeX = e.getX() - cellBounds.x;
                            
                            if (relativeX < cellBounds.width * 0.95) {
                                // 如果点击位置在左侧区域（小于90%的宽度），则显示详情
                                IAnimal animal = selectedListModel.getElementAt(index);
                                showAnimalDetails(animal);
                            } else if (relativeX > cellBounds.width * 0.95) {
                                // 如果点击位置在右侧区域（大于90%的宽度），则删除该项
                                selectedListModel.remove(index);
                            }
                        }
                    }
                }
            }
        });
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
                        Rectangle cellBounds = animalList.getCellBounds(index, index);
                        if (cellBounds != null) {
                            // 获取点击位置相对于列表项的坐标
                            int relativeX = e.getX() - cellBounds.x;
                            
                            // 如果点击位置在左侧区域（小于70%的宽度），则显示详情
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
                            // 获取点击位置相对于列表项的坐标
                            int relativeX = e.getX() - cellBounds.x;

                            // 如果点击位置在右侧区域（大于90%的宽度），则添加到列表
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
                            // 如果点击位置在左侧区域（80% ~ 90%的宽度），则显示详情
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
        
        // 设置 selectedAnimalList 的显示行数和首选大小
        selectedAnimalList.setVisibleRowCount(3);  // 减少显示行数从6行到3行
        JScrollPane selectedScrollPane = new JScrollPane(selectedAnimalList);
        selectedScrollPane.setPreferredSize(new Dimension(0, 100));  // 减小高度从300像素到100像素
        
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

        // 创建标签和下拉框的容器
        JPanel[] filterRows = new JPanel[9];  // 9个筛选条件
        for (int i = 0; i < filterRows.length; i++) {
            filterRows[i] = new JPanel();
            filterRows[i].setLayout(new BoxLayout(filterRows[i], BoxLayout.Y_AXIS));
            filterRows[i].setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // 创建左对齐的标签
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

        // 设置标签左对齐
        for (JLabel label : labels) {
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        // 设置下拉框左对齐并设置最大宽度
        JComboBox<?>[] comboBoxes = {
            typeComboBox, breedComboBox, sizeComboBox, genderComboBox,
            patternComboBox, colorComboBox, ageComboBox, cityComboBox, dateRangeComboBox
        };
        for (JComboBox<?> comboBox : comboBoxes) {
            comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            comboBox.setMaximumSize(new Dimension(200, comboBox.getPreferredSize().height));
        }

        // 将标签和下拉框添加到各自的容器中
        for (int i = 0; i < filterRows.length; i++) {
            filterRows[i].add(labels[i]);
            filterRows[i].add(comboBoxes[i]);
            filterPanel.add(filterRows[i]);
        }

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

        // 添加查看地图按钮
        JButton viewMapButton = new JButton("View on Map");
        viewMapButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        viewMapButton.addActionListener(e -> {
            // 切换到地图标签页
            tabbedPane.setSelectedIndex(1);
            
            // 更新地图显示当前筛选的动物
            List<IAnimal> filteredAnimals = controller.getFilteredAnimals();
            System.out.println("Current number of animals: " + filteredAnimals.size());
            displayMap(filteredAnimals);
        });
        filterPanel.add(viewMapButton);

        // Add buttons with left alignment
        filterButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);

        // Add export functionality
        JPanel exportPanel = new JPanel();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel exportLabel = new JLabel("Export as:");
        exportLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JComboBox<String> exportFormatComboBox = new JComboBox<>(new String[]{"XML", "JSON", "TXT", "CSV"});
        exportFormatComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        exportFormatComboBox.setMaximumSize(new Dimension(200, exportFormatComboBox.getPreferredSize().height));

        JButton exportButton = getJButton(exportFormatComboBox);

        exportPanel.add(exportLabel);
        exportPanel.add(exportFormatComboBox);
        exportPanel.add(exportButton);
        filterPanel.add(exportPanel);
    }

    private JButton getJButton(JComboBox<String> exportFormatComboBox) {
        JButton exportButton = new JButton("Export");
        exportButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        exportButton.addActionListener(e -> {
            String format = (String) exportFormatComboBox.getSelectedItem();
            if (format != null && !format.isEmpty()) {
                // Get animals from selectedAnimalList instead of selectedAnimals
                List<IAnimal> animalsToExport = new ArrayList<>();
                for (int i = 0; i < selectedListModel.size(); i++) {
                    animalsToExport.add(selectedListModel.getElementAt(i));
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

    private <T extends Enum<T>> String[] getEnumValues(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i].name();
        }
        return result;
    }



    public void showAnimalDetails(IAnimal animal) {
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
                // Switch relative path to asolute path
                if (!imagePath.startsWith("/")) {
                    imagePath = "data/image/" + imagePath;
                }
                ImageIcon originalIcon = new ImageIcon(animal.getImage());
                Image scaledImage = originalIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
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
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 0, 0)); // 减小水平和垂直间距
        infoPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 减小边距

        // Add animal information
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
        
        // Add missing animal information
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description - Missing Information"));
        
        JTextArea descArea = new JTextArea(animal.getDescription());
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBackground(new Color(255, 250, 205)); // light yellow
        descArea.setFont(new Font("Dialog", Font.BOLD, 12));
        descArea.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(0, 100));
        descriptionPanel.add(descScroll, BorderLayout.CENTER);
        
        // 创建滚动面板来容纳信息面板
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Animal Information"));

        // 创建主信息面板，包含普通信息和走失信息
        JPanel mainInfoPanel = new JPanel(new BorderLayout());
        mainInfoPanel.add(scrollPane, BorderLayout.CENTER);
        mainInfoPanel.add(descriptionPanel, BorderLayout.SOUTH);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // 将组件添加到对话框
        dialog.add(imagePanel, BorderLayout.WEST);
        dialog.add(mainInfoPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // 设置对话框大小和位置
        dialog.setSize(800, 600);
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
                    String extension = selectedImageFile[0].getName().substring(selectedImageFile[0].getName().lastIndexOf("."));
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

        // unfilter
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

    private static class AnimalListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(0, 0));  // 设置布局管理器的间距为0
            panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除外边距
            
            JPanel infoPanel = new JPanel(new BorderLayout(0, 0));  // 设置布局管理器的间距为0
            infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除内边距
            JLabel label = new JLabel();

            if (value instanceof IAnimal animal) {
                StringBuilder text = new StringBuilder();
                text.append("Type: ").append(animal.getAnimalType()).append(" | ");
                text.append("Breed: ").append(animal.getSpecies()).append(" | ");
                text.append("Date: ").append(animal.getSeenDate()).append(" | ");
                text.append("Time: ").append(animal.getTime()).append(" | ");
                text.append("Area: ").append(animal.getArea()).append(" | ");
                text.append("Address: ").append(animal.getAddress());
                label.setText(text.toString());
                
                // 创建按钮面板，使用最小间距
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  // 设置按钮间距为0
                
                // 创建 Add to List 按钮
                JButton addToListButton = new JButton("Add to List");
                addToListButton.setMargin(new Insets(0, 2, 0, 2));  // 减小按钮内边距

                // 创建查看详情按钮
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.setMargin(new Insets(0, 2, 0, 2));  // 减小按钮内边距

                // 将按钮添加到按钮面板
                buttonPanel.add(viewDetailsButton);
                buttonPanel.add(addToListButton);

                // 将标签和按钮面板添加到信息面板
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


    private void addInfoField(JPanel panel, String labelText, String value) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 0)); // 减小水平间距为1，垂直间距为0
        JLabel label = new JLabel(labelText);
        JTextField valueField = new JTextField(value);
        valueField.setEditable(false);
        valueField.setColumns(12); // 减小文本框的列数，使整体更紧凑
        fieldPanel.add(label);
        fieldPanel.add(valueField);
        panel.add(fieldPanel);
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
        JLabel mapInfoLabel = new JLabel("Map shows all animals matching your filters. Click on markers to see animal details.");
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
                    System.out.println("Got coordinates from address: " + position.getLatitude() + ", " + position.getLongitude());
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
                            (int)(point2D.getX() - viewportBounds.getX()),
                            (int)(point2D.getY() - viewportBounds.getY())
                        );
                        
                        // Calculate distance between click point and marker point (both are now in screen coordinate system)
                        double distance = clickPoint.distance(mapPoint);
                        
                        // Output detailed debug information
                        System.out.println("Animal: " + waypoint.getAnimal().getAnimalType() + 
                                         ", Screen coordinates: (" + mapPoint.x + ", " + mapPoint.y + ")" +
                                         ", Distance to click point: " + distance + " pixels");
                        
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
                System.out.println("Successfully geocoded address to: " +
                        geoLocation.getLatitude() + ", " + geoLocation.getLongitude());
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
     * Waypoint with animal information
     */
    public static class WaypointWithInfo extends DefaultWaypoint {
        private final IAnimal animal;

        public WaypointWithInfo(GeoPosition position, IAnimal animal) {
            super(position);
            this.animal = animal;
        }

        public IAnimal getAnimal() {
            return animal;
        }

        @Override
        public String toString() {
            return animal.getAnimalType() + " - " + animal.getSpecies() + " at " + animal.getAddress();
        }
    }


    /**
     * Animal waypoint renderer that colors markers based on animal type
     */
     private static class AnimalWaypointRenderer implements WaypointRenderer<WaypointWithInfo> {
        // Store colors for different animal types
        private final Map<String, Color> typeColors = new HashMap<>();

        public AnimalWaypointRenderer() {
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

                // Note: No need to convert to viewport coordinates, as Graphics2D is already in the correct coordinate system
                int x = (int) point.getX();
                int y = (int) point.getY();

                // Get the animal directly from the waypoint
                IAnimal animal = waypoint.getAnimal();
                String animalType = animal.getAnimalType();

                // Normalize to uppercase to solve case sensitivity issues
                String normalizedType = animalType.toUpperCase();

                // Simplified debug output to reduce console clutter
                System.out.println("Painting animal: " + animalType + ", type: " + normalizedType +
                        ", position: (" + x + ", " + y + ")");

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