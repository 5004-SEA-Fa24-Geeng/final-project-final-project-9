package model.input;

import model.animals.IAnimal;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * Writes a list of IAnimal objects to a CSV file in the specified format.
 */
public class AnimalCSVWriter {
    /** The path to the CSV file to write. */
    private final String filePath;

    /**
     * Constructs an AnimalCSVWriter with the specified file path.
     *
     * @param filePath the path to the CSV file
     */
    public AnimalCSVWriter(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writes the provided list of IAnimal objects to the CSV file.
     *
     * @param animals the list of animals to write to the file
     */
    public void writeAnimals(List<IAnimal> animals) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Write header
            String[] header = {
                    "AnimalType", "species", "AnimalSize", "Gender", "Pattern", "Color",
                    "Age", "Address", "City", "Time", "Date", "AnimalDescription",
                    "LocationDescription", "Number", "Image"
            };
            writer.writeNext(header);
            // Write data
            for (IAnimal animal : animals) {
                String[] data = {
                        animal.getAnimalType(),      // AnimalType
                        animal.getSpecies(),         // species
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
