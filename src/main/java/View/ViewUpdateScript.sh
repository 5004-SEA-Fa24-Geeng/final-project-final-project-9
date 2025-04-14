#!/bin/bash

# 修改View.java文件

# 备份原文件
cp View.java View.java.bak.$(date +%Y%m%d%H%M%S)

# 1. 添加导入语句
sed -i '' '13i\\
import java.awt.geom.Point2D;' View.java
sed -i '' '17i\\
import java.util.HashMap;\\
import java.util.Map;\\
import java.util.function.Consumer;' View.java
sed -i '' '51i\\
import View.Map.AnimalMapHelper;' View.java

# 2. 添加实例变量
sed -i '' '/private final Set<IAnimal> selectedAnimals;/a\\
\\    private AnimalMapHelper mapHelper; // 添加地图帮助类实例变量' View.java

# 3. 修改showAnimalDetails方法的访问修饰符
sed -i '' 's/private void showAnimalDetails/public void showAnimalDetails/' View.java

# 4. 修改initializeMapPanel方法，添加mapHelper初始化
sed -i '' '/mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));/a\\
\\        // 初始化地图帮助类\\
        try {\\
            mapHelper = new AnimalMapHelper(mapViewer, animal -> showAnimalDetails(animal));\\
        } catch (Exception e) {\\
            System.err.println("Error initializing map helper: " + e.getMessage());\\
            e.printStackTrace();\\
        }' View.java

# 5. 替换displayMap方法的内容
sed -i '' '/public void displayMap(List<IAnimal> animals) {/,/    }/ c\\
    @Override\\
    public void displayMap(List<IAnimal> animals) {\\
        // 检查mapHelper是否已初始化\\
        if (mapHelper == null) {\\
            try {\\
                mapHelper = new AnimalMapHelper(mapViewer, animal -> showAnimalDetails(animal));\\
            } catch (Exception e) {\\
                System.err.println("Error initializing map helper: " + e.getMessage());\\
                e.printStackTrace();\\
            }\\
        }\\
        \\
        // 使用地图帮助类显示动物\\
        if (mapHelper != null) {\\
            mapHelper.displayAnimalsOnMap(animals);\\
        } else {\\
            // 如果mapHelper初始化失败，使用原始的显示方法\\
            Set<Waypoint> waypoints = new HashSet<>();\\
            for (IAnimal animal : animals) {\\
                try {\\
                    GeoPosition position = getGeoPosition(animal.getArea());\\
                    waypoints.add(new DefaultWaypoint(position));\\
                } catch (Exception e) {\\
                    System.err.println("Error displaying location for animal: " + e.getMessage());\\
                }\\
            }\\
            \\
            WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();\\
            waypointPainter.setWaypoints(waypoints);\\
            mapViewer.setOverlayPainter(waypointPainter);\\
        }\\
    }' View.java

echo "View.java 已更新！" 