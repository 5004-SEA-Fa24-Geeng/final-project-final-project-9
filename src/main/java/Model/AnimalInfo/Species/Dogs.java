package Model.AnimalInfo.Species;

/**
 * Enum representing different dog breeds.
 * This enum provides a standardized list of dog breeds supported in the system.
 */
public enum Dogs implements Species {
    /** Popular family-friendly breed known for intelligence and friendly nature */
    LABRADOR,
    /** Intelligent, friendly breed with golden coat */
    GOLDEN_RETRIEVER,
    /** Intelligent working breed often used in police and military */
    GERMAN_SHEPHERD,
    /** Small to medium-sized scent hound breed */
    BEAGLE,
    /** Medium-sized, muscular breed with distinctive face */
    BULLDOG,
    /** Small companion breed with bat-like ears */
    FRENCH_BULLDOG,
    /** Intelligent breed available in three sizes (toy, miniature, standard) */
    POODLE,
    /** Small companion breed with long, silky coat */
    YORKSHIRE_TERRIER,
    /** Medium to large muscular breed with square head */
    BOXER,
    /** Small breed with long body and short legs */
    DACHSHUND,
    /** Small companion breed with long, flowing coat */
    SHIH_TZU,
    /** Medium-sized working breed with thick double coat */
    SIBERIAN_HUSKY,
    /** Small companion breed, one of the smallest dog breeds */
    CHIHUAHUA,
    /** Giant breed known for its size and gentle nature */
    GREAT_DANE,
    /** Medium-sized herding breed with high energy */
    AUSTRALIAN_SHEPHERD,
    /** Small spaniel breed known for its gentle nature */
    CAVALIER_KING_CHARLES_SPANIEL,
    /** Small herding breed with short legs */
    PEMBROKE_WELSH_CORGI,
    /** Highly intelligent herding breed */
    BORDER_COLLIE,
    /** Small companion breed with distinctive wrinkled face */
    PUG,
    /** Large working breed with thick double coat */
    ALASKAN_MALAMUTE,
    /** Medium-sized Japanese breed with fox-like appearance */
    SHIBA_INU,
    /** Any other dog breed not listed above */
    OTHER;

    /**
     * Converts a string representation of a dog breed to the corresponding Dogs enum value.
     * @param value The string representation of the dog breed
     * @return The corresponding Dogs enum value, or OTHER if the string doesn't match any breed
     */
    public static Dogs fromString(String value) {
        for (Dogs animal : Dogs.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}