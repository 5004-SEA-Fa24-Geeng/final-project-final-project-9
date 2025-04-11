import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Model.PostedAnimal;

class PostedAnimalTest {

    @Test
    void testConstruct() {
        PostedAnimal pa = new PostedAnimal(
            1.0, "cat", "American Shorthair", 1, 1, "Tabby", "Black", 1,
            new Integer[]{2024, 3, 30, 15, 30}, "near Standard", "Seattle", false,
            "user1", "image1.jpg", "Test description",
            47.6062, -122.3321 // Seattle coordinates
        );
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

    @Test
    void testGetters() {
        PostedAnimal pa = new PostedAnimal(
            1.0, "cat", "American Shorthair", 1, 1, "Tabby", "Black", 1,
            new Integer[]{2024, 3, 30, 15, 30}, "near Standard", "Seattle", false,
            "user1", "image1.jpg", "Test description",
            47.6062, -122.3321 // Seattle coordinates
        );

        assertEquals(1.0, pa.getNumber());
        assertEquals("cat", pa.getAnimalType());
        assertEquals("American Shorthair", pa.getSpecies());
        assertEquals(1, pa.getAnimalSize());
        assertEquals(1, pa.getGender());
        assertEquals("Tabby", pa.getPattern());
        assertEquals("Black", pa.getAnimalColor());
        assertEquals(1, pa.getAnimalAge());
        assertEquals("near Standard", pa.getloDescription());
        assertEquals("Seattle", pa.getLocation());
        assertEquals(false, pa.getIfAdded());
        assertEquals("user1", pa.getWitness());
        assertEquals("image1.jpg", pa.getImage());
        assertEquals("Test description", pa.getDescription());
        assertEquals(47.6062, pa.getLatitude());
        assertEquals(-122.3321, pa.getLongitude());
    }
}