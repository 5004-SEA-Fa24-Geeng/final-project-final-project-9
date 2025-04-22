package model.animalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AnimalType enum class.
 */
public class AnimalTypeTest {

    @Test
    public void testFromString_ValidType() {
        assertEquals(AnimalType.DOG, AnimalType.fromString("DOG"));
        assertEquals(AnimalType.CAT, AnimalType.fromString("CAT"));
        assertEquals(AnimalType.RABBIT, AnimalType.fromString("RABBIT"));
        assertEquals(AnimalType.BIRD, AnimalType.fromString("BIRD"));
        assertEquals(AnimalType.HAMSTER, AnimalType.fromString("HAMSTER"));
        assertEquals(AnimalType.DUCK, AnimalType.fromString("DUCK"));
        assertEquals(AnimalType.HEDGEHOG, AnimalType.fromString("HEDGEHOG"));
        assertEquals(AnimalType.GOOSE, AnimalType.fromString("GOOSE"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(AnimalType.DOG, AnimalType.fromString("dog"));
        assertEquals(AnimalType.CAT, AnimalType.fromString("cat"));
        assertEquals(AnimalType.RABBIT, AnimalType.fromString("Rabbit"));
        assertEquals(AnimalType.DUCK, AnimalType.fromString("Duck"));
    }

    @Test
    public void testFromString_InvalidType() {
        assertNull(AnimalType.fromString("UNKNOWN"));
        assertNull(AnimalType.fromString("INVALID"));
        assertNull(AnimalType.fromString(""));
    }
} 