package Model.AnimalInfo;

/**
 * Enum representing the gender categories for animals.
 * This enum provides standardized gender classifications for animals in the system.
 */
public enum Gender {
    /** Female animals */
    FEMALE,
    /** Male animals */
    MALE,
    /** Gender is unknown or not specified */
    UNKNOWN;

    /**
     * Converts a string representation of gender to the corresponding Gender enum value.
     * @param gender The string representation of the gender (case-insensitive)
     * @return The corresponding Gender enum value, or null if the string doesn't match any gender
     */
    public static Gender fromString(String gender) {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}