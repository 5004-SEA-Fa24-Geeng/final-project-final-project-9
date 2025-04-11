package Model.AnimalInfo;

/**
 * Dark and light colors of animals.
 */
public enum Color {
    /** Colors - Dark or Light.*/
    DARK, LIGHT, MIX;

    public static Color fromString(String value) {
        for (Color color : Color.values()) {
            if (color.name().equalsIgnoreCase(value)) {
                return color;
            }
        }
        return null;
    }
}
