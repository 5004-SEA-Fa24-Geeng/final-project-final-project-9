package Controller;

import Model.InputModel.AnimalFilter;
import Model.PetOwner;
import Model.PostedAnimal;
import Model.UserWit;
import Model.output.AnimalList;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static String filePath = "src/main/resources/input.csv";
    private List<PostedAnimal> allAnimals;
    private List<PostedAnimal> filteredAnimals;
    private PetOwner owner;




    public Controller(PetOwner owner) {
        allAnimals = Controller.loadFromDatabase();
        this.owner = owner;
    }


    public void clickReport(String type, String species, String size, String gender, String pattern, String color, String age, String time, String lodes, String location, UserWit witness, String image, String descroption){
        PostedAnimal pa = new PostedAnimal(allAnimals.size(),type,species,size,gender,pattern,color,age,time,lodes,location,false,witness.getNumber(),image,descroption);
        allAnimals.add(pa);
        saveAnimalsToCSV(pa);
        //写进数据
    }

    public void clickFilter(){
        AnimalFilter af = new AnimalFilter(allAnimals);
        String filter = "Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,within one week";
        filteredAnimals = af.filter(filter).toList();
    }


    public void clickSort(){
        //
    }

    public void clickAddToList(){
        AnimalList.addToList(filteredAnimals, owner.getList());
    }

    public void clickExportToFile(String format){
        AnimalList.write(format, owner.getList());
    }


    public void clickShowList(){

        for(PostedAnimal p : owner.getList()){
            System.out.println(p.getSpecies());
        }
    }//感觉reset可以删掉


    public static List<PostedAnimal> loadFromDatabase(){
        String fp = "src/main/resources/input.csv";
            try (FileReader reader = new FileReader(fp)) {
                CsvToBean<PostedAnimal> csvToBean = new CsvToBeanBuilder<PostedAnimal>(reader)
                        .withType(PostedAnimal.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();

                return new ArrayList<>(csvToBean.parse());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

    }

    public List<PostedAnimal> getList(){
        return allAnimals;
    }

    public static void saveAnimalsToCSV(PostedAnimal animal) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {

            String[] data = {
                    //String.valueOf(animal.getNumber()),
                    animal.getAnimalType(),
                    //animal.getSpecies(),
                    //animal.getAnimalSize(),
                    //animal.getGender(),
                    //animal.getPattern(),
                    animal.getAnimalColor(),
                    animal.getAnimalAge(),
                    animal.getWitnessedTime(),
                    animal.getloDescription(),
                    animal.getLocation(),
                    "false",
                    String.valueOf(animal.getWitnessNo()),
                    animal.getImage(),
                    animal.getDescription(),
                    animal.getGender(),
                    animal.getAnimalSize(),
                    animal.getSpecies(),
                    String.valueOf(animal.getNumber())
            };
            writer.writeNext(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
