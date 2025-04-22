package model.animalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Size enum class.
 */
public class SizeTest {

    @Test
    public void testFromString_ValidSize() {
        assertEquals(Size.MICRO, Size.fromString("MICRO"));
        assertEquals(Size.SMALL, Size.fromString("SMALL"));
        assertEquals(Size.MEDIUM, Size.fromString("MEDIUM"));
        assertEquals(Size.LARGE, Size.fromString("LARGE"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Size.MICRO, Size.fromString("micro"));
        assertEquals(Size.SMALL, Size.fromString("small"));
        assertEquals(Size.MEDIUM, Size.fromString("Medium"));
        assertEquals(Size.LARGE, Size.fromString("Large"));
    }

    @Test
    public void testFromString_InvalidSize() {
        assertNull(Size.fromString("EXTRA_LARGE"));
        assertNull(Size.fromString("TINY"));
        assertNull(Size.fromString(""));
    }
} 