# View.java 修改指南

## 导入语句

在导入部分添加以下内容：

```java
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import View.Map.AnimalMapHelper;
```

## 添加实例变量

在View类的实例变量部分添加：

```java
private AnimalMapHelper mapHelper; // 添加地图帮助类实例变量
```

## 修改initializeMapPanel方法

找到initializeMapPanel方法（第920行左右），在方法的末尾添加以下代码：

```java
// 初始化地图帮助类
try {
    mapHelper = new AnimalMapHelper(mapViewer, animal -> showAnimalDetails(animal));
} catch (Exception e) {
    System.err.println("Error initializing map helper: " + e.getMessage());
    e.printStackTrace();
}
```

在`mapPanel.add(mapViewer, BorderLayout.CENTER);`语句之前添加。

## 修改showAnimalDetails方法

找到showAnimalDetails方法（第514行左右），将其访问修饰符从private改为public：

```java
public void showAnimalDetails(IAnimal animal) {
    // 方法内容保持不变
```

## 替换displayMap方法

找到displayMap方法（第941行左右），将其替换为：

```java
@Override
public void displayMap(List<IAnimal> animals) {
    // 检查mapHelper是否已初始化
    if (mapHelper == null) {
        try {
            mapHelper = new AnimalMapHelper(mapViewer, animal -> showAnimalDetails(animal));
        } catch (Exception e) {
            System.err.println("Error initializing map helper: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 使用地图帮助类显示动物
    if (mapHelper != null) {
        mapHelper.displayAnimalsOnMap(animals);
    } else {
        // 如果mapHelper初始化失败，使用原始的显示方法
        Set<Waypoint> waypoints = new HashSet<>();
        for (IAnimal animal : animals) {
            try {
                GeoPosition position = getGeoPosition(animal.getArea());
                waypoints.add(new DefaultWaypoint(position));
            } catch (Exception e) {
                System.err.println("Error displaying location for animal: " + e.getMessage());
            }
        }
        
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(waypointPainter);
    }
}
```

## 修改后的功能

完成这些修改后，地图上将会显示每个动物的位置坐标，且当点击地图上的标记点时，会显示该动物的详细信息。动物的位置会基于其地址和城市信息映射到地图上的实际坐标，并自动添加Washington State以提高定位准确性。

使用函数式接口传递showAnimalDetails方法解决了访问权限问题，使代码更加模块化和可维护。 