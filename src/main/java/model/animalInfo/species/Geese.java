package model.animalInfo.species;

/**
 * Enum representing different goose breeds.
 * This enum provides a standardized list of goose breeds supported in the system.
 */
public enum Geese implements Species {
    /** Small, lightweight goose breed with distinctive knob on bill. */
    CHINESE,
    /** Large white goose breed commonly raised for meat. */
    EMBDEN,
    /** Large gray goose breed known for docile nature. */
    TOULOUSE,
    /** Large goose breed with distinctive knob on bill. */
    AFRICAN,
    /** Medium-sized goose breed with sex-linked color differences. */
    PILGRIM,
    /** Medium-sized goose breed with curly feathers. */
    SEBASTOPOL,
    /** Large goose breed with buff-colored plumage. */
    AMERICAN_BUFF,
    /** Small goose breed with tuft on head. */
    ROMAN,
    /** Small goose breed with distinctive knob on bill. */
    EGYPTIAN,
    /** Medium-sized goose breed with distinctive color pattern. */
    POMERANIAN,
    /** Small goose breed from Scotland. */
    SHETLAND,
    /** Wild goose species commonly seen in North America. */
    CANADA,
    /** Medium-sized goose breed with buff-colored plumage. */
    BUFF,
    /** Medium-sized goose breed from the southern United States. */
    COTTON_PATCH,
    /** Small goose breed with tuft on head. */
    TUFTED_ROMAN,
    /** Medium-sized goose breed from Germany. */
    STEINBACHER,
    /** Medium-sized goose breed from England. */
    WEST_OF_ENGLAND,
    /** Medium-sized goose breed with buff-colored plumage. */
    BRECON_BUFF,
    /** Wild goose species, ancestor of many domestic breeds. */
    GREYLAG,
    /** Large white goose breed, variant of Emden. */
    EMDEN_WHITE,
    /** Large white goose breed similar to Emden. */
    SNOW,
    /** Any other goose breed not listed above. */
    OTHER;

    /**
     * Converts a string representation of a goose breed to the corresponding Geese enum value.
     * @param value The string representation of the goose breed
     * @return The corresponding Geese enum value, or OTHER if the string doesn't match any breed
     */
    public static Geese fromString(String value) {
        for (Geese animal : Geese.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
