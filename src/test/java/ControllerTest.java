import Controller.Controller;
import Model.PetOwner;
import Model.PostedAnimal;
import Model.UserWit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    Controller controller;
    PetOwner po;
    UserWit wit;

    @BeforeEach
    void setUp() {
        po = new PetOwner(1,"po");
        controller = new Controller(po);
        wit = new UserWit(2,"2","2","2");

    }

    @Test
    void clickGenReport() {
        controller.clickReport("cat","American Shorthair","1","1","Tabby","Black","1","25-3-30","near Standard","Seattle, UD",wit,"2","1");
    }

    @Test
    void clickFilter() {
        //controller.clickFilter();
        //assertEquals(controller.getStream().count(), 0);


    }

    @Test
    void clickSort() {
    }

    @Test
    void clickExportToFile() {
    }

    @Test
    void clickReset() {
    }

    @Test
    void loadFromDatabase() {
        List<PostedAnimal> loadedAnimals = controller.getList();
        assertEquals(loadedAnimals.stream().toList().getFirst().getWitnessedTime(), 3);
    }
}