package View;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

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
        sortButton = new JButton("Sort");
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

        // Add tab change listener
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) { // Map tab is selected
                System.out.println("Switching to map tab");
                controller.handleMapDisplay();
            }
        });

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
                    String text = "Type: " + animal.getAnimalType() + " | " +
                            "Breed: " + animal.getSpecies() + " | " +
                            "Date: " + animal.getSeenDate() + " | " +
                            "Time: " + animal.getTime() + " | " +
                            "Area: " + animal.getArea() + " | " +
                            "Address: " + animal.getAddress();
                    label.setText(text);
                    
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
        //selectedAnimalList.addMouseListener(new MouseAdapter() {
        //    @Override
        //    public void mouseClicked(MouseEvent e) {
        //        if (e.getClickCount() == 1) {
        //            int index = selectedAnimalList.locationToIndex(e.getPoint());
        //            if (index >= 0) {
        //                Rectangle cellBounds = selectedAnimalList.getCellBounds(index, index);
        //                if (cellBounds != null) {
        //                    // 获取点击位置相对于列表项的坐标
        //                    int relativeX = e.getX() - cellBounds.x;
                            
        //                    if (relativeX < cellBounds.width && relativeX > cellBounds.width*0.95) {
        //                        // 如果点击位置在左侧区域（小于90%的宽度），则显示详情
        //                        IAnimal animal = selectedListModel.getElementAt(index);
        //                        showAnimalDetails(animal);
        //                    }
        //                }
        //            }
        //        }
        //    }
        //});
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
                            if (relativeX > cellBounds.width * 0.9) {
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
                            // 如果点击位置在左侧区域（小于70%的宽度），则显示详情
                            if (relativeX > cellBounds.width * 0.8 && relativeX < cellBounds.width * 0.9) {
                                showAnimalDetails(listModel.getElementAt(index));
                            }
                        }
                    }
                }
            }
        });

        listPanel.add(new JScrollPane(animalList), BorderLayout.CENTER);

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

        // Add buttons with left alignment and minimal spacing
        filterButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        resetButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        filterPanel.add(filterButton);
        filterPanel.add(resetButton);
        
        // Add sort components to filter panel after reset button
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));
        sortPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除边距
        
        JLabel sortLabel = new JLabel("Sort by Date:");
        sortLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sortComboBox.setMaximumSize(new Dimension(200, sortComboBox.getPreferredSize().height));
        sortButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        sortPanel.add(sortLabel);
        sortPanel.add(sortComboBox);
        sortPanel.add(sortButton);
        filterPanel.add(sortPanel);

        // Add export functionality
        JPanel exportPanel = new JPanel();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        exportPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除边距
        
        JLabel exportLabel = new JLabel("Export as:");
        exportLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JComboBox<String> exportFormatComboBox = new JComboBox<>(new String[]{"", "XML", "JSON", "TXT", "CSV"});
        exportFormatComboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        exportFormatComboBox.setMaximumSize(new Dimension(200, exportFormatComboBox.getPreferredSize().height));
        
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
                    controller.exportData(format);
                } else {
                    controller.exportData(animalsToExport, format.toLowerCase());
                }
            }
        });
        
        exportPanel.add(exportLabel);
        exportPanel.add(exportFormatComboBox);
        exportPanel.add(exportButton);
        filterPanel.add(exportPanel);
        
        // Set minimal spacing between components
        for (Component comp : filterPanel.getComponents()) {
            if (comp instanceof JPanel) {
                ((JPanel) comp).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 移除所有面板的边距
            }
        }
        
        // 设置所有按钮的边距为0
        filterButton.setMargin(new Insets(0, 0, 0, 0));
        resetButton.setMargin(new Insets(0, 0, 0, 0));
        sortButton.setMargin(new Insets(0, 0, 0, 0));
        exportButton.setMargin(new Insets(0, 0, 0, 0));
        
        // 设置所有标签的边距为0
        sortLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        exportLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // 设置所有下拉框的边距为0
        sortComboBox.setMaximumSize(new Dimension(200, sortComboBox.getPreferredSize().height));
        exportFormatComboBox.setMaximumSize(new Dimension(200, exportFormatComboBox.getPreferredSize().height));
        
        // 设置所有面板的布局间距为0
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        
        // 设置所有组件的首选大小为最小
        filterButton.setPreferredSize(new Dimension(100, 20));
        resetButton.setPreferredSize(new Dimension(100, 20));
        sortButton.setPreferredSize(new Dimension(100, 20));
        exportButton.setPreferredSize(new Dimension(100, 20));
        
        // 设置所有标签的首选大小为最小
        sortLabel.setPreferredSize(new Dimension(100, 15));
        exportLabel.setPreferredSize(new Dimension(100, 15));
        
        // 设置所有下拉框的首选大小为最小
        sortComboBox.setPreferredSize(new Dimension(200, 20));
        exportFormatComboBox.setPreferredSize(new Dimension(200, 20));
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
            
            // 保持下拉框的大小不变
            Dimension originalSize = breedComboBox.getPreferredSize();
            
            if (breeds != null) {
                for (String breed : breeds) {
                    breedComboBox.addItem(breed);
                }
            }
            
            // 恢复下拉框的大小
            breedComboBox.setPreferredSize(originalSize);
            breedComboBox.setMaximumSize(new Dimension(200, originalSize.height));
            
            // 强制重新验证和重绘
            breedComboBox.revalidate();
            breedComboBox.repaint();
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
                // 直接使用原始图片路径，不进行修改
                ImageIcon originalIcon = new ImageIcon(imagePath);
                
                // 计算保持宽高比的缩放尺寸
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                int maxSize = 300;
                
                int scaledWidth, scaledHeight;
                if (originalWidth > originalHeight) {
                    scaledWidth = maxSize;
                    scaledHeight = (int) ((double) originalHeight / originalWidth * maxSize);
                } else {
                    scaledHeight = maxSize;
                    scaledWidth = (int) ((double) originalWidth / originalHeight * maxSize);
                }
                
                // 使用保持宽高比的缩放
                Image scaledImage = originalIcon.getImage().getScaledInstance(
                    scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                
                // 创建图片标签并居中显示
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

        // 创建信息面板
        //JPanel infoPanel = new JPanel(new GridLayout(0, 1, 0, 0)); // 移除水平和垂直间距

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5)); // 移除边距

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
        //addInfoField(infoPanel, "Image Path:", imagePath); // 添加图片路径信息

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

    private void addInfoField(JPanel panel, String label, String value) {
        // 创建一个面板来容纳标签和内容
        JPanel fieldPanel = new JPanel(new BorderLayout(0, 0));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        fieldPanel.setOpaque(false); // 设置面板透明
        
        // 创建标签
        JLabel labelComponent = new JLabel(label);
        labelComponent.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // 创建文本框
        JTextField textField;
        if (label.equals("Description:")) {
            // 为 description 字段创建多行文本框
            JTextArea textArea = new JTextArea(value);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setRows(5); // 增加行数为5
            textArea.setColumns(20); // 减少列数
            textArea.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
            textArea.setOpaque(true); // 设置文本区域不透明
            textArea.setBackground(Color.WHITE); // 设置白色背景
            
            // 将文本区域添加到滚动面板
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            scrollPane.setPreferredSize(new Dimension(250, 100)); // 增加高度为100
            scrollPane.setOpaque(false); // 设置滚动面板透明
            scrollPane.getViewport().setOpaque(false); // 设置视口透明
            
            // 将标签和滚动面板添加到字段面板
            fieldPanel.add(labelComponent, BorderLayout.WEST);
            fieldPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            // 为其他字段创建单行文本框
            textField = new JTextField(value);
            textField.setEditable(false);
            textField.setColumns(20); // 减少列数
            textField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            textField.setOpaque(false); // 设置文本框透明
            textField.setBackground(new Color(0, 0, 0, 0)); // 设置透明背景
            
            // 将标签和文本框添加到字段面板
            fieldPanel.add(labelComponent, BorderLayout.WEST);
            fieldPanel.add(textField, BorderLayout.CENTER);
        }
        
        // 将整个面板添加到主面板
        panel.add(fieldPanel);
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
        dialog.add(new JLabel("Time Seen (HH:MM):"));
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
                    addressField.getText(),
                    (String) cityComboBox.getSelectedItem(),
                    timeField.getText(),
                    dateField.getText(),
                    descriptionArea.getText(),
                    locDescArea.getText(),
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
        String city = (String) cityComboBox.getSelectedItem();
        String dateRange = (String) dateRangeComboBox.getSelectedItem();

        // Unfilter.
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
        if (city != null && !city.isEmpty()) {
            controller.handleFilter("AREA", city.toUpperCase());
        }
        if (dateRange != null && !dateRange.isEmpty()) {
            controller.handleFilter("SEENDATE", dateRange);
        }
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
                String text = "Type: " + animal.getAnimalType() + " | " +
                        "Breed: " + animal.getSpecies() + " | " +
                        "Date: " + animal.getSeenDate() + " | " +
                        "Time: " + animal.getTime() + " | " +
                        "City: " + animal.getArea() + " | " +
                        "Address: " + animal.getAddress();
                label.setText(text);
                
                // 创建按钮面板，使用最小间距
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));  // 设置按钮间距为0

                // 创建查看详情按钮
                JButton viewDetailsButton = new JButton("View Details");
                viewDetailsButton.setMargin(new Insets(0, 2, 0, 2));  // 减小按钮内边距

                // 将组件添加到面板
                buttonPanel.add(viewDetailsButton);
                // 将按钮添加到按钮面板

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


    private void initializeMapPanel() {
        System.out.println("Initializing map panel");
        
        // Set up map
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Set initial map position to Seattle, Washington
        GeoPosition seattle = new GeoPosition(47.6062, -122.3321);
        System.out.println("Setting initial map position to: " + seattle.getLatitude() + ", " + seattle.getLongitude());
        
        // Set zoom level and center
        mapViewer.setZoom(8); // Zoomed out to show more of Washington state
        mapViewer.setCenterPosition(seattle);
        
        // Add mouse controls
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // Set map viewer size
        mapViewer.setPreferredSize(new Dimension(800, 600));
        
        // Add map viewer to panel
        mapPanel.add(mapViewer, BorderLayout.CENTER);
        
        // Force layout update
        mapPanel.revalidate();
        mapPanel.repaint();
        
        System.out.println("Map panel initialization complete");
    }

    @Override
    public void displayMap(List<IAnimal> animals) {
        System.out.println("View: Displaying map with " + animals.size() + " animals");
        
        // Clear existing waypoints
        Set<Waypoint> waypoints = new HashSet<>();
        
        // Create waypoints for each animal
        for (IAnimal animal : animals) {
            try {
                String fullAddress = animal.getAddress() + ", " + animal.getArea();
                System.out.println("View: Processing animal at address: " + fullAddress);
                
                GeoPosition position = getGeoPosition(fullAddress);
                if (position != null) {
                    System.out.println("View: Successfully got coordinates: " + position.getLatitude() + ", " + position.getLongitude());
                    Waypoint waypoint = new DefaultWaypoint(position);
                    waypoints.add(waypoint);
                } else {
                    System.out.println("View: Failed to get coordinates for address: " + fullAddress);
                }
            } catch (Exception e) {
                System.out.println("View: Error processing animal: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("View: Created " + waypoints.size() + " waypoints");
        
        // Create a waypoint painter with a custom renderer
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>() {
            @Override
            protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
                System.out.println("View: Painting waypoints");
                System.out.println("View: Map size: " + width + "x" + height);
                System.out.println("View: Current zoom level: " + map.getZoom());
                
                // Get the map's center position
                GeoPosition center = map.getCenterPosition();
                System.out.println("View: Map center: " + center.getLatitude() + ", " + center.getLongitude());
                
                for (Waypoint waypoint : getWaypoints()) {
                    // Convert geo coordinates to screen coordinates
                    Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
                    
                    // Get the center point in screen coordinates
                    Point2D centerPoint = map.getTileFactory().geoToPixel(center, map.getZoom());
                    
                    // Calculate the offset from the center
                    double x = point.getX() - centerPoint.getX() + (double) width / 2;
                    double y = point.getY() - centerPoint.getY() + (double) height / 2;
                    
                    // Debug coordinates
                    System.out.println("View: Drawing waypoint at screen coordinates: " + x + ", " + y);
                    System.out.println("View: Waypoint position: " + waypoint.getPosition().getLatitude() + ", " + waypoint.getPosition().getLongitude());
                    
                    // Only draw if the point is within the visible area
                    if (x >= 0 && x <= width && y >= 0 && y <= height) {
                        // Draw a large red cross
                        g.setColor(Color.RED);
                        g.setStroke(new BasicStroke(3));
                        g.drawLine((int)x - 10, (int)y - 10, (int)x + 10, (int)y + 10);
                        g.drawLine((int)x - 10, (int)y + 10, (int)x + 10, (int)y - 10);
                        
                        // Draw a circle around the cross
                        g.setColor(Color.BLACK);
                        g.setStroke(new BasicStroke(2));
                        g.drawOval((int)x - 15, (int)y - 15, 30, 30);
                    } else {
                        System.out.println("View: Waypoint outside visible area");
                    }
                }
            }
        };
        waypointPainter.setWaypoints(waypoints);
        
        // Set the painter to the map
        mapViewer.setOverlayPainter(waypointPainter);
        
        // Repaint the map
        mapViewer.repaint();
        System.out.println("View: Map repainted");
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

}