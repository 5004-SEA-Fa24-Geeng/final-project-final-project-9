package model.animalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Gender enum class.
 */
public class GenderTest {

    @Test
    public void testFromString_ValidGender() {
        assertEquals(Gender.FEMALE, Gender.fromString("FEMALE"));
        assertEquals(Gender.MALE, Gender.fromString("MALE"));
        assertEquals(Gender.UNKNOWN, Gender.fromString("UNKNOWN"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Gender.FEMALE, Gender.fromString("female"));
        assertEquals(Gender.MALE, Gender.fromString("male"));
        assertEquals(Gender.UNKNOWN, Gender.fromString("Unknown"));
    }

    @Test
    public void testFromString_InvalidGender() {
        assertNull(Gender.fromString("OTHER"));
        assertNull(Gender.fromString("NON_BINARY"));
        assertNull(Gender.fromString(""));
    }
} 