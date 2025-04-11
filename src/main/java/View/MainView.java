package View;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame implements IView {
    private JMapViewer mapViewer;
    private JPanel controlPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton filterButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;

    public MainView() {
        initializeUI();
    }

    private void initializeUI() {
        // 设置主窗口
        setTitle("Lost Animal Report System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 创建地图视图
        mapViewer = new JMapViewer();
        mapViewer.setZoom(10); // 设置初始缩放级别
        
        // 创建控制面板
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        
        // 创建搜索框
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        filterButton = new JButton("Filter");
        zoomInButton = new JButton("Zoom In");
        zoomOutButton = new JButton("Zoom Out");
        
        // 添加按钮监听器
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.setZoom(mapViewer.getZoom() + 1);
            }
        });
        
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.setZoom(mapViewer.getZoom() - 1);
            }
        });
        
        // 添加控件到控制面板
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(filterButton);
        controlPanel.add(zoomInButton);
        controlPanel.add(zoomOutButton);
        
        // 设置布局
        setLayout(new BorderLayout());
        add(mapViewer, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void displayAnimalsOnMap(List<PostedAnimal> animals) {
        mapViewer.removeAllMapMarkers();
        for (PostedAnimal animal : animals) {
            double[] coords = animal.getCoordinates();
            MapMarker marker = new MapMarkerDot(animal.getDescription(), 
                new Coordinate(coords[0], coords[1]));
            mapViewer.addMapMarker(marker);
        }
        
        // 如果有点标记，则居中显示
        if (!animals.isEmpty()) {
            double[] firstCoords = animals.get(0).getCoordinates();
            mapViewer.setDisplayPosition(new Coordinate(firstCoords[0], firstCoords[1]), mapViewer.getZoom());
        }
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getFilterButton() {
        return filterButton;
    }

    public JMapViewer getMapViewer() {
        return mapViewer;
    }
} 