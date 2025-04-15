# Dog Station UML Diagram

```mermaid
classDiagram
    direction LR
    %% Interfaces
    class IAnimal {
        <<interface>>
        +String IMG_SRC
        +String getAnimalType()
        +String getSpecies()
        +String getAnimalSize()
        +String getGender()
        +String getPattern()
        +String getColor()
        +String getAge()
        +String getSeenDate()
        +String getTime()
        +String getArea()
        +String getAddress()
        +String getLocDesc()
        +String getDescription()
        +String getNumber()
        +String getImage()
    }

    class IAnimalList {
        <<interface>>
        +String DATABASE
        +List~IAnimal~ getAnimals()
        +int count()
        +int getMaxNumber()
        +void addNewAnimal(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
        +void write()
    }

    class IAnimalFilter {
        <<interface>>
        +List~IAnimal~ filterByType(String, List~IAnimal~)
        +List~IAnimal~ filterBySpecies(String, List~IAnimal~)
        +List~IAnimal~ filterBySize(String, List~IAnimal~)
        +List~IAnimal~ filterByGender(String, List~IAnimal~)
        +List~IAnimal~ filterByPattern(String, List~IAnimal~)
        +List~IAnimal~ filterByColor(String, List~IAnimal~)
        +List~IAnimal~ filterByAge(String, List~IAnimal~)
        +List~IAnimal~ filterByArea(String, List~IAnimal~)
    }

    class IController {
        <<interface>>
        +void performOperation(Operation, String)
        +void addAnimal(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
        +void filterAnimalsByType(String)
        +void filterAnimalsBySpecies(String)
        +void filterAnimalsBySize(String)
        +void filterAnimalsByGender(String)
        +void filterAnimalsByPattern(String)
        +void filterAnimalsByColor(String)
        +void filterAnimalsByAge(String)
        +void filterAnimalsByArea(String)
        +void sortAnimalsByDateAsc()
        +void sortAnimalsByDateDesc()
        +void generateOutputFile(String, OutputFormat)
    }

    class IView {
        <<interface>>
        +void initialize()
        +void displayAnimals(List~IAnimal~)
        +void showError(String)
        +void updateFilters(List~String~, List~String~, List~String~, List~String~, List~String~, List~String~, List~String~, List~String~)
    }

    class IOutputGenerator {
        <<interface>>
        +void generateOutput(List~IAnimal~, String, OutputFormat)
    }

    %% Classes
    class Animal {
        -String number
        -String type
        -String species
        -String size
        -String gender
        -String pattern
        -String color
        -String age
        -String seenDate
        -String time
        -String area
        -String address
        -String locDesc
        -String description
        -String image
        +Animal(String, String, String, String, String, String, String, String, String, String, String, String, String, String)
    }

    class AnimalData {
        -List~IAnimal~ animals
        +AnimalData()
        +void readData()
    }

    class AnimalList {
        -List~IAnimal~ animals
        -int maxNumber
        +AnimalList()
        +void loadAnimals()
    }

    class AnimalFilter {
        +AnimalFilter()
    }

    class Sorts {
        +List~IAnimal~ sortByDateAsc(List~IAnimal~)
        +List~IAnimal~ sortByDateDesc(List~IAnimal~)
    }

    class Controller {
        -IAnimalList animalList
        -IAnimalFilter animalFilter
        -Sorts sorts
        -IView view
        -IOutputGenerator outputGenerator
        -List~IAnimal~ filteredAnimals
        +Controller(IAnimalList, IView)
    }

    class View {
        -IController controller
        -JFrame mainFrame
        -JPanel cardPanel
        -CardLayout cardLayout
        -JPanel homePanel
        -JPanel reportPanel
        -JPanel mapPanel
        -JPanel wishlistPanel
        -List~IAnimal~ displayedAnimals
        -Map~String, JCheckBox~ filterCheckboxes
        +View()
        -void setupHomePanel()
        -void setupReportPanel()
        -void setupMapPanel()
        -void setupWishlistPanel()
    }

    class MapGeocoder {
        -static Map~String, LatLng~ geocodedAddresses
        +static LatLng getCoordinates(String)
        +static double calculateDistance(LatLng, LatLng)
    }

    class AnimalOutputGenerator {
        +AnimalOutputGenerator()
        -void writeToJson(List~IAnimal~, String)
        -void writeToCsv(List~IAnimal~, String)
        -void writeToXml(List~IAnimal~, String)
        -void writeToTxt(List~IAnimal~, String)
    }

    class Main {
        +static void main(String[])
    }

    %% Enums
    class Operation {
        <<enumeration>>
    }

    class OutputFormat {
        <<enumeration>>
        +static OutputFormat fromString(String)
    }

    %% Relationships
    Animal ..|> IAnimal : implements
    AnimalList ..|> IAnimalList : implements
    AnimalFilter ..|> IAnimalFilter : implements
    Controller ..|> IController : implements
    View ..|> IView : implements
    AnimalOutputGenerator ..|> IOutputGenerator : implements

    Controller --> IAnimalList : uses
    Controller --> IAnimalFilter : uses
    Controller --> Sorts : uses
    Controller --> IView : updates
    Controller --> IOutputGenerator : uses
    
    View --> IController : calls
    View --> MapGeocoder : uses
    
    AnimalList --> IAnimal : contains
    AnimalList --> AnimalData : uses
    
    Main --> Controller : creates
    Main --> View : creates
    Main --> AnimalList : creates
```

This UML diagram represents the main components of the Dog Station application, organized according to its MVC architecture:

1. **Model Layer**:
   - `IAnimal`: Interface defining animal properties
   - `Animal`: Implementation of the IAnimal interface
   - `IAnimalList` & `AnimalList`: Manage collections of animals
   - `IAnimalFilter` & `AnimalFilter`: Filter animals by various criteria
   - `Sorts`: Sort animals by different criteria
   - `OutputFormat`: Enum defining output formats (JSON, CSV, XML, TXT)
   - `IOutputGenerator` & `AnimalOutputGenerator`: Generate output in different formats

2. **Controller Layer**:
   - `IController` & `Controller`: Handle operations and connect the model and view
   - `Operation`: Enum defining available operations (filtering, sorting)

3. **View Layer**:
   - `IView` & `View`: Display the graphical user interface
   - `MapGeocoder`: Handle location data and geocoding

4. **Application Entry Point**:
   - `Main`: Initialize the application components

The diagram shows the relationships between these components, following the MVC architecture pattern.
