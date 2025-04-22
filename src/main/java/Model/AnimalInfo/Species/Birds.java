
package Model.AnimalInfo.Species;

/**
 * Enum representing different bird species commonly kept as pets.
 * This enum provides a standardized list of bird species supported in the system.
 */
public enum Birds implements Species {
    /** Small, colorful parrot species, also known as budgie. */
    BUDGERIGAR,
    /** Small crested parrot species with distinctive cheek patches. */
    COCKATIEL,
    /** Small songbird known for its melodious singing. */
    CANARY,
    /** Small parrot species known for strong pair bonds. */
    LOVEBIRD,
    /** Smallest parrot species. */
    PARROTLET,
    /** Small to medium-sized parrot species with long tail. */
    CONURE,
    /** Highly intelligent African parrot species. */
    AFRICAN_GREY_PARROT,
    /** Large crested parrot species. */
    COCKATOO,
    /** Small songbird species. */
    FINCH,
    /** Medium to large parrot species from the Americas. */
    AMAZON_PARROT,
    /** Large, colorful parrot species with long tail. */
    MACAW,
    /** Small to medium-sized parrot species with distinctive gray face. */
    QUAKER_PARROT,
    /** Small African parrot species with distinctive head pattern. */
    SENEGAL_PARROT,
    /** Small, colorful parrot species from South America. */
    CAIQUE,
    /** Small to medium-sized parrot species with long tail. */
    PARAKEET,
    /** Medium-sized bird species known for gentle nature. */
    DOVE,
    /** Small dove species with distinctive diamond pattern. */
    DIAMOND_DOVE,
    /** Medium-sized parrot species with quiet nature. */
    PIONUS,
    /** Small Australian parrot species with gentle nature. */
    BOURKES_PARAKEET,
    /** Medium to large parrot species with distinctive sexual dimorphism. */
    ECLECTUS,
    /** Any other bird species not listed above. */
    OTHER;

    /**
     * Converts a string representation of a bird species to the corresponding Birds enum value.
     * @param value The string representation of the bird species
     * @return The corresponding Birds enum value, or OTHER if the string doesn't match any species
     */
    public static Birds fromString(String value) {
        for (Birds animal : Birds.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
