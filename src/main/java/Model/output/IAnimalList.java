package Model.output;

import Model.PostedAnimal;

import java.util.List;
import java.util.stream.Stream;

public interface IAnimalList {

    String ADD_ALL = "all";

    List<String> getGameNames();

    void clear();

    int count();

    void saveGame(String filename);

    void addToList(String str, Stream<PostedAnimal> filtered) throws IllegalArgumentException;

    void removeFromList(String str) throws IllegalArgumentException;
}
