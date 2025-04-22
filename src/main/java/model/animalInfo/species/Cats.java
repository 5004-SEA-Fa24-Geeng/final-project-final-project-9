
package model.animalInfo.species;

/**
 * Enum representing different cat breeds.
 * This enum provides a standardized list of cat breeds supported in the system.
 */
public enum Cats implements Species {
    /** Common domestic short-haired cat. */
    DOMESTIC_SHORTHAIR,
    /** Large, long-haired breed from Maine. */
    MAINE_COON,
    /** Slender, short-haired breed with distinctive color points. */
    SIAMESE,
    /** Long-haired breed with a flat face. */
    PERSIAN,
    /** Large, long-haired breed known for going limp when picked up. */
    RAGDOLL,
    /** Medium-sized, short-haired breed. */
    AMERICAN_SHORTHAIR,
    /** Spotted coat breed resembling wild cats. */
    BENGAL,
    /** Hairless breed. */
    SPHYNX,
    /** Breed with folded ears. */
    SCOTTISH_FOLD,
    /** Short-haired breed with large ears. */
    DEVON_REX,
    /** Black, short-haired breed resembling a panther. */
    BOMBAY,
    /** Short-haired breed with ticked coat. */
    ABYSSINIAN,
    /** Stocky, short-haired breed. */
    BRITISH_SHORTHAIR,
    /** Medium-sized, short-haired breed. */
    BURMESE,
    /** Large, long-haired breed from Norway. */
    NORWEGIAN_FOREST_CAT,
    /** Slender, short-haired breed. */
    ORIENTAL_SHORTHAIR,
    /** Short-haired breed with blue-gray coat. */
    RUSSIAN_BLUE,
    /** Long-haired breed with color points. */
    HIMALAYAN,
    /** Short-haired breed with Persian-like features. */
    EXOTIC_SHORTHAIR,
    /** Hybrid breed with wild serval ancestry. */
    SAVANNAH,
    /** Any other cat breed not listed above. */
    OTHER;

    /**
     * Converts a string representation of a cat breed to the corresponding Cats enum value.
     * @param value The string representation of the cat breed
     * @return The corresponding Cats enum value, or OTHER if the string doesn't match any breed
     */
    public static Cats fromString(String value) {
        for (Cats animal : Cats.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
