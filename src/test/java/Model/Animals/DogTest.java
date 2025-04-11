package Model.Animals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DogTest {
    private Dog dog;

    @BeforeEach
    void setUp() {
        dog = new Dog(
            "DOG", "Labrador", "Medium", "Male", "Solid", "Black",
            "Adult", "04/11/2024", "10:00", "123 Main St", "Downtown",
            "Near park", "Friendly dog", "1"
        );
    }

    @Test
    void testGetters() {
        assertEquals("DOG", dog.getAnimalType());
        assertEquals("Labrador", dog.getSpecies());
        assertEquals("Medium", dog.getAnimalSize());
        assertEquals("Male", dog.getGender());
        assertEquals("Solid", dog.getPattern());
        assertEquals("Black", dog.getColor());
        assertEquals("Adult", dog.getAge());
        assertEquals("04/11/2024", dog.getSeenDate());
        assertEquals("10:00", dog.getTime());
        assertEquals("123 Main St", dog.getAddress());
        assertEquals("Downtown", dog.getArea());
        assertEquals("Near park", dog.getLocDesc());
        assertEquals("Friendly dog", dog.getDescription());
        assertEquals("1", dog.getNumber());
    }

    @Test
    void testSetters() {
        dog.setAnimalType("DOG");
        assertEquals("DOG", dog.getAnimalType());

        dog.setSpecies("Golden Retriever");
        assertEquals("Golden Retriever", dog.getSpecies());

        dog.setAnimalSize("Large");
        assertEquals("Large", dog.getAnimalSize());

        dog.setGender("Female");
        assertEquals("Female", dog.getGender());

        dog.setPattern("Spotted");
        assertEquals("Spotted", dog.getPattern());

        dog.setColor("Golden");
        assertEquals("Golden", dog.getColor());

        dog.setAge("Puppy");
        assertEquals("Puppy", dog.getAge());

        dog.setSeenDate("04/12/2024");
        assertEquals("04/12/2024", dog.getSeenDate());

        dog.setTime("11:00");
        assertEquals("11:00", dog.getTime());

        dog.setAddress("456 Oak St");
        assertEquals("456 Oak St", dog.getAddress());

        dog.setArea("Suburbs");
        assertEquals("Suburbs", dog.getArea());

        dog.setLocDesc("Backyard");
        assertEquals("Backyard", dog.getLocDesc());

        dog.setDescription("Playful puppy");
        assertEquals("Playful puppy", dog.getDescription());
    }

    @Test
    void testImagePath() {
        String imagePath = dog.getImage();
        assertNotNull(imagePath);
        assertTrue(imagePath.startsWith("data/animalImage/"));
    }
} 