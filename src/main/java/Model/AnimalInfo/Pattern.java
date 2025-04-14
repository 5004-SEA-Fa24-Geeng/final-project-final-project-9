package Model.AnimalInfo;

/**
 * Enum representing the coat/fur pattern categories for animals.
 * This enum provides standardized pattern classifications for animals in the system.
 */
public enum Pattern {
    /** Single color without patterns */
    SOLID,
    /** Spotted or dappled pattern */
    SPOTTED,
    /** Striped or tabby pattern */
    STRIPED,
    /** Brindle pattern (irregular streaks) */
    BRINDLE,
    /** Merle pattern (mottled patches) */
    MERLE,
    /** Tuxedo pattern (black and white) */
    TUXEDO;

    /**
     * Converts a string representation of pattern to the corresponding Pattern enum value.
     * @param pattern The string representation of the pattern (case-insensitive)
     * @return The corresponding Pattern enum value, or null if the string doesn't match any pattern
     */
    public static Pattern fromString(String pattern) {
        try {
            return Pattern.valueOf(pattern.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}