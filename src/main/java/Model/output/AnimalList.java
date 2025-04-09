package Model.output;

import Model.PostedAnimal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalList {

    private static List<PostedAnimal> animalList = new ArrayList<>();
    private static String filePath = "resources/animals.json";

    public AnimalList() {}

    public static void addToList(Stream<PostedAnimal> filtered){

        List<PostedAnimal> filteredList = filtered.collect(Collectors.toList());

        for (PostedAnimal pa : filteredList) {
            if (pa.getIfAdded()){
                animalList.add(pa);
            }
        }

    }


    public static void writeAnimalsToJson() {
        ObjectMapper mapper = new ObjectMapper();//使用库
        try {
            mapper.writeValue(new File(filePath), animalList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeAnimalsToCSV() {
        try (FileWriter writer = new FileWriter(filePath)) {//仍需修改
            writer.append("Number,Type,Species,Size,Gender,Pattern,Color,Age,Location\n"); // CSV 头部
            for (PostedAnimal animal : animalList) {
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


    public static void writeAnimalsToXml() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            AnimalListWrapper wrapper = new AnimalListWrapper(animalList);
            xmlMapper.writeValue(new File(filePath), wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String format) {

        switch (format) {
            case "XML":
                writeAnimalsToXml();
                break;
            case "JSON":
                writeAnimalsToJson();
                break;
            case "CSV":
                writeAnimalsToCSV();
                break;
            default:
                //prettyPrint(records, out);
                break;
        }

    }



}
