package Model.AnimalInfo.Species;

/**
 * Enum representing different rabbit breeds.
 * This enum provides a standardized list of rabbit breeds supported in the system.
 */
public enum Rabbits implements Species {
    /** Small rabbit breed with lop ears. */
    HOLLAND_LOP,
    /** Small rabbit breed with plush fur. */
    MINI_REX,
    /** Small rabbit breed, one of the smallest breeds. */
    NETHERLAND_DWARF,
    /** Small rabbit breed with mane-like fur around head. */
    LIONHEAD,
    /** Large rabbit breed, one of the largest breeds. */
    FLEMISH_GIANT,
    /** Small to medium-sized rabbit breed with lop ears. */
    MINI_LOP,
    /** Medium to large rabbit breed with very long ears. */
    ENGLISH_LOP,
    /** Medium-sized rabbit breed with distinctive color pattern. */
    DUTCH,
    /** Large rabbit breed with lop ears. */
    FRENCH_LOP,
    /** Large white rabbit breed commonly raised for meat. */
    NEW_ZEALAND_WHITE,
    /** Medium-sized rabbit breed with distinctive color pattern. */
    CALIFORNIAN,
    /** Small rabbit breed with compact body. */
    POLISH,
    /** Small rabbit breed with wooly fur and lop ears. */
    AMERICAN_FUZZY_LOP,
    /** Small rabbit breed with wooly fur. */
    JERSEY_WOOLY,
    /** Medium-sized rabbit breed with color points. */
    HIMALAYAN,
    /** Medium-sized rabbit breed with long, wooly fur. */
    ENGLISH_ANGORA,
    /** Large rabbit breed similar to Flemish Giant. */
    CONTINENTAL_GIANT,
    /** Medium-sized rabbit breed with rich brown coat. */
    HAVANA,
    /** Medium-sized rabbit breed with plush fur. */
    REX,
    /** Medium-sized rabbit breed with distinctive color pattern. */
    HARLEQUIN,
    /** Any other rabbit breed not listed above. */
    OTHER;

    /**
     * Converts a string representation of a rabbit breed to the corresponding Rabbits enum value.
     * @param value The string representation of the rabbit breed
     * @return The corresponding Rabbits enum value, or OTHER if the string doesn't match any breed
     */
    public static Rabbits fromString(String value) {
        for (Rabbits animal : Rabbits.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
