package Model.AnimalInfo.Species;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import Model.AnimalInfo.AnimalType;

/**
 * Tests for the Species interface and its static factory method.
 */
public class SpeciesTest {

    @Test
    public void testGetSpeciesByType_Dogs() {
        Species species = Species.getSpeciesByType(AnimalType.DOG, "LABRADOR");
        assertEquals(Dogs.LABRADOR, species);

        species = Species.getSpeciesByType(AnimalType.DOG, "GOLDEN_RETRIEVER");
        assertEquals(Dogs.GOLDEN_RETRIEVER, species);

        species = Species.getSpeciesByType(AnimalType.DOG, "OTHER");
        assertEquals(Dogs.OTHER, species);
    }

    @Test
    public void testGetSpeciesByType_Cats() {
        Species species = Species.getSpeciesByType(AnimalType.CAT, "PERSIAN");
        assertEquals(Cats.PERSIAN, species);

        species = Species.getSpeciesByType(AnimalType.CAT, "MAINE_COON");
        assertEquals(Cats.MAINE_COON, species);
    }

    @Test
    public void testGetSpeciesByType_Birds() {
        Species species = Species.getSpeciesByType(AnimalType.BIRD, "PARAKEET");
        assertEquals(Birds.PARAKEET, species);

        species = Species.getSpeciesByType(AnimalType.BIRD, "CANARY");
        assertEquals(Birds.CANARY, species);
    }

    @Test
    public void testGetSpeciesByType_Rabbits() {
        Species species = Species.getSpeciesByType(AnimalType.RABBIT, "HOLLAND_LOP");
        assertEquals(Rabbits.HOLLAND_LOP, species);

        species = Species.getSpeciesByType(AnimalType.RABBIT, "MINI_REX");
        assertEquals(Rabbits.MINI_REX, species);
    }

    @Test
    public void testGetSpeciesByType_Hamsters() {
        Species species = Species.getSpeciesByType(AnimalType.HAMSTER, "SYRIAN");
        assertEquals(Hamsters.SYRIAN, species);

        species = Species.getSpeciesByType(AnimalType.HAMSTER, "CAMPBELLS_DWARF");
        assertEquals(Hamsters.CAMPBELLS_DWARF, species);
    }

    @Test
    public void testGetSpeciesByType_OtherAnimalTypes() {
        Species species = Species.getSpeciesByType(AnimalType.DUCK, "MALLARD");
        assertEquals(Ducks.MALLARD, species);

        species = Species.getSpeciesByType(AnimalType.GOOSE, "CANADA");
        assertEquals(Geese.CANADA, species);

        species = Species.getSpeciesByType(AnimalType.HEDGEHOG, "AFRICAN_PYGMY");
        assertEquals(Hedgehogs.AFRICAN_PYGMY, species);
    }
} 