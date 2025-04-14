package Model.Animals;

import Model.AnimalInfo.Species.Rabbits;
import Model.AnimalInfo.Species.Species;

public class Rabbit extends Animal {
    /**
     * Creates a new animal instance with the specified attributes.
     *
     * @param type        The type of animal
     * @param species     The species of animal
     * @param size        The size of animal
     * @param gender      The gender of animal
     * @param pattern     The pattern of animal
     * @param color       The color of animal
     * @param age         The age of animal
     * @param address     The address where animal was seen
     * @param area        The city where animal was seen
     * @param time        The time when animal was seen
     * @param seenDate    The date when animal was seen
     * @param description The general description
     * @param locDesc     The location description
     * @param number      the number of this animal
     * @param fileSrc     the file source of this image
     */
    public Rabbit(String type, String species, String size, String gender, String pattern, String color, String age, String address, String area, String time, String seenDate, String description, String locDesc, String number, String fileSrc) {
        super(type, species, size, gender, pattern, color, age, address, area, time, seenDate, description, locDesc, number, fileSrc);
    }

    @Override
    public String[] getSpeciesOfThisType() {
        Species[] species = Rabbits.values();
        String[] breeds = new String[species.length];
        for (int i = 0; i < breeds.length; ++i) {
            breeds[i] = species[i].toString();
        }
        return breeds;
    }
}
