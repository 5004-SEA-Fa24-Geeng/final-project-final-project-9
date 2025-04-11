package Model.AnimalInfo;

/**
 * Areas of the reported animals.
 */
public enum Area {
    /** Cities of choice. */
    SEATTLE, BELLEVUE, REDMOND, KIRKLAND, EVERETTE,
    /** continued.*/
    TACOMA, RENTON, KENT, LYNNWOOD, BOTHELL;

    public static Area fromString(String value) {
        for (Area area: Area.values()) {
            if (area.name().equalsIgnoreCase(value)) {
                return area;
            }
        }
        return null;
    }
}
