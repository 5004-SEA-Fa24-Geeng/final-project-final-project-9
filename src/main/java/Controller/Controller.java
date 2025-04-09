package Controller;

import Model.InputModel.AnimalFilter;
import Model.PostedAnimal;
import Model.UserWit;
import Model.output.AnimalList;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    static String filePath = "data/animals.csv";
    Set<PostedAnimal> loadedAnimals = new HashSet<>();
    Stream<PostedAnimal> stream = Stream.empty();;
    AnimalFilter af = new AnimalFilter(loadedAnimals);


    public Controller() {
        loadedAnimals = Controller.loadFromDatabase();
    }


    public void clickGenReport(String type, String species, int size, int gender, String pattern, String color, int age, Integer[] time, String lodes, String location, boolean ifAdded, UserWit witness, String image, String descroption){
        PostedAnimal pa = new PostedAnimal(loadedAnimals.size(),type,species,size,gender,pattern,color,age,time,lodes,location,ifAdded,witness,image,descroption);
        loadedAnimals.add(pa);
        //写进数据库
    }

    public void clickFilter(){
        String filter="";
        stream = af.filter(filter);
    }


    public void clickSort(){
        //
    }



    public void clickExportToFile(String format){
        AnimalList.addToList(stream);
        AnimalList.write(format);
    }

    public void clickReset(){

    }//感觉reset可以删掉

    public static Set<PostedAnimal> loadFromDatabase(){

            try (FileReader reader = new FileReader(filePath)) {
                CsvToBean<PostedAnimal> csvToBean = new CsvToBeanBuilder<PostedAnimal>(reader)
                        .withType(PostedAnimal.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                return csvToBean.parse().stream().collect(Collectors.toSet());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

    }
}
