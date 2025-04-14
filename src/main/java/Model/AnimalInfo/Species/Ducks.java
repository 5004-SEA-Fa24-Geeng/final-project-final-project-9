package Model.AnimalInfo.Species;

/**
 * Enum representing different duck breeds.
 * This enum provides a standardized list of duck breeds supported in the system.
 */
public enum Ducks implements Species {
    /** Small bantam duck breed with distinctive call */
    CALL,
    /** Large white duck breed commonly raised for meat */
    PEKIN,
    /** Medium-sized duck breed known for high egg production */
    KHAKI_CAMPBELL,
    /** Tall, upright duck breed known for running rather than waddling */
    INDIAN_RUNNER,
    /** Medium-sized duck breed with harlequin pattern */
    WELSH_HARLEQUIN,
    /** Medium-sized duck breed with iridescent black plumage */
    CAYUGA,
    /** Large duck breed with distinctive red facial caruncles */
    MUSCOVY,
    /** Wild duck species, ancestor of many domestic breeds */
    MALLARD,
    /** Medium-sized duck breed with blue-gray plumage */
    SWEDISH_BLUE,
    /** Large duck breed similar to Mallard but larger */
    ROUEN,
    /** Medium-sized duck breed with mottled plumage */
    ANCONA,
    /** Large duck breed with silver and white plumage */
    SILVER_APPLEYARD,
    /** Large duck breed with buff-colored plumage */
    BUFF_ORPINGTON,
    /** Medium-sized duck breed with blue-gray plumage */
    BLUE_SWEDISH,
    /** Medium-sized duck breed with black and white plumage */
    MAGPIE,
    /** Medium-sized duck breed with distinctive color pattern */
    SAXONY,
    /** Medium-sized duck breed with crest on head */
    CRESTED,
    /** Medium-sized duck breed with white crest */
    WHITE_CRESTED,
    /** Small duck breed with black plumage */
    BLACK_EAST_INDIAN,
    /** Large white duck breed traditionally raised for meat */
    AYLESBURY,
    /** Any other duck breed not listed above */
    OTHER;

    /**
     * Converts a string representation of a duck breed to the corresponding Ducks enum value.
     * @param value The string representation of the duck breed
     * @return The corresponding Ducks enum value, or OTHER if the string doesn't match any breed
     */
    public static Ducks fromString(String value) {
        for (Ducks animal : Ducks.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}