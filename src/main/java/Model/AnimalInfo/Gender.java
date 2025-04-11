package Model.AnimalInfo;

/**
 * Gender of animals.
 */
public enum Gender {
    /** Gender. */
    FEMALE, MALE, UNCLEAR;

    public static Gender fromString(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        return null;
    }
}
