package Model;

/**
 * Animal data columns for filter and sort.
 */
public enum AnimalData {
    TYPE, SPECIES, SIZE, GENDER, PATTERN, COLOR, AGE, SEENDATE, SEENTIME,
    ADDRESS, AREA, LOCDESC, DESCRIPTION, NUMBER, IMAGE;

    public static AnimalData fromString(String str) {
        for (AnimalData data : AnimalData.values()) {
            if (data.name().equals(str)) {
                return data;
            }
        }
        return null;
    }
}
