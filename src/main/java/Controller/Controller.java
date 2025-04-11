package Controller;

import Model.Animals.IAnimal;
import Model.AnimalFilter;
import Model.AnimalList;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    static String filePath = "data/animals.csv";
    Set<IAnimal> loadedAnimals = new HashSet<>();
    Stream<IAnimal> stream = Stream.empty();
    AnimalFilter af = new AnimalFilter(loadedAnimals);

    public Controller() {
        loadedAnimals = Controller.loadFromDatabase();
    }

    public void clickGenReport(String type, String species, int size, int gender, String pattern, String color, int age, Integer[] time, String lodes, String location, boolean ifAdded, String witness, String image, String description, double latitude, double longitude) {
        IAnimal pa = new IAnimal(loadedAnimals.size(), type, species, size, gender, pattern, color, age, time, lodes, location, ifAdded, witness, image, description, latitude, longitude);
        loadedAnimals.add(pa);
        //写进数据库
    }

    public void clickFilter() {
        String filter = "";
        stream = af.filter(filter);
    }

    public void clickSort() {
        //
    }

    public void clickExportToFile(String format) {
        AnimalList.addToList(stream);
        AnimalList.write(format);
    }

    public void clickReset() {
        //感觉reset可以删掉
    }

    public static Set<IAnimal> loadFromDatabase() {
        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<IAnimal> csvToBean = new CsvToBeanBuilder<IAnimal>(reader)
                    .withType(IAnimal.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse().stream().collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
