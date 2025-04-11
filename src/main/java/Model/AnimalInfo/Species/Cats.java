package Model.AnimalInfo.Species;

public enum Cats implements Species {
    DOMESTIC_SHORTHAIR, MAINE_COON, SIAMESE, PERSIAN, RAGDOLL, AMERICAN_SHORTHAIR, BENGAL, SPHYNX, SCOTTISH_FOLD,
    DEVON_REX, BOMBAY, ABYSSINIAN, BRITISH_SHORTHAIR, BURMESE, NORWEGIAN_FOREST_CAT, ORIENTAL_SHORTHAIR, RUSSIAN_BLUE,
    HIMALAYAN, EXOTIC_SHORTHAIR, SAVANNAH, OTHER;

    public static Cats fromString(String value) {
        for (Cats animal : Cats.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
