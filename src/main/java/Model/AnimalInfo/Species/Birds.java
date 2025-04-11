package Model.AnimalInfo.Species;

public enum Birds {
    BUDGERIGAR, COCKATIEL, CANARY, LOVEBIRD, PARROTLET, CONURE, AFRICAN_GREY_PARROT, COCKATOO, FINCH, AMAZON_PARROT,
    MACAW, QUAKER_PARROT, SENEGAL_PARROT, CAIQUE, PARAKEET, DOVE, DIAMOND_DOVE, PIONUS, BOURKES_PARAKEET,
    ECLECTUS, OTHER;

    public static Birds fromString(String value) {
        for (Birds animal : Birds.values()) {
            if (animal.name().equals(value)) {
                return animal;
            }
        }
        return OTHER;
    }
}
