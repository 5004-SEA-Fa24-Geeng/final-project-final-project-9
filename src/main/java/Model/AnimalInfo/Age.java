package Model.AnimalInfo;

/**
 * Enum representing the age categories for animals.
 * This enum provides standardized age classifications for animals in the system.
 */
public enum Age {
    /** Newborn or very young animals */
    BABY,
    /** Juvenile animals that are not fully grown */
    YOUNG,
    /** Fully grown adult animals */
    ADULT,
    /** Senior animals */
    OLD;

    /**
     * Converts a string representation of age to the corresponding Age enum value.
     * @param age The string representation of the age category (case-insensitive)
     * @return The corresponding Age enum value, or null if the string doesn't match any age category
     */
    public static Age fromString(String age) {
        try {
            return Age.valueOf(age.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
