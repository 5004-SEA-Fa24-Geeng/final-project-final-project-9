package model.animalInfo;

/**
 * Enum representing the types of animals supported in the system.
 * This enum provides a standardized list of animal types that can be reported.
 */
public enum AnimalType {
    /** Domestic dog. */
    DOG,
    /** Domestic cat. */
    CAT,
    /** Domestic rabbit. */
    RABBIT,
    /** Various bird species. */
    BIRD,
    /** Domestic hamster. */
    HAMSTER,
    /** Domestic duck. */
    DUCK,
    /** Domestic hedgehog. */
    HEDGEHOG,
    /** Domestic goose. */
    GOOSE;

    /**
     * Converts a string representation of animal type to the corresponding AnimalType enum value.
     * @param type The string representation of the animal type (case-insensitive)
     * @return The corresponding AnimalType enum value, or null if the string doesn't match any type
     */
    public static AnimalType fromString(String type) {
        try {
            return AnimalType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
