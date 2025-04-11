package Model.AnimalInfo;

/**
 * Size of animals.
 */
public enum Size {
    /** Size of animals.*/
    SMALL, MEDIUM, BIG;

    public static Size fromString(String value) {
        for (Size size : Size.values()) {
            if (size.name().equalsIgnoreCase(value)) {
                return size;
            }
        }
        return null;
    }
}
