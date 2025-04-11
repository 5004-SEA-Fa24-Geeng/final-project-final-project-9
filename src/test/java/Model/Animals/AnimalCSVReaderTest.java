package Model.Animals;

import Model.output.AnimalCSVReader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class AnimalCSVReaderTest {
    @Test
    public void testReadAnimals() {
        String testFilePath = "src/test/resources/test_animals.csv";
        AnimalCSVReader reader = new AnimalCSVReader(testFilePath);
        
        List<IAnimal> animals = reader.readAnimals();
        
        assertNotNull(animals);
        assertFalse(animals.isEmpty());
        
        // 验证第一个动物的属性
        IAnimal firstAnimal = animals.get(0);
        assertNotNull(firstAnimal.getAnimalType());
        assertNotNull(firstAnimal.getSpecies());
        assertNotNull(firstAnimal.getAnimalSize());
        assertNotNull(firstAnimal.getGender());
        assertNotNull(firstAnimal.getPattern());
        assertNotNull(firstAnimal.getColor());
        assertNotNull(firstAnimal.getAge());
        assertNotNull(firstAnimal.getAddress());
        assertNotNull(firstAnimal.getArea());
        assertNotNull(firstAnimal.getTime());
        assertNotNull(firstAnimal.getSeenDate());
        assertNotNull(firstAnimal.getDescription());
        assertNotNull(firstAnimal.getLocDesc());
        assertNotNull(firstAnimal.getNumber());
    }
} 