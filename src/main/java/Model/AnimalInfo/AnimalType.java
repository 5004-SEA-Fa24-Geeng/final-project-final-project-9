package Model.AnimalInfo;

/**
 * Animal Types to select.
 */
public enum AnimalType {
    /** Animal types. */
    DOG, CAT, BIRD, RABBIT, HAMSTER, HEDGEHOG, DUCK, GOOSE;

    public static AnimalType fromString(String value) {
        for (AnimalType type : AnimalType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
