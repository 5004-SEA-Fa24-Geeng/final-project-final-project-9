import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.InputModel.AnimalFilter;
import Model.PostedAnimal;

class AnimalFilterTest {
    private Set<PostedAnimal> animals;
    private AnimalFilter af;
    private Stream<PostedAnimal> s;

    @BeforeEach
    void setUp() {
        animals = new HashSet<>();
        animals.add(new PostedAnimal(1.0,"cat","American Shorthair",1,1,"Tabby","Black",1,
            new Integer[]{2024, 3, 30, 15, 30},"near Standard","Seattle",false,"user1","image1.jpg","Test description 1",
            47.6062, -122.3321)); // Seattle coordinates
        animals.add(new PostedAnimal(2.0,"dog","Poodle",3,1,"Solid","White",1,
            new Integer[]{2024, 3, 30, 10, 30},"","Bellevue",false,"user2","image2.jpg","Test description 2",
            47.6101, -122.2015)); // Bellevue coordinates
        animals.add(new PostedAnimal(3.0,"cat","Ragdoll",2,1,"Solid","White",1,
            new Integer[]{2024, 3, 30, 10, 30},"","Bellevue",false,"user3","image3.jpg","Test description 3",
            47.6101, -122.2015)); // Bellevue coordinates
        af = new AnimalFilter(animals);
    }

    @Test
    void testFilter() {
        String filter = "cat,American Shorthair,1,1,Tabby,Black,1,Seattle,UD,within one week";
        s = af.filter(filter);
        assertEquals(1, s.count());
    }
}