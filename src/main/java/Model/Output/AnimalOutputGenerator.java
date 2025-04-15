package Model.Output;

import java.io.*;
import java.util.List;

import Model.Animals.IAnimal;

import com.opencsv.CSVWriter;


/**
 * Animal list output generator.
 * Generate output in specific format to designated place
 */
public class AnimalOutputGenerator implements IOutputGenerator {
    @Override
    public void generateOutput(List<IAnimal> animals, OutputFormat format, OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);

        try {
            switch (format) {
                case JSON:
                    exportToJSON(animals, writer);
                    break;
                case CSV:
                    exportToCSV(animals, writer);
                    break;
                case XML:
                    exportToXML(animals, writer);
                    break;
                case TXT:
                    exportToTXT(animals, writer);
                    break;
            }
        } finally {
            writer.flush();
        }
    }


    /**
     * Export list to TXT.
     * @param animals animal list
     * @param writer PrintWriter instance -- to where
     */
    private void exportToTXT(List<IAnimal> animals, PrintWriter writer) {
            for (IAnimal animal : animals) {
                writer.println("Animal Information:");
                writer.println("Type: " + animal.getAnimalType());
                writer.println("Breed: " + animal.getSpecies());
                writer.println("Size: " + animal.getAnimalSize());
                writer.println("Gender: " + animal.getGender());
                writer.println("Pattern: " + animal.getPattern());
                writer.println("Color: " + animal.getColor());
                writer.println("Age: " + animal.getAge());
                writer.println("Date Seen: " + animal.getSeenDate());
                writer.println("Time Seen: " + animal.getTime());
                writer.println("Area: " + animal.getArea());
                writer.println("Address: " + animal.getAddress());
                writer.println("Location Description: " + animal.getLocDesc());
                writer.println("Description: " + animal.getDescription());
                writer.println("----------------------------------------");
            }
    }


    /**
     * Export list to JSON.
     * @param animals animal list
     * @param writer PrintWriter instance -- to where
     */
    private void exportToJSON(List<IAnimal> animals, PrintWriter writer) {
        writer.println("{");
        writer.println("  \"animals\": [");
        for (int i = 0; i < animals.size(); i++) {
            IAnimal animal = animals.get(i);
            writer.println("    {");
            writer.println("      \"type\": \"" + animal.getAnimalType() + "\",");
            writer.println("      \"breed\": \"" + animal.getSpecies() + "\",");
            writer.println("      \"size\": \"" + animal.getAnimalSize() + "\",");
            writer.println("      \"gender\": \"" + animal.getGender() + "\",");
            writer.println("      \"pattern\": \"" + animal.getPattern() + "\",");
            writer.println("      \"color\": \"" + animal.getColor() + "\",");
            writer.println("      \"age\": \"" + animal.getAge() + "\",");
            writer.println("      \"date\": \"" + animal.getSeenDate() + "\",");
            writer.println("      \"time\": \"" + animal.getTime() + "\",");
            writer.println("      \"city\": \"" + animal.getArea() + "\",");
            writer.println("      \"address\": \"" + animal.getAddress() + "\",");
            writer.println("      \"locationDesc\": \"" + animal.getLocDesc() + "\",");
            writer.println("      \"description\": \"" + animal.getDescription() + "\"");
            writer.println("    }" + (i < animals.size() - 1 ? "," : ""));
        }
        writer.println("  ]");
        writer.println("}");
    }


    /**
     * Export list to CSV.
     * @param animals animal list
     * @param writer PrintWriter instance -- to where
     */
    private void exportToCSV(List<IAnimal> animals, PrintWriter writer) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        // Write header
        String[] header = {"Type", "Breed", "Size", "Gender", "Pattern", "Color", "Age", "Date", "Time", "Address",
                "City", "LocationDesc", "Description",};
        csvWriter.writeNext(header);

        // Write data
        for (IAnimal animal : animals) {
            String[] data = {
                    animal.getAnimalType(),
                    animal.getSpecies(),
                    animal.getAnimalSize(),
                    animal.getGender(),
                    animal.getPattern(),
                    animal.getColor(),
                    animal.getAge(),
                    animal.getSeenDate(),
                    animal.getTime(),
                    animal.getAddress(),
                    animal.getArea(),
                    animal.getLocDesc(),
                    animal.getDescription()
            };
            csvWriter.writeNext(data);
        }
        writer.println(stringWriter);
    }


    /**
     * Export list to XML.
     * @param animals animal list
     * @param writer PrintWriter instance -- to where
     */
    private void exportToXML(List<IAnimal> animals, PrintWriter writer) {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<animals>");

        for (IAnimal animal : animals) {
            writer.println("  <animal>");
            writer.println("    <type>" + animal.getAnimalType() + "</type>");
            writer.println("    <breed>" + animal.getSpecies() + "</breed>");
            writer.println("    <size>" + animal.getAnimalSize() + "</size>");
            writer.println("    <gender>" + animal.getGender() + "</gender>");
            writer.println("    <pattern>" + animal.getPattern() + "</pattern>");
            writer.println("    <color>" + animal.getColor() + "</color>");
            writer.println("    <age>" + animal.getAge() + "</age>");
            writer.println("    <date>" + animal.getSeenDate() + "</date>");
            writer.println("    <time>" + animal.getTime() + "</time>");
            writer.println("    <city>" + animal.getArea() + "</city>");
            writer.println("    <address>" + animal.getAddress() + "</address>");
            writer.println("    <locationDesc>" + animal.getLocDesc() + "</locationDesc>");
            writer.println("    <description>" + animal.getDescription() + "</description>");
            writer.println("  </animal>");
        }

        writer.println("</animals>");
    }

}