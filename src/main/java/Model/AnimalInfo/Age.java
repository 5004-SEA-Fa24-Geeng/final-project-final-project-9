package Model.AnimalInfo;

/**
 * Age range of animals.
 */
public enum Age {
    /** Age choices.*/
    BABY, YOUNG, ADULT, OLD;

    public static Age fromString(String age) {
        try {
            return Age.valueOf(age.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
