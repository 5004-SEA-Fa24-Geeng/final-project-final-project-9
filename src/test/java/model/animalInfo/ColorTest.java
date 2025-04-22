package model.animalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Color enum class.
 */
public class ColorTest {

    @Test
    public void testFromString_ValidColor() {
        assertEquals(Color.BLACK, Color.fromString("BLACK"));
        assertEquals(Color.WHITE, Color.fromString("WHITE"));
        assertEquals(Color.BROWN, Color.fromString("BROWN"));
        assertEquals(Color.GOLDEN, Color.fromString("GOLDEN"));
        assertEquals(Color.GRAY, Color.fromString("GRAY"));
        assertEquals(Color.ORANGE, Color.fromString("ORANGE"));
        assertEquals(Color.MIXED, Color.fromString("MIXED"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Color.BLACK, Color.fromString("black"));
        assertEquals(Color.WHITE, Color.fromString("white"));
        assertEquals(Color.BROWN, Color.fromString("Brown"));
        assertEquals(Color.GOLDEN, Color.fromString("Golden"));
    }

    @Test
    public void testFromString_InvalidColor() {
        assertNull(Color.fromString("RED"));
        assertNull(Color.fromString("BLUE"));
        assertNull(Color.fromString(""));
    }
} 