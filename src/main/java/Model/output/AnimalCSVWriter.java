package Model.output;

import Model.Animals.IAnimal;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.List;

public class AnimalCSVWriter {
    private final String filePath;

    public AnimalCSVWriter(String filePath) {
        this.filePath = filePath;
    }

    public void writeAnimals(List<IAnimal> animals) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Write header
            String[] header = {
                    "AnimalType", "Species", "AnimalSize", "Gender", "Pattern", "Color",
                    "Age", "Address", "City", "Time", "Date", "AnimalDescription",
                    "LocationDescription", "Number", "Image"
            };
            writer.writeNext(header);
            // Write data
            for (IAnimal animal : animals) {
                String[] data = {
                        animal.getAnimalType(),      // AnimalType
                        animal.getSpecies(),         // Species
                        animal.getAnimalSize(),      // AnimalSize
                        animal.getGender(),          // Gender
                        animal.getPattern(),         // Pattern
                        animal.getColor(),           // Color
                        animal.getAge(),             // Age
                        animal.getAddress(),         // Address
                        animal.getArea(),            // City
                        animal.getTime(),            // Time
                        animal.getSeenDate(),            // Date
                        animal.getDescription(),     // AnimalDescription
                        animal.getLocDesc(),         // LocationDescription
                        animal.getNumber(),           // Number
                        animal.getImage()           // Image
                };
                writer.writeNext(data);
            }
        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}