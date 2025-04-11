import Model.InputModel.AnimalFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalFilterTest {

    private Set<PostedAnimal> animals;

    @BeforeEach
    void setUp() {
        animals = new HashSet<>();
        animals.add(new PostedAnimal(1,"cat","American Shorthair",1,1,"Tabby","Black",1,new Integer[]{25, 3, 30, 15, 30},"near Standard","Seattle","UD","1","1","1"));
        animals.add(new PostedAnimal(2,"dog","Poodle",3,1,"Solid","White",1,new Integer[]{25, 3, 30, 10, 30},"","Bellevue","Clyde","1","1","1"));
        animals.add(new PostedAnimal(3,"cat","Ragdoll",2,1,"Solid","White",1,new Integer[]{25, 3, 30, 10, 30},"","Bellevue","Downtown","1","1","1"));
    }

    @Test
    void filterTest() {
        Stream<PostedAnimal> s;
        String[] filter = {"cat","Not Sure","Not Sure","Not Sure","Not Sure","Not Sure","Not Sure","Not Sure","Downtown,UD","within one month"};
        AnimalFilter af = new AnimalFilter(animals);
        s=af.filter(filter);
        List<PostedAnimal> filteredList = s.collect(Collectors.toList());
        assertEquals(2, filteredList.size());
    }
}