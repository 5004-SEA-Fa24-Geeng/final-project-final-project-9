package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Model.AnimalOutputGenerator;
import Model.FilterCriteria;
import Model.IOutputGenerator;
import Model.OutputFormat;
import Model.PostedAnimal;
import Model.TestDataGenerator;

public class AnimalController implements IController {
    // Store the list of animals
    private List<PostedAnimal> animalList;
    private IOutputGenerator outputGenerator;

    public AnimalController() {
        this.animalList = new ArrayList<>();
        this.outputGenerator = new AnimalOutputGenerator();
    }

    @Override
    public List<PostedAnimal> filter(List<FilterCriteria> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return new ArrayList<>(animalList);
        }

        return animalList.stream()
            .filter(animal -> criteria.stream().allMatch(criterion -> {
                switch (criterion.getOperation()) {
                    case FILTER_BY_TYPE:
                        return animal.getAnimalType().equals(criterion.getValue());
                    case FILTER_BY_LOCATION:
                        return animal.getLocation().equals(criterion.getValue());
                    case FILTER_BY_SIZE:
                        return String.valueOf(animal.getAnimalSize()).equals(criterion.getValue());
                    case FILTER_BY_GENDER:
                        return String.valueOf(animal.getGender()).equals(criterion.getValue());
                    case FILTER_BY_AGE:
                        return String.valueOf(animal.getAnimalAge()).equals(criterion.getValue());
                    default:
                        return true;
                }
            }))
            .collect(Collectors.toList());
    }

    @Override
    public List<PostedAnimal> search(List<FilterCriteria> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return new ArrayList<>(animalList);
        }

        return animalList.stream()
            .filter(animal -> criteria.stream().anyMatch(criterion -> {
                switch (criterion.getOperation()) {
                    case SEARCH_BY_KEYWORD:
                        return animal.getDescription().toLowerCase().contains(criterion.getValue().toLowerCase()) ||
                               animal.getAnimalType().toLowerCase().contains(criterion.getValue().toLowerCase()) ||
                               animal.getSpecies().toLowerCase().contains(criterion.getValue().toLowerCase());
                    case SEARCH_BY_COLOR:
                        return animal.getAnimalColor().toLowerCase().contains(criterion.getValue().toLowerCase());
                    case SEARCH_BY_PATTERN:
                        return animal.getPattern().toLowerCase().contains(criterion.getValue().toLowerCase());
                    case SEARCH_BY_SPECIES:
                        return animal.getSpecies().toLowerCase().contains(criterion.getValue().toLowerCase());
                    default:
                        return false;
                }
            }))
            .collect(Collectors.toList());
    }

    @Override
    public List<PostedAnimal> sort(List<FilterCriteria> criteria) {
        if (criteria == null || criteria.isEmpty()) {
            return new ArrayList<>(animalList);
        }

        List<PostedAnimal> sortedList = new ArrayList<>(animalList);
        for (FilterCriteria criterion : criteria) {
            switch (criterion.getOperation()) {
                case SORT_BY_TIME_ASC:
                    sortedList.sort((a, b) -> a.getWitnessedTime()[0].compareTo(b.getWitnessedTime()[0]));
                    break;
                case SORT_BY_TIME_DESC:
                    sortedList.sort((a, b) -> b.getWitnessedTime()[0].compareTo(a.getWitnessedTime()[0]));
                    break;
                case SORT_BY_LOCATION_ASC:
                    sortedList.sort((a, b) -> a.getLocation().compareTo(b.getLocation()));
                    break;
                case SORT_BY_LOCATION_DESC:
                    sortedList.sort((a, b) -> b.getLocation().compareTo(a.getLocation()));
                    break;
                case SORT_BY_SIZE_ASC:
                    sortedList.sort((a, b) -> Integer.compare(a.getAnimalSize(), b.getAnimalSize()));
                    break;
                case SORT_BY_SIZE_DESC:
                    sortedList.sort((a, b) -> Integer.compare(b.getAnimalSize(), a.getAnimalSize()));
                    break;
            }
        }
        return sortedList;
    }

    @Override
    public void outputGen(List<PostedAnimal> listAnimal) {
        // Default to pretty format if no format is specified
        outputGenerator.generateOutput(listAnimal, OutputFormat.PRETTY, System.out);
    }

    // Helper method to add an animal to the list
    public void addAnimal(PostedAnimal animal) {
        animalList.add(animal);
    }

    // Helper method to get all animals
    public List<PostedAnimal> getAllAnimals() {
        return new ArrayList<>(animalList);
    }

    // Helper method to set output format
    public void setOutputFormat(OutputFormat format) {
        // This method can be used to change the output format
        // For now, we'll just use the default pretty format
    }

    // Find nearby animals within a certain radius (in kilometers)
    public List<PostedAnimal> findNearbyAnimals(double latitude, double longitude, double radiusKm) {
        return animalList.stream()
            .filter(animal -> {
                double distance = TestDataGenerator.calculateDistance(
                    latitude, longitude,
                    animal.getLatitude(), animal.getLongitude()
                );
                return distance <= radiusKm;
            })
            .collect(Collectors.toList());
    }

    // Calculate distance between two animals
    public double calculateDistance(PostedAnimal animal1, PostedAnimal animal2) {
        return TestDataGenerator.calculateDistance(
            animal1.getLatitude(), animal1.getLongitude(),
            animal2.getLatitude(), animal2.getLongitude()
        );
    }

    // Generate test data
    public void generateTestData(int count) {
        List<PostedAnimal> testData = TestDataGenerator.generateTestData(count);
        animalList.addAll(testData);
    }
} 