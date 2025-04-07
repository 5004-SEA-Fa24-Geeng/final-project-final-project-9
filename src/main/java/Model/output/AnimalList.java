package Model.output;

import Model.PostedAnimal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalList {

    private List<PostedAnimal> animalList = new ArrayList<>();

    public AnimalList() {}


    public void addToList(String sortOn, Stream<PostedAnimal> filtered){

        List<PostedAnimal> filteredList = filtered.collect(Collectors.toList());

        for (PostedAnimal pa : filteredList) {
            if (pa.getIfAdded()){
                this.animalList.add(pa);
            }

        }


    }
}
