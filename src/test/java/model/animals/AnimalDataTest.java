package model.animals;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the AnimalData enum.
 */
public class AnimalDataTest {

    @Test
    public void testFromString_ValidFields() {
        assertEquals(AnimalData.TYPE, AnimalData.fromString("TYPE"));
        assertEquals(AnimalData.SPECIES, AnimalData.fromString("SPECIES"));
        assertEquals(AnimalData.SIZE, AnimalData.fromString("SIZE"));
        assertEquals(AnimalData.GENDER, AnimalData.fromString("GENDER"));
        assertEquals(AnimalData.PATTERN, AnimalData.fromString("PATTERN"));
        assertEquals(AnimalData.COLOR, AnimalData.fromString("COLOR"));
        assertEquals(AnimalData.AGE, AnimalData.fromString("AGE"));
        assertEquals(AnimalData.SEENDATE, AnimalData.fromString("SEENDATE"));
        assertEquals(AnimalData.SEENTIME, AnimalData.fromString("SEENTIME"));
        assertEquals(AnimalData.ADDRESS, AnimalData.fromString("ADDRESS"));
        assertEquals(AnimalData.AREA, AnimalData.fromString("AREA"));
        assertEquals(AnimalData.LOCDESC, AnimalData.fromString("LOCDESC"));
        assertEquals(AnimalData.DESCRIPTION, AnimalData.fromString("DESCRIPTION"));
        assertEquals(AnimalData.NUMBER, AnimalData.fromString("NUMBER"));
        assertEquals(AnimalData.IMAGE, AnimalData.fromString("IMAGE"));
    }

    @Test
    public void testFromString_InvalidFields() {
        assertNull(AnimalData.fromString("WEIGHT"));
        assertNull(AnimalData.fromString("HEIGHT"));
        assertNull(AnimalData.fromString(""));
        assertNull(AnimalData.fromString(null));
    }

    @Test
    public void testFromString_CaseSensitivity() {
        // The method should be case-sensitive
        assertNull(AnimalData.fromString("type"));
        assertNull(AnimalData.fromString("species"));
        assertNull(AnimalData.fromString("Size"));
        assertNull(AnimalData.fromString("Gender"));
    }
} 