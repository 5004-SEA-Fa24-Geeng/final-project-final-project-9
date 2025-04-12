package Model.AnimalInfo.Species;

public enum Dogs implements Species {
    /** Common pet dog breeds in America. */
    LABRADOR, GOLDEN_RETRIEVER, GERMAN_SHEPHERD, BEAGLE, BULLDOG, FRENCH_BULLDOG, POODLE, YORKSHIRE_TERRIER,
    BOXER, DACHSHUND, SHIH_TZU, SIBERIAN_HUSKY, CHIHUAHUA, GREAT_DANE, AUSTRALIAN_SHEPHERD, CAVALIER_KING_CHARLES_SPANIEL,
    PEMBROKE_WELSH_CORGI, BORDER_COLLIE, PUG, ALASKAN_MALAMUTE, SHIBA_INU, OTHER;

    public static Dogs fromString(String value) {
        for (Dogs animal : Dogs.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}