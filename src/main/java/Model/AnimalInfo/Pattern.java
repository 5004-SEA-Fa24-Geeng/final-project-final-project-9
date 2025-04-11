package Model.AnimalInfo;

/**
 * Patterns of animals.
 */
public enum Pattern {
    /** patterns. */
    SOLID, SPOTTED, STRIPED, BRINDLE, MERLE, TUXEDO;

    public static Pattern fromString(String pattern) {
        try {
            return Pattern.valueOf(pattern.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
