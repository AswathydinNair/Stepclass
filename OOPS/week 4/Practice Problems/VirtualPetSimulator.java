import java.util.*;

class VirtualPet {
    final String petId;
    String petName;
    String species;
    int age;
    int happiness;
    int health;
    String stage;
    static final String[] EVOLUTION_STAGES = {"Egg", "Baby", "Child", "Teen", "Adult", "Elder"};
    static int totalPetsCreated = 0;

    VirtualPet() {
        this("peppa", getRandomSpecies(), 0, 50, 50, EVOLUTION_STAGES[0]);
    }

    VirtualPet(String petName) {
        this(petName, getRandomSpecies(), 0, 60, 60, EVOLUTION_STAGES[1]);
    }

    VirtualPet(String petName, String species) {
        this(petName, species, 0, 70, 70, EVOLUTION_STAGES[2]);
    }

    VirtualPet(String petName, String species, int age, int happiness, int health, String stage) {
        this.petId = generatePetId();
        this.petName = petName;
        this.species = species;
        this.age = age;
        this.happiness = happiness;
        this.health = health;
        this.stage = stage;
        totalPetsCreated++;
    }

    void evolvePet() {
        if (stage.equals("Ghost")) return;
        if (age >= 0 && age < 2) stage = EVOLUTION_STAGES[0];
        else if (age < 5) stage = EVOLUTION_STAGES[1];
        else if (age < 10) stage = EVOLUTION_STAGES[2];
        else if (age < 15) stage = EVOLUTION_STAGES[3];
        else if (age < 20) stage = EVOLUTION_STAGES[4];
        else stage = EVOLUTION_STAGES[5];
    }

    void feedPet() {
        if (!isAlive()) return;
        happiness += 5;
        health += 10;
    }

    void playWithPet() {
        if (!isAlive()) return;
        happiness += 10;
        health -= 2;
    }

    void healPet() {
        if (!isAlive()) return;
        health += 15;
    }

    void simulateDay() {
        if (!isAlive()) return;
        age++;
        happiness -= new Random().nextInt(5);
        health -= new Random().nextInt(5);
        if (health <= 0) {
            stage = "Ghost";
            health = 0;
        } else {
            evolvePet();
        }
    }

    String getPetStatus() {
        return "PetID: " + petId + " | Name: " + petName + " | Species: " + species +
               " | Age: " + age + " | Happiness: " + happiness +
               " | Health: " + health + " | Stage: " + stage;
    }

    boolean isAlive() {
        return !stage.equals("Ghost");
    }

    static String generatePetId() {
        return UUID.randomUUID().toString();
    }

    static String getRandomSpecies() {
        String[] speciesList = {"Dragon", "Phoenix", "Unicorn", "Wolf", "Cat"};
        return speciesList[new Random().nextInt(speciesList.length)];
    }
}

public class VirtualPetSimulator {
    public static void main(String[] args) {
        List<VirtualPet> daycare = new ArrayList<>();
        daycare.add(new VirtualPet());
        daycare.add(new VirtualPet("Luna"));
        daycare.add(new VirtualPet("Max", "Dragon"));
        daycare.add(new VirtualPet("Bella", "Wolf", 8, 80, 90, "Child"));

        for (int day = 1; day <= 10; day++) {
            System.out.println("Day " + day);
            for (VirtualPet pet : daycare) {
                pet.simulateDay();
                if (pet.isAlive()) {
                    pet.feedPet();
                    pet.playWithPet();
                }
                System.out.println(pet.getPetStatus());
            }
        }
        System.out.println("Total Pets Created: " + VirtualPet.totalPetsCreated);
    }
}
