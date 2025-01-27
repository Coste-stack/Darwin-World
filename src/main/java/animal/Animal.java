package animal;

import area.Point;

import javafx.scene.paint.Color;
import utils.ConfigHandler;
import utils.Random;

import java.util.Arrays;

public class Animal {
    private AnimalView animalView; // Visual representation of the animal
    private Point position; // Grid position of the animal
    private Direction direction; // Direction facing the front of the animal
    private boolean isAlive;
    private int age;
    private final int GENOTYPE_SIZE = Direction.values().length;
    private Direction[] genotypeList;
    private int currGenotypeIndex;
    private int energy;
    private int energyConsumption;

    public Animal(Point position) {
        this.position = position;
        this.direction = Direction.getRandomDirection();
        this.isAlive = true;
        this.age = 0;
        this.energy = ConfigHandler.getInstance().getConfigValue("DEFAULT_ENERGY");
        this.energyConsumption = ConfigHandler.getInstance().getConfigValue("DEFAULT_ENERGY_CONSUMPTION");
        this.genotypeList = generateGenotype();
        this.currGenotypeIndex = 0;
    }

    private Direction[] generateGenotype() {
        Direction[] genotype = new Direction[GENOTYPE_SIZE];
        for (int i = 0; i < GENOTYPE_SIZE; i++) {
            genotype[i] = Direction.values()[Random.getRandom(0, Direction.values().length-1)];
        }
        return genotype;
    }

    public Direction[] getGenotype() {
        return genotypeList;
    }

    public void setGenotype(Direction[] genotypeList) {
        this.genotypeList = genotypeList;
    }

    public int getGenotypeSize() {
        return GENOTYPE_SIZE;
    }

    private int getSkipTurnChance () {
        int MIN_AGE_TO_SKIP_TURN = ConfigHandler.getInstance().getConfigValue("MIN_AGE_TO_SKIP_TURN");
        return Math.min((age - MIN_AGE_TO_SKIP_TURN) * 2, 50);
    }

    public void move() {
        int MIN_AGE_TO_SKIP_TURN = ConfigHandler.getInstance().getConfigValue("MIN_AGE_TO_SKIP_TURN");
        if (!(age > MIN_AGE_TO_SKIP_TURN && Random.getRandom(0, 100) < getSkipTurnChance())) {
            // Get direction from genotypes
            this.direction = this.genotypeList[this.currGenotypeIndex];

            // Set new animal position
            this.position.setX(this.position.getX() + direction.getDeltaX());
            this.position.setY(this.position.getY() + direction.getDeltaY());

            // Increment genotype index and check if inbounds of list
            this.currGenotypeIndex++;
            if (this.currGenotypeIndex >= this.genotypeList.length) {
                this.currGenotypeIndex = 0;
            }
        }
        this.subtractEnergy(energyConsumption);
        this.age++;
    }

    public void rotate() {
        this.direction = Direction.getRandomDirection();
    }

    public void rotate(Direction direction) {
        this.direction = direction;
    }

    public Point getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getAge() {
        return this.age;
    }

    public int getEnergy() {
        return this.energy;
    }

    public float getEnergyPercentage() {
        int MAX_ENERGY = ConfigHandler.getInstance().getConfigValue("MAX_ENERGY");
        if (MAX_ENERGY == 0) {
            return 0;
        }
        return (float) this.energy / (float) MAX_ENERGY;
    }

    public int getEnergyConsumption() {
        return this.energyConsumption;
    }

    public void setEnergyConsumption(int energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public int addEnergy(int energy) {
        int added = 0;
        // add it
        if (energy >= 0) {
            added = this.energy + energy;
            this.energy += energy;
        }
        // normalize the energy
        int MAX_ENERGY = ConfigHandler.getInstance().getConfigValue("MAX_ENERGY");
        if (this.energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        }
        return added;
    }

    public int subtractEnergy(int energy) {
        int subtracted = 0;
        // subtract it
        if (energy > 0) {
            subtracted = Math.max(this.energy - energy, 0);
            this.energy -= energy;
        }
        // normalize current energy
        if (this.energy < 0) {
            this.energy = 0;
        }
        // check if no energy - dead
        if (this.energy == 0) {
            isAlive = false;
            animalView = null;
        }
        return subtracted;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean canReproduce() {
        return this.energy >= ConfigHandler.getInstance().getConfigValue("REPRODUCTION_ENERGY_REQUIREMENT");
    }

    public boolean isAlive() {
        return isAlive;
    }

    public AnimalView getAnimalView() {
        return this.animalView;
    }

    public void setAnimalView(AnimalView animalView) {
        this.animalView = animalView;
    }

    @Override
    public String toString() {
        return "Animal: " + this.position.toString() + "; age: " + this.age + "; energy: " + this.energy + " consumption: " + this.energyConsumption + " %: " + this.getEnergyPercentage() + "; genotype: " + Arrays.toString(this.genotypeList);
    }
}
