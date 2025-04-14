package Model.AnimalInfo.Species;

/**
 * Enum representing different hamster species and varieties.
 * This enum provides a standardized list of hamster types supported in the system.
 */
public enum Hamsters implements Species {
    /** Small dwarf hamster species from Russia and China */
    CAMPBELLS_DWARF,
    /** Small hamster species with mouse-like appearance */
    CHINESE_DWARF,
    /** Smallest hamster species, known for speed and activity */
    ROBOROVSKI_DWARF,
    /** Small dwarf hamster species that changes color in winter */
    WINTER_WHITE_DWARF,
    /** Large hamster species, also known as golden hamster */
    SYRIAN,
    /** Long-haired variety of Syrian hamster */
    TEDDY_BEAR,
    /** Black variety of Syrian hamster */
    BLACK_BEAR,
    /** Large wild hamster species from Europe */
    EUROPEAN,
    /** Small hamster species with distinctive dorsal stripe */
    CHINESE_STRIPED,
    /** White variety of Chinese hamster */
    CHINESE_WHITE,
    /** Small dwarf hamster species from Siberia */
    DJUNGARIAN,
    /** General term for domesticated hamsters */
    FANCY,
    /** Short-haired variety of Syrian hamster */
    SHORT_HAIRED,
    /** Long-haired variety of Syrian hamster */
    LONG_HAIRED,
    /** Variety with shiny, satin-like coat */
    SATIN,
    /** Variety with curly fur */
    REX,
    /** Variety with black belly */
    BLACK_BELLIED,
    /** Small dwarf hamster species with gray coat */
    GREY_DWARF,
    /** Color variety of dwarf hamster */
    PUDDING,
    /** Alternative name for Campbell's dwarf hamster */
    RUSSIAN_CAMPBELL,
    /** Any other hamster type not listed above */
    OTHER;

    /**
     * Converts a string representation of a hamster type to the corresponding Hamsters enum value.
     * @param value The string representation of the hamster type
     * @return The corresponding Hamsters enum value, or OTHER if the string doesn't match any type
     */
    public static Hamsters fromString(String value) {
        for (Hamsters animal : Hamsters.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}