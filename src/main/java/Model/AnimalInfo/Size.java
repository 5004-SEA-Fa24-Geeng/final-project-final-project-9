package Model.AnimalInfo;

/**
 * Size of animals.
 */
public enum Size {
    MICRO, SMALL, MEDIUM, LARGE;

    public static Size fromString(String size) {
        try {
            return Size.valueOf(size.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
