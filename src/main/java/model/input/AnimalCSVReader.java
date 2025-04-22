package model.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

import model.animals.IAnimal;

/**
 * Reads animal data from a CSV file and converts it into a list of IAnimal objects.
 */
public class AnimalCSVReader {
    /** The path to the CSV file to read. */
    private final String filePath;

    /**
     * Constructs an AnimalCSVReader with the specified file path.
     *
     * @param filePath the path to the CSV file
     */
    public AnimalCSVReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads animals from the CSV file.
     * If the file doesn't exist or cannot be read, returns an empty list.
     *
     * @return List of IAnimal objects, or empty list if the file doesn't exist or has errors
     */
    public List<IAnimal> readAnimals() {
        List<IAnimal> animals = new ArrayList<>();
        
        // First check if file exists to avoid FileNotFoundException
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            System.err.println("Cannot read CSV file: " + filePath + " (file doesn't exist or is not readable)");
            return animals; // Return empty list for nonexistent files
        }
        
        try (FileReader reader = new FileReader(filePath)) {
            List<AnimalCSV> csvBeans = new CsvToBeanBuilder<AnimalCSV>(reader)
                    .withType(AnimalCSV.class)
                    .build()
                    .parse();

            for (AnimalCSV csvBean : csvBeans) {
                animals.add(csvBean.toAnimal());
            }
        } catch (FileNotFoundException e) {
            // This shouldn't happen since we checked existence, but just in case
            System.err.println("File not found: " + filePath);
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            e.printStackTrace();
        }
        return animals;
    }
}
