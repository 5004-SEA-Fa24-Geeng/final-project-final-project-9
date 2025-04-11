package Model.output;

import Model.PostedAnimal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;



public class AnimalList {

    private static String filePathJson = "src/main/resources/output.json";
    private static String filePathXml = "src/main/resources/output.xml";
    private static String filePathCsv = "src/main/resources/output.csv";
    private static String filePathTxt = "src/main/resources/output.txt";


    public AnimalList() {
    }



    public static void addToList(List<PostedAnimal> filteredList, Set<PostedAnimal> ownerList) {


        for (PostedAnimal pa : filteredList) {
            if (pa.getIfAdded()){
                ownerList.add(pa);
            }
        }

    }



    public static void writeAnimalsToJson(Set<PostedAnimal> ownerList) {
        ObjectMapper mapper = new ObjectMapper();//使用库
        List<PostedAnimal> al = ownerList.stream().toList();
        try {
            mapper.writeValue(new File(filePathJson), al);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeAnimalsToCSV(Set<PostedAnimal> ownerList) {
        try (FileWriter writer = new FileWriter(filePathCsv)) {//仍需修改
            writer.append("Number,Type,Species,Size,Gender,Pattern,Color,Age,Location\n"); // CSV 头部
            for (PostedAnimal animal : ownerList) {
                writer.append(animal.getNumber() + ",")
                        .append(animal.getAnimalType() + ",")
                        .append(animal.getSpecies() + ",")
                        .append(animal.getAnimalSize() + ",")
                        .append(animal.getGender() + ",")
                        .append(animal.getPattern() + ",")
                        .append(animal.getAnimalColor() + ",")
                        .append(animal.getAnimalAge() + ",")
                        .append(animal.getLocation() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeAnimalsToTxt(Set<PostedAnimal> ownerList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePathTxt))) {
            for (PostedAnimal animal : ownerList) {
                writer.println("Posted Animal Information:");
                writer.println("=====================================");
                writer.println("Animal Type: " + animal.getAnimalType());
                writer.println("Species: " + animal.getSpecies());
                writer.println("Animal Size: " + animal.getAnimalSize());
                writer.println("Gender: " + animal.getGender());
                writer.println("Pattern: " + animal.getPattern());
                writer.println("Animal Color: " + animal.getAnimalColor());
                writer.println("Animal Age: " + animal.getAnimalAge());
                writer.println("Witnessed Time: " + animal.getWitnessedTime());
                writer.println("Location Description: " + animal.getloDescription());
                writer.println("Location: " + animal.getLocation());
                writer.println("Description: " + animal.getDescription());
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

        // Test example in main




    public static void writeAnimalsToXml(Set<PostedAnimal> ownerList) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(filePathXml), ownerList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write(String format, Set<PostedAnimal> ownerList) {

        switch (format) {
            case "XML":
                writeAnimalsToXml(ownerList);
                break;
            case "JSON":
                writeAnimalsToJson(ownerList);
                break;
            case "CSV":
                writeAnimalsToCSV(ownerList);
                break;
            case "TXT":
                writeAnimalsToTxt(ownerList);
            default:
                break;
        }

    }

    //public static Set<PostedAnimal> getAnimalList() {
    //    return animalList;
    //}
}
