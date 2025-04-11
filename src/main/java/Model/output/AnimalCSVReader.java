package Model.output;

import Model.Animals.IAnimal;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class AnimalCSVReader {
    private final String filePath;

    public AnimalCSVReader(String filePath) {
        this.filePath = filePath;
    }

    public List<IAnimal> readAnimals() {
        List<IAnimal> animals = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            List<AnimalCSV> csvBeans = new CsvToBeanBuilder<AnimalCSV>(reader)
                .withType(AnimalCSV.class)
                .build()
                .parse();

            for (AnimalCSV csvBean : csvBeans) {
                animals.add(csvBean.toAnimal());
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
        return animals;
    }
} 