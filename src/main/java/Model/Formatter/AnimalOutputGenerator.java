package Model.Formatter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import Model.Animals.IAnimal;
import org.json.JSONArray;
import org.json.JSONObject;
import com.opencsv.CSVWriter;
import java.io.StringWriter;

public class AnimalOutputGenerator implements IOutputGenerator {
    @Override
    public void generateOutput(List<IAnimal> animals, OutputFormat format, OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);

        try {
            switch (format) {
                case JSON:
                    generateJsonOutput(animals, writer);
                    break;
                case CSV:
                    generateCsvOutput(animals, writer);
                    break;
                case XML:
                    generateXmlOutput(animals, writer);
                    break;
                case PRETTY:
                    generatePrettyOutput(animals, writer);
                    break;
            }
        } finally {
            writer.flush();
        }
    }

    private void generateJsonOutput(List<IAnimal> animals, PrintWriter writer) {
        JSONArray jsonArray = new JSONArray();
        for (IAnimal animal : animals) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", animal.getAnimalType());
            jsonObject.put("species", animal.getSpecies());
            jsonObject.put("size", animal.getAnimalSize());
            jsonObject.put("gender", animal.getGender());
            jsonObject.put("color", animal.getColor());
            jsonObject.put("pattern", animal.getPattern());
            jsonObject.put("age", animal.getAge());
            jsonObject.put("area", animal.getArea());
            jsonObject.put("description", animal.getDescription());
            jsonArray.put(jsonObject);
        }
        writer.println(jsonArray.toString(2));
    }

    private void generateCsvOutput(List<IAnimal> animals, PrintWriter writer) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);

        // Write header
        String[] header = {"Type", "Species", "Size", "Gender", "Color", "Pattern", "Age", "Location", "Description"};
        csvWriter.writeNext(header);

        // Write data
        for (IAnimal animal : animals) {
            String[] data = {
                animal.getAnimalType(),
                animal.getSpecies(),
                String.valueOf(animal.getAnimalSize()),
                String.valueOf(animal.getGender()),
                animal.getColor(),
                animal.getPattern(),
                String.valueOf(animal.getAge()),
                animal.getArea(),
                animal.getDescription()
            };
            csvWriter.writeNext(data);
        }

        writer.println(stringWriter);
    }

    private void generateXmlOutput(List<IAnimal> animals, PrintWriter writer) {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<animals>");

        for (IAnimal animal : animals) {
            writer.println("  <animal>");
            writer.println("    <type>" + escapeXml(animal.getAnimalType()) + "</type>");
            writer.println("    <species>" + escapeXml(animal.getSpecies()) + "</species>");
            writer.println("    <size>" + animal.getAnimalSize() + "</size>");
            writer.println("    <gender>" + animal.getGender() + "</gender>");
            writer.println("    <color>" + escapeXml(animal.getColor()) + "</color>");
            writer.println("    <pattern>" + escapeXml(animal.getPattern()) + "</pattern>");
            writer.println("    <age>" + animal.getAge() + "</age>");
            writer.println("    <location>" + escapeXml(animal.getArea()) + "</location>");
            writer.println("    <description>" + escapeXml(animal.getDescription()) + "</description>");
            writer.println("  </animal>");
        }

        writer.println("</animals>");
    }

    private void generatePrettyOutput(List<IAnimal> animals, PrintWriter writer) {
        for (IAnimal animal : animals) {
            writer.println("Animal Information:");
            writer.println("------------------");
            writer.println("Type: " + animal.getAnimalType());
            writer.println("Species: " + animal.getSpecies());
            writer.println("Size: " + animal.getAnimalSize());
            writer.println("Gender: " + animal.getGender());
            writer.println("Color: " + animal.getColor());
            writer.println("Pattern: " + animal.getPattern());
            writer.println("Age: " + animal.getAge());
            writer.println("Location: " + animal.getArea());
            writer.println("Description: " + animal.getDescription());
            writer.println("------------------");
            writer.println();
        }
    }

    private String escapeXml(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}