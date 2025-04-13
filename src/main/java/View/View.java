package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

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
    //private final JButton addSelectedButton;
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

        //addSelectedButton.addActionListener(e -> {
        //    if (!selectedAnimals.isEmpty()) {
        //        for (IAnimal animal : selectedAnimals) {
        //            if (!selectedListModel.contains(animal)) {
        //                selectedListModel.addElement(animal);
        //            }
        //        }
        //        JOptionPane.showMessageDialog(this, "Added " + selectedAnimals.size() + " animals to the list");
        //        selectedAnimals.clear();
        //        animalList.clearSelection();
        //    } else {
        //        JOptionPane.showMessageDialog(this, "Please select animals first");
        //    }
       // });

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
        
        JButton exportButton = new JButton("Export");
        exportButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        exportButton.addActionListener(e -> {
            String format = (String) exportFormatComboBox.getSelectedItem();
            if (format != null) {
                controller.exportData(format.toLowerCase());
            }
        });
        
        exportPanel.add(exportLabel);
        exportPanel.add(exportFormatComboBox);
        exportPanel.add(exportButton);
        filterPanel.add(exportPanel);
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
        dialog.setLayout(new BorderLayout());

        // 创建图片面板
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(300, 300));
        imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));

        // 加载图片
        String imagePath = animal.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // 如果是相对路径，转换为绝对路径
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

        // 创建信息面板
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 0, 0)); // 减小水平和垂直间距
        infoPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 减小边距

        // 添加动物信息
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
        addInfoField(infoPanel, "Image Path:", imagePath); // 添加图片路径信息

        // 创建滚动面板来容纳信息面板
        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Animal Information"));

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        // 将组件添加到对话框
        dialog.add(imagePanel, BorderLayout.WEST);
        dialog.add(scrollPane, BorderLayout.CENTER);
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

        // Load filtered animals through controller
        controller.loadFilteredAnimals(type);
    }

    private class AnimalListCellRenderer extends DefaultListCellRenderer {
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
                
                // 将按钮添加到按钮面板
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

    private JPanel createAnimalPanel(IAnimal animal) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);

        // 创建左侧图片面板
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(150, 150));
        imagePanel.setBackground(Color.WHITE);
        
        // 加载图片
        String imagePath = animal.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // 如果是相对路径，转换为绝对路径
                if (!imagePath.startsWith("/")) {
                    imagePath = "data/image/" + imagePath;
                }
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imagePanel.add(imageLabel, BorderLayout.CENTER);
            } catch (Exception e) {
                JLabel noImageLabel = new JLabel("No Image");
                noImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imagePanel.add(noImageLabel, BorderLayout.CENTER);
            }
        } else {
            JLabel noImageLabel = new JLabel("No Image");
            noImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imagePanel.add(noImageLabel, BorderLayout.CENTER);
        }

        // 创建右侧信息面板，减小水平和垂直间距
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 2, 2)); // 将间距从 5,5 改为 2,2
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 减小边距

        // 只添加指定的字段
        addInfoField(infoPanel, "Type:", animal.getAnimalType());
        addInfoField(infoPanel, "Breed:", animal.getSpecies());
        addInfoField(infoPanel, "Date:", animal.getSeenDate());
        addInfoField(infoPanel, "Time:", animal.getTime());
        addInfoField(infoPanel, "Area:", animal.getArea());
        addInfoField(infoPanel, "Address:", animal.getAddress());

        // 创建查看详情按钮
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> showAnimalDetails(animal));

        // 将组件添加到面板
        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(viewDetailsButton, BorderLayout.SOUTH);

        return panel;
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
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(seattle);

        // Add mouse controls
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        mapPanel.add(mapViewer, BorderLayout.CENTER);
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
}