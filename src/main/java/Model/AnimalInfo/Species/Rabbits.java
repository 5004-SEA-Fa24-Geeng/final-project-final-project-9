package Model.AnimalInfo.Species;

public enum Rabbits implements Species {
    HOLLAND_LOP, MINI_REX, NETHERLAND_DWARF, LIONHEAD, FLEMISH_GIANT, MINI_LOP, ENGLISH_LOP, DUTCH,
    FRENCH_LOP, NEW_ZEALAND_WHITE, CALIFORNIAN, POLISH, AMERICAN_FUZZY_LOP, JERSEY_WOOLY, HIMALAYAN,
    ENGLISH_ANGORA, CONTINENTAL_GIANT, HAVANA, REX, HARLEQUIN, OTHER;

    public static Rabbits fromString(String value) {
        for (Rabbits animal : Rabbits.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}