package Model.AnimalInfo;

public enum AnimalSpecies {
    // Dog breeds
    HUSKY, LABRADOR, GOLDEN_RETRIEVER, GERMAN_SHEPHERD, POODLE, BULLDOG, BEAGLE, CHIHUAHUA,
    // Cat breeds
    PERSIAN, SIAMESE, MAINE_COON, RAGDOLL, BRITISH_SHORTHAIR, SPHYNX, BENGAL, RUSSIAN_BLUE,
    // Other animals
    UNKNOWN;

    public static AnimalSpecies fromString(String species) {
        try {
            return AnimalSpecies.valueOf(species.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
} 