import Model.PostedAnimal;
import Model.UserWit;
import Model.output.AnimalList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class AnimalListTest {
    Set<PostedAnimal> userList;
    Stream s = Stream.empty();


    @BeforeEach
    void setUp(){

        userList = new HashSet<>();

        HashSet<PostedAnimal> animals = new HashSet<>();
        UserWit user1 = new UserWit(1,"1","1","1");
        UserWit user2 = new UserWit(2,"2","2","2");
        animals.add(new PostedAnimal(1,"cat","American Shorthair","1","1","Tabby","Black","1","25-3-30","near Standard","Seattle, UD",false, user1.getNumber(),"2","1"));
        animals.add(new PostedAnimal(2,"dog","Poodle","2","1","","Grey","1","25-3-30","","Kent,Lake Fenwick",true, user2.getNumber(),"1","1"));
        animals.add(new PostedAnimal(3,"cat","Ragdoll","2","0","Solid","White","1","25-04-05","","Bellevue,",false, user1.getNumber(),"1","1"));
        animals.add(new PostedAnimal(4,"dog","Golden Retriever","3","1","","yellow","1","25-02-09","","Seattle, Capital Hill",true, user1.getNumber(),"1","1"));
        animals.add(new PostedAnimal(5,"rabbit","Netherland Dwarf Rabbit","1","1","","White","1","25-01-25","","Bellevue",false, user1.getNumber(),"1","1"));

        s = animals.stream();
    }

    @Test
    void testAddToList(){
        AnimalList.addToList(s.toList(),userList);
        Assertions.assertEquals(userList.size(),2);
    }

    @Test
    void testWriteToJson(){
        AnimalList.addToList(s.toList(),userList);
        AnimalList.write("JSON",userList);
    }

    @Test
    void testWriteToTxt(){
        AnimalList.addToList(s.toList(),userList);
        AnimalList.write("TXT",userList);
    }

    @Test
    void testWriteToCsv(){
        AnimalList.addToList(s.toList(),userList);
        AnimalList.write("CSV",userList);
    }


}
