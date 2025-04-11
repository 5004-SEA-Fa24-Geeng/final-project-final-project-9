import Model.InputModel.AnimalFilter;
import Model.Animals.Animal;
import Model.Animals.IAnimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalFilterTest {

    private Set<IAnimal> animals;

    @BeforeEach
    void setUp() {
        animals = new HashSet<>();
        animals.add(new Animal(1, "cat", "American Shorthair", 1, 1, "Tabby", "Black", 1, new Integer[]{25, 3, 30, 15, 30}, "near Standard", "Seattle", false, "UD", "1", "1", 47.6062, -122.3321));
        animals.add(new Animal(2, "dog", "Poodle", 3, 1, "Solid", "White", 1, new Integer[]{25, 3, 30, 10, 30}, "", "Bellevue", false, "Clyde", "1", "1", 47.6101, -122.2015));
        animals.add(new Animal(3, "cat", "Ragdoll", 2, 1, "Solid", "White", 1, new Integer[]{25, 3, 30, 10, 30}, "", "Bellevue", false, "Downtown", "1", "1", 47.6101, -122.2015));
    }

    @Test
    void filterTest() {
        Stream<IAnimal> s;
        String filter = "cat,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Not Sure,Downtown,UD,within one month";
        AnimalFilter af = new AnimalFilter(animals);
        s = af.filter(filter);
        List<IAnimal> filteredList = s.collect(Collectors.toList());
        assertEquals(2, filteredList.size());
    }
}