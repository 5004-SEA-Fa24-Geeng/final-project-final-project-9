# 地图视图更新说明

为了在地图上更好地显示动物位置并支持点击查看详情，我创建了`AnimalMapHelper`类。但由于无法直接修改`View.java`文件，请按照以下步骤手动更新您的代码：

## 1. 修改AnimalMapHelper类

已创建的`AnimalMapHelper`类位于`src/main/java/View/Map/AnimalMapHelper.java`，但您需要修改以下部分以解决访问权限问题：

### 将依赖View的部分替换为函数式接口

```java
// 修改构造函数和变量声明
private final JXMapViewer mapViewer;
private final java.util.function.Consumer<IAnimal> showAnimalDetailsFunction;
private Map<GeoPosition, IAnimal> positionToAnimal;

/**
 * 创建AnimalMapHelper实例
 * @param mapViewer 地图查看器组件
 * @param showAnimalDetailsFunction 显示动物详情的函数
 */
public AnimalMapHelper(JXMapViewer mapViewer, java.util.function.Consumer<IAnimal> showAnimalDetailsFunction) {
    this.mapViewer = mapViewer;
    this.showAnimalDetailsFunction = showAnimalDetailsFunction;
    this.positionToAnimal = new HashMap<>();
}

// 修改openAnimalDetailsDialog方法
private void openAnimalDetailsDialog(IAnimal animal) {
    // 使用函数式接口回调
    showAnimalDetailsFunction.accept(animal);
}
```

## 2. 在View.java中进行的修改

请在View.java中进行以下更改：

### 添加新的导入语句
```java
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import View.Map.AnimalMapHelper;
```

### 添加AnimalMapHelper实例变量
```java
private AnimalMapHelper mapHelper;
```

### 在initializeMapPanel方法中初始化MapHelper
```java
private void initializeMapPanel() {
    // 现有代码...
    
    // 使用lambda表达式初始化地图帮助类
    mapHelper = new AnimalMapHelper(mapViewer, animal -> showAnimalDetails(animal));
    
    mapPanel.add(mapViewer, BorderLayout.CENTER);
}
```

### 更新displayMap方法使用AnimalMapHelper
```java
@Override
public void displayMap(List<IAnimal> animals) {
    // 使用地图帮助类显示动物
    mapHelper.displayAnimalsOnMap(animals);
}
```

## 3. 完成这些更改后的功能

完成这些更改后，您将获得以下功能：

1. 在地图上显示动物位置，并自动将Washington State添加到地址中以提高地理位置的准确性
2. 支持点击地图上的标记点查看动物详情
3. 更好的地理位置解析和匹配

这些修改通过使用函数式接口解决了View.java中私有方法的访问问题，不需要修改showAnimalDetails方法的可见性。 