package Model.AnimalInfo;

/**
 * Enum representing cities in Washington State where animals have been reported.
 * This enum provides a standardized list of supported cities for animal sightings.
 */
public enum Area {
    /** Major cities in Washington State where animal sightings are tracked */
    SEATTLE, TACOMA, BELLEVUE, KENT, KIRKLAND, EVERETT, RENTON, REDMOND, LYNNWOOD, BOTHELL;

    /**
     * Converts a string representation of a city to the corresponding Area enum value.
     * Handles spaces in city names by converting them to underscores.
     * @param area The string representation of the city name (case-insensitive)
     * @return The corresponding Area enum value, or null if the string doesn't match any city
     */
    public static Area fromString(String area) {
        try {
            return Area.valueOf(area.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
