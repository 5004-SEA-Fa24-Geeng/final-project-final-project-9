package Model.AnimalInfo.Species;

/**
 * Enum representing different hedgehog species.
 * This enum provides a standardized list of hedgehog species supported in the system.
 */
public enum Hedgehogs implements Species {
    /** Small hedgehog species commonly kept as pets */
    AFRICAN_PYGMY,
    /** Large hedgehog species native to Europe */
    EUROPEAN,
    /** Hedgehog species with distinctive long ears */
    LONG_EARED,
    /** Hedgehog species with long ears native to Egypt */
    EGYPTIAN_LONG_EARED,
    /** Hedgehog species with long ears native to India */
    INDIAN_LONG_EARED,
    /** Hedgehog species adapted to desert environments */
    DESERT,
    /** Small hedgehog species with four toes on hind feet */
    FOUR_TOED,
    /** Hedgehog species native to Russia and China */
    DAURIAN,
    /** Hedgehog species native to Central Asia */
    BRANDTS,
    /** Hedgehog species native to China */
    HUGHS,
    /** Any other hedgehog species not listed above */
    OTHER;

    /**
     * Converts a string representation of a hedgehog species to the corresponding Hedgehogs enum value.
     * @param value The string representation of the hedgehog species
     * @return The corresponding Hedgehogs enum value, or OTHER if the string doesn't match any species
     */
    public static Hedgehogs fromString(String value) {
        for (Hedgehogs animal : Hedgehogs.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}