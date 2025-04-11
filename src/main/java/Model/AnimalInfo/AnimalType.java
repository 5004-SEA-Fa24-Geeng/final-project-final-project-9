package Model.AnimalInfo;

/**
 * Animal Types to select.
 */
public enum AnimalType {
    /** Animal types. */
    DOG, CAT, RABBIT, BIRD, HAMSTER, DUCK, HEDGEHOG;

    public static AnimalType fromString(String type) {
        try {
            return AnimalType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
