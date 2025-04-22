package Model.AnimalInfo;

/**
 * Enum representing the color categories for animals.
 * This enum provides standardized color classifications for animals in the system.
 */
public enum Color {
    /** Solid black color. */
    BLACK,
    /** Solid white color. */
    WHITE,
    /** Various shades of brown. */
    BROWN,
    /** Golden or yellow tones. */
    GOLDEN,
    /** Gray or silver tones. */
    GRAY,
    /** Orange or ginger tones. */
    ORANGE,
    /** Multiple colors or patterns. */
    MIXED;

    /**
     * Converts a string representation of color to the corresponding Color enum value.
     * @param color The string representation of the color (case-insensitive)
     * @return The corresponding Color enum value, or null if the string doesn't match any color
     */
    public static Color fromString(String color) {
        try {
            return Color.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
