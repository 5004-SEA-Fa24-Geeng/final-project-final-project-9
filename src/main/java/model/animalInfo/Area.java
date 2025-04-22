package model.animalInfo;

/**
 * Enum representing cities in Washington State where animals have been reported.
 * This enum provides a standardized list of supported cities for animal sightings.
 */
public enum Area {
    /** Major cities in Washington State where animal sightings are tracked. */
    SEATTLE, TACOMA, BELLEVUE, KENT, KIRKLAND, EVERETT, RENTON, REDMOND, LYNNWOOD, BOTHELL;

    /**
     * Converts a string representation of a city to the corresponding Area enum value.
     * Handles spaces in city names by converting them to underscores.
     * @param value The string representation of the city name (case-insensitive)
     * @return The corresponding Area enum value, or null if the string doesn't match any city
     */
    public static Area fromString(String value) {
        for (Area area : Area.values()) {
            if (area.name().equalsIgnoreCase(value)) {
                return area;
            }
        }
        return null;
    }
}
