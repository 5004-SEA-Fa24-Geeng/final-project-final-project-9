package Model.Output;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 * Tests for the OutputFormat enum.
 */
public class OutputFormatTest {

    @Test
    public void testFromString_ValidFormats() {
        assertEquals(OutputFormat.JSON, OutputFormat.fromString("JSON"));
        assertEquals(OutputFormat.CSV, OutputFormat.fromString("CSV"));
        assertEquals(OutputFormat.XML, OutputFormat.fromString("XML"));
        assertEquals(OutputFormat.TXT, OutputFormat.fromString("TXT"));
    }

    @Test
    public void testFromString_CaseInsensitive() {
        assertEquals(OutputFormat.JSON, OutputFormat.fromString("json"));
        assertEquals(OutputFormat.CSV, OutputFormat.fromString("csv"));
        assertEquals(OutputFormat.XML, OutputFormat.fromString("Xml"));
        assertEquals(OutputFormat.TXT, OutputFormat.fromString("txt"));
    }

    @Test
    public void testFromString_InvalidFormats() {
        assertNull(OutputFormat.fromString("YAML"));
        assertNull(OutputFormat.fromString("HTML"));
        assertNull(OutputFormat.fromString("PDF"));
        assertNull(OutputFormat.fromString(""));
        assertNull(OutputFormat.fromString(null));
    }
} 