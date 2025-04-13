package Controller;

import Model.Animals.IAnimal;
import View.IView;
import java.util.List;

public interface IController {
    /**
     * 设置视图
     * @param view 视图实例
     */
    void setView(IView view);

    /**
     * 处理过滤请求
     * @param filterType 过滤类型
     * @param filterValue 过滤值
     */
    void handleFilter(String filterType, String filterValue);

    /**
     * 处理排序请求
     * @param ascending 是否升序
     */
    void handleSort(boolean ascending);

    /**
     * 处理重置请求
     */
    void handleReset();

    /**
     * 处理地图显示请求
     */
    void handleMapDisplay();

    /**
     * 获取当前过滤后的动物列表
     * @return 动物列表
     */
    List<IAnimal> getFilteredAnimals();

    /**
     * 初始化控制器
     */
    void initialize();

    IAnimal createAnimal(
            String type, String breed, String size, String gender,
            String pattern, String color, String age, String date,
            String time, String city, String address, String locDesc,
            String description, String image
    );

    void addAnimal(IAnimal animal);

    /**
     * Export animal data to a file in the specified format
     * @param format the format to export (xml, json, txt, csv)
     */
    void exportData(String format);
}