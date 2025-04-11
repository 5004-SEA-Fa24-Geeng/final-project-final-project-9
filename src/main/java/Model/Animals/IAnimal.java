package Model.Animals;

import Model.AnimalInfo.*;

public interface IAnimal {
    /** Numebr used for assigning numbers to new animals. Increment by 1 when new animal is added. */
    int animalNumber = 50; // Already have 50 animals in database

   AnimalType getAnimalType();

   String getSpecies();

   Size getAnimalSize();

   Gender getGender();

   Pattern getPattern();

   Color getAnimalColor();

   Age getAnimalAge();

   Integer[] getWitnessedTime();

   String getAddress();

   Area getArea();

   String getloDescription();

   String getDescription();

    public String getImage();

    public int getNumber();
}
