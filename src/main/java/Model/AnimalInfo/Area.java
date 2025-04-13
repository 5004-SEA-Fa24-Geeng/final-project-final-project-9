package Model.AnimalInfo;

/**
 * Areas of the reported animals.
 */
public enum Area {
    SEATTLE, TACOMA, BELLEVUE, KENT, KIRKLAND, EVERETT, RENTON, REDMOND, LYNNWOOD, BOTHELL;

    public static Area fromString(String area) {
        try {
            return Area.valueOf(area.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
