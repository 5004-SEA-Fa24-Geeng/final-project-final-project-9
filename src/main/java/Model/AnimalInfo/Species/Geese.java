package Model.AnimalInfo.Species;

public enum Geese {
    CHINESE, EMBDEN, TOULOUSE, AFRICAN, PILGRIM, SEBASTOPOL, AMERICAN_BUFF, ROMAN, EGYPTIAN, POMERANIAN, SHETLAND,
    CANADA, BUFF, COTTON_PATCH, TUFTED_ROMAN, STEINBACHER, WEST_OF_ENGLAND, BRECON_BUFF, GREYLAG,
    EMDEN_WHITE, SNOW, OTHER;

    public static Geese fromString(String value) {
        for (Geese animal : Geese.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
