package Model.AnimalInfo.Species;

public enum Hamsters implements Species {
    CAMPBELLS_DWARF, CHINESE_DWARF, ROBOROVSKI_DWARF, WINTER_WHITE_DWARF, SYRIAN, TEDDY_BEAR, BLACK_BEAR, EUROPEAN,
    CHINESE_STRIPED, CHINESE_WHITE, DJUNGARIAN, FANCY, SHORT_HAIRED, LONG_HAIRED, SATIN, REX,
    BLACK_BELLIED, GREY_DWARF, PUDDING, RUSSIAN_CAMPBELL, OTHER;

    public static Hamsters fromString(String value) {
        for (Hamsters animal : Hamsters.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}