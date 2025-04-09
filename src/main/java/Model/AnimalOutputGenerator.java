package Model;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.opencsv.CSVWriter;
import java.io.StringWriter;

public class AnimalOutputGenerator implements IOutputGenerator {
    @Override
    public void generateOutput(List<PostedAnimal> animals, OutputFormat format, OutputStream outputStream) {
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

    private void generateJsonOutput(List<PostedAnimal> animals, PrintWriter writer) {
        JSONArray jsonArray = new JSONArray();
        for (PostedAnimal animal : animals) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", animal.getAnimalType());
            jsonObject.put("species", animal.getSpecies());
            jsonObject.put("size", animal.getAnimalSize());
            jsonObject.put("gender", animal.getGender());
            jsonObject.put("color", animal.getAnimalColor());
            jsonObject.put("pattern", animal.getPattern());
            jsonObject.put("age", animal.getAnimalAge());
            jsonObject.put("location", animal.getLocation());
            jsonObject.put("description", animal.getDescription());
            jsonArray.put(jsonObject);
        }
        writer.println(jsonArray.toString(2));
    }

    private void generateCsvOutput(List<PostedAnimal> animals, PrintWriter writer) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter);
        
        // Write header
        String[] header = {"Type", "Species", "Size", "Gender", "Color", "Pattern", "Age", "Location", "Description"};
        csvWriter.writeNext(header);
        
        // Write data
        for (PostedAnimal animal : animals) {
            String[] data = {
                animal.getAnimalType(),
                animal.getSpecies(),
                String.valueOf(animal.getAnimalSize()),
                String.valueOf(animal.getGender()),
                animal.getAnimalColor(),
                animal.getPattern(),
                String.valueOf(animal.getAnimalAge()),
                animal.getLocation(),
                animal.getDescription()
            };
            csvWriter.writeNext(data);
        }
        
        writer.println(stringWriter.toString());
    }

    private void generateXmlOutput(List<PostedAnimal> animals, PrintWriter writer) {
        writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        writer.println("<animals>");
        
        for (PostedAnimal animal : animals) {
            writer.println("  <animal>");
            writer.println("    <type>" + escapeXml(animal.getAnimalType()) + "</type>");
            writer.println("    <species>" + escapeXml(animal.getSpecies()) + "</species>");
            writer.println("    <size>" + animal.getAnimalSize() + "</size>");
            writer.println("    <gender>" + animal.getGender() + "</gender>");
            writer.println("    <color>" + escapeXml(animal.getAnimalColor()) + "</color>");
            writer.println("    <pattern>" + escapeXml(animal.getPattern()) + "</pattern>");
            writer.println("    <age>" + animal.getAnimalAge() + "</age>");
            writer.println("    <location>" + escapeXml(animal.getLocation()) + "</location>");
            writer.println("    <description>" + escapeXml(animal.getDescription()) + "</description>");
            writer.println("  </animal>");
        }
        
        writer.println("</animals>");
    }

    private void generatePrettyOutput(List<PostedAnimal> animals, PrintWriter writer) {
        for (PostedAnimal animal : animals) {
            writer.println("Animal Information:");
            writer.println("------------------");
            writer.println("Type: " + animal.getAnimalType());
            writer.println("Species: " + animal.getSpecies());
            writer.println("Size: " + animal.getAnimalSize());
            writer.println("Gender: " + animal.getGender());
            writer.println("Color: " + animal.getAnimalColor());
            writer.println("Pattern: " + animal.getPattern());
            writer.println("Age: " + animal.getAnimalAge());
            writer.println("Location: " + animal.getLocation());
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