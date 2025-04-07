import Model.PostedAnimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostedAnimalTest {

    @Test
    void testConstruct() {

        PostedAnimal pa = new PostedAnimal(1,"cat","American Shorthair",1,1,"Tabby","Black",1,new Integer[]{25, 3, 30, 15, 30},"near Standard","Seattle","UD","1","1","1");
        assertTrue(pa instanceof PostedAnimal);
    }

    @Test
    void getNumber() {
    }

    @Test
    void getAnimalType() {
    }

    @Test
    void getSpecies() {
    }

    @Test
    void getPattern() {
    }

    @Test
    void getAnimalColor() {
    }

    @Test
    void getAnimalAge() {
    }

    @Test
    void getWitnessedTime() {
    }

    @Test
    void getLocationState() {
    }

    @Test
    void getLocationArea1() {
    }

    @Test
    void getLocationArea2() {
    }

    @Test
    void getWitness() {
    }

    @Test
    void getImage() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getGender() {
    }

    @Test
    void getAnimalSize() {
    }
}