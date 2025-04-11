package Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PetOwner {
    private int id;
    private String name;
    private Set<PostedAnimal> potentialPets = new HashSet<>();

    public PetOwner(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Set<PostedAnimal> getList() {
        return potentialPets;
    }


}
