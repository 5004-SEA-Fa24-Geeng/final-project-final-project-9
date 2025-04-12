package Model.AnimalInfo;

/**
 * Areas of the reported animals.
 */
public enum Area {
    DOWNTOWN, CLYDE, HILL, MEDINA, LAKE_HILLS, CROSSROADS, BRIDLE_TRAILS;

    public static Area fromString(String area) {
        try {
            return Area.valueOf(area.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
