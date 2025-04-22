
package Model.Animals;

/**
 * Animal data columns for filter and sort.
 */
public enum AnimalData {
    /** Fields of animal information. */
    TYPE, SPECIES, SIZE, GENDER, PATTERN, COLOR, AGE, SEENDATE, SEENTIME,

    /** Continued. */
    ADDRESS, AREA, LOCDESC, DESCRIPTION, NUMBER, IMAGE;


    /**
     * Get the field as enum type.
     * @param str field string
     * @return enum type of the field
     */
    public static AnimalData fromString(String str) {
        for (AnimalData data : AnimalData.values()) {
            if (data.name().equals(str)) {
                return data;
            }
        }
        return null;
    }
}
