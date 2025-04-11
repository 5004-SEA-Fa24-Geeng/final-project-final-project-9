package Model.AnimalInfo;

/**
 * Dark and light colors of animals.
 */
public enum Color {
    /** Colors - Dark or Light.*/
    BLACK, WHITE, BROWN, GOLDEN, GRAY, ORANGE, MIXED;

    public static Color fromString(String color) {
        try {
            return Color.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
