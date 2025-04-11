package Model.AnimalInfo.Species;

public enum Ducks {
    CALL, PEKIN, KHAKI_CAMPBELL, INDIAN_RUNNER, WELSH_HARLEQUIN, CAYUGA, MUSCOVY, MALLARD, SWEDISH_BLUE, ROUEN,
    ANCONA, SILVER_APPLEYARD, BUFF_ORPINGTON, BLUE_SWEDISH, MAGPIE, SAXONY, CRESTED, WHITE_CRESTED, BLACK_EAST_INDIAN,
    AYLESBURY, OTHER;

    public static Ducks fromString(String value) {
        for (Ducks animal : Ducks.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
