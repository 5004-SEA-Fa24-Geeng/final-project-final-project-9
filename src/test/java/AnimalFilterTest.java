import Model.InputModel.AnimalFilter;
import Model.PostedAnimal;
import Model.UserWit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalFilterTest {

    private List<PostedAnimal> animals;

    @BeforeEach
    void setUp() {
        animals = new ArrayList<>();
        UserWit user1 = new UserWit(1,"1","1","1");
        UserWit user2 = new UserWit(2,"2","2","2");
        animals.add(new PostedAnimal(1,"cat","American Shorthair","1","1","Tabby","Black","1","25-3-30","near Standard","Seattle, UD",false, user1.getNumber(),"2","1"));
        animals.add(new PostedAnimal(2,"dog","Poodle","2","1","","Grey","1","25-3-30","","Kent,Lake Fenwick",false, user2.getNumber(),"1","1"));
        animals.add(new PostedAnimal(3,"cat","Ragdoll","2","0","Solid","White","1","25-04-05","","Bellevue,",false, user1.getNumber(),"1","1"));
        animals.add(new PostedAnimal(4,"dog","Golden Retriever","3","1","","yellow","1","25-02-09","","Seattle, Capital Hill",false, user1.getNumber(),"1","1"));
        animals.add(new PostedAnimal(5,"rabbit","Netherland Dwarf Rabbit","1","1","","White","1","25-01-25","","Bellevue",false, user1.getNumber(),"1","1"));
    }

    @Test
    void filterTest() {
        Stream<PostedAnimal> s;
        String filter = "Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure";
        AnimalFilter af = new AnimalFilter(animals);
        s=af.filter(filter);
        assertEquals(2, s.toList().size());
    }
}