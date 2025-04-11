import Model.Animals.Animal;
import Model.Animals.IAnimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PostedAnimalTest {

    @Test
    void testConstruct() {
        IAnimal pa = new Animal(1, "cat", "American Shorthair", 1, 1, "Tabby", "Black", 1, new Integer[]{25, 3, 30, 15, 30}, "near Standard", "Seattle", false, "UD", "1", "1", 47.6062, -122.3321);
        assertTrue(pa instanceof IAnimal);
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

    @Test
    void testGetters() {
        IAnimal pa = new Animal(
            1.0, "cat", "American Shorthair", 1, 1, "Tabby", "Black", 1,
            new Integer[]{2024, 3, 30, 15, 30}, "near Standard", "Seattle", false,
            "user1", "image1.jpg", "Test description",
            47.6062, -122.3321 // Seattle coordinates
        );

        assertEquals(1, pa.getNumber());
        assertEquals("cat", pa.getAnimalType().name().toLowerCase());
        assertEquals("American Shorthair", pa.getSpecies().name().toLowerCase());
        assertEquals(1, pa.getAnimalSize().ordinal());
        assertEquals(1, pa.getGender().ordinal());
        assertEquals("Tabby", pa.getPattern().name().toLowerCase());
        assertEquals("Black", pa.getAnimalColor().name().toLowerCase());
        assertEquals(1, pa.getAnimalAge().ordinal());
        assertEquals("near Standard", pa.getLocDesc());
        assertEquals("Seattle", pa.getAddress());
        assertEquals("user1", pa.getWitness());
        assertEquals("image1.jpg", pa.getImage());
        assertEquals("Test description", pa.getDescription());
    }
}