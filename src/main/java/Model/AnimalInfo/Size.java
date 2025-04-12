package Model.AnimalInfo;

/**
 * Size of animals.
 */
public enum Size{
    MICRO(0), SMALL(1), MEDIUM(2), LARGE(3);

    private final int size;

    Size(int size) {
        this.size = size;
    }

    public static Size fromString(String size) {
        try {
            return Size.valueOf(size.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}