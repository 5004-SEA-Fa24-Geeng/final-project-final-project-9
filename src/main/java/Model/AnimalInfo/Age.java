package Model.AnimalInfo;

/**
 * Age range of animals.
 */
public enum Age {
    /** Age choices.*/
    BABY, YOUNG, OLD, UNCLEAR;

    public static Age fromString(String value) {
        for (Age age : Age.values()) {
            if (age.name().equalsIgnoreCase(value)) {
                return age;
            }
        }
        return null;
    }
}
