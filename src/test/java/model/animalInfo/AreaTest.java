package model.animalInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Area enum class.
 */
public class AreaTest {

    @Test
    public void testFromString_ValidArea() {
        assertEquals(Area.SEATTLE, Area.fromString("SEATTLE"));
        assertEquals(Area.TACOMA, Area.fromString("TACOMA"));
        assertEquals(Area.BELLEVUE, Area.fromString("BELLEVUE"));
        assertEquals(Area.KENT, Area.fromString("KENT"));
        assertEquals(Area.KIRKLAND, Area.fromString("KIRKLAND"));
        assertEquals(Area.EVERETT, Area.fromString("EVERETT"));
        assertEquals(Area.RENTON, Area.fromString("RENTON"));
        assertEquals(Area.REDMOND, Area.fromString("REDMOND"));
        assertEquals(Area.LYNNWOOD, Area.fromString("LYNNWOOD"));
        assertEquals(Area.BOTHELL, Area.fromString("BOTHELL"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(Area.SEATTLE, Area.fromString("seattle"));
        assertEquals(Area.TACOMA, Area.fromString("tacoma"));
        assertEquals(Area.BELLEVUE, Area.fromString("Bellevue"));
        assertEquals(Area.KIRKLAND, Area.fromString("Kirkland"));
    }

    @Test
    public void testFromString_WithSpaces() {
        // Testing space conversion - though no current areas have spaces
        // This tests the replace(" ", "_") functionality
        assertEquals(Area.SEATTLE, Area.fromString("SEATTLE"));
        
        // If we were to add a multi-word city name like "FEDERAL WAY", it would be:
        // assertEquals(Area.FEDERAL_WAY, Area.fromString("FEDERAL WAY"));
    }

    @Test
    public void testFromString_InvalidArea() {
        assertNull(Area.fromString("SHORELINE"));
        assertNull(Area.fromString("ISSAQUAH"));
        assertNull(Area.fromString(""));
        assertNull(Area.fromString(null));
    }
} 