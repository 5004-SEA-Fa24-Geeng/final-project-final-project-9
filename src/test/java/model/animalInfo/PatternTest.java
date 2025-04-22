package model.animalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Pattern enum class.
 */
public class PatternTest {

    @Test
    public void testFromString_ValidPattern() {
        assertEquals(Pattern.SOLID, Pattern.fromString("SOLID"));
        assertEquals(Pattern.SPOTTED, Pattern.fromString("SPOTTED"));
        assertEquals(Pattern.STRIPED, Pattern.fromString("STRIPED"));
        assertEquals(Pattern.BRINDLE, Pattern.fromString("BRINDLE"));
        assertEquals(Pattern.MERLE, Pattern.fromString("MERLE"));
        assertEquals(Pattern.TUXEDO, Pattern.fromString("TUXEDO"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Pattern.SOLID, Pattern.fromString("solid"));
        assertEquals(Pattern.SPOTTED, Pattern.fromString("spotted"));
        assertEquals(Pattern.STRIPED, Pattern.fromString("Striped"));
        assertEquals(Pattern.BRINDLE, Pattern.fromString("Brindle"));
    }

    @Test
    public void testFromString_InvalidPattern() {
        assertNull(Pattern.fromString("CALICO"));
        assertNull(Pattern.fromString("TABBY"));
        assertNull(Pattern.fromString(""));
    }
} 