package Model.AnimalInfo;

/**
 * Gender of animals.
 */
public enum Gender {
    /** Gender. */
    FEMALE, MALE, UNKNOWN;

    public static Gender fromString(String gender) {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}