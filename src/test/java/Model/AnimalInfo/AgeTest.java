package Model.AnimalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Age enum class.
 */
public class AgeTest {

    @Test
    public void testFromString_ValidAge() {
        assertEquals(Age.BABY, Age.fromString("BABY"));
        assertEquals(Age.YOUNG, Age.fromString("YOUNG"));
        assertEquals(Age.ADULT, Age.fromString("ADULT"));
        assertEquals(Age.OLD, Age.fromString("OLD"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Age.BABY, Age.fromString("baby"));
        assertEquals(Age.YOUNG, Age.fromString("young"));
        assertEquals(Age.ADULT, Age.fromString("Adult"));
        assertEquals(Age.OLD, Age.fromString("Old"));
    }

    @Test
    public void testFromString_InvalidAge() {
        assertNull(Age.fromString("TEENAGER"));
        assertNull(Age.fromString("MIDDLE_AGED"));
        assertNull(Age.fromString(""));
    }
} 