package Model.AnimalInfo.Species;

public enum Hedgehogs implements Species {
    AFRICAN_PYGMY, EUROPEAN, LONG_EARED, EGYPTIAN_LONG_EARED, INDIAN_LONG_EARED, DESERT, FOUR_TOED,
    DAURIAN, BRANDTS, HUGHS, OTHER;

    public static Hedgehogs fromString(String value) {
        for (Hedgehogs animal : Hedgehogs.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }

}
