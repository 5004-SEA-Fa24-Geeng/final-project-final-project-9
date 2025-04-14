package Model.AnimalInfo;

/**
 * Enum representing the size categories for animals.
 * This enum provides standardized size classifications for animals in the system.
 */
public enum Size {
    /** Micro-sized animals (e.g., small rodents, tiny birds) */
    MICRO,
    /** Small-sized animals (e.g., small dogs, cats) */
    SMALL,
    /** Medium-sized animals (e.g., medium dogs, large cats) */
    MEDIUM,
    /** Large-sized animals (e.g., large dogs, geese) */
    LARGE;

    /**
     * Converts a string representation of size to the corresponding Size enum value.
     * @param size The string representation of the size (case-insensitive)
     * @return The corresponding Size enum value, or null if the string doesn't match any size
     */
    public static Size fromString(String size) {
        try {
            return Size.valueOf(size.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}