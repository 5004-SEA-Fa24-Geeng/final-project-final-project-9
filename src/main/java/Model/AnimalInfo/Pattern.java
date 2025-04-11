package Model.AnimalInfo;

/**
 * Patterns of animals.
 */
public enum Pattern {
    /** patterns. */
    SOLID, TABBY;

    public static Pattern fromString(String value) {
        for (Pattern pattern : Pattern.values()) {
            if (pattern.name().equalsIgnoreCase(value)) {
                return pattern;
            }
        }
        return null;
    }
}
