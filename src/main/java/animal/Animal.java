package animal;

import area.Point;

import javafx.scene.paint.Color;
import utils.Random;

public class Animal {
    private AnimalView animalView; // Visual representation of the animal
    private Point position; // Grid position of the animal
    private Direction direction; // Direction facing the front of the animal
    private int age;
    private static final int MIN_AGE_TO_SKIP_TURN = 10;

    private int energy;
    private static final int DEFAULT_ENERGY = 100;
    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY = 0;
    private int energyConsumption;
    private static final int DEFAULT_ENERGY_CONSUMPTION = 10;
    private static final int MAX_ENERGY_CONSUMPTION = 15;
    private static final int MIN_ENERGY_CONSUMPTION = 5;
    private static final int ENERGY_FOOD_GAIN = 50;

    private boolean isAlive;

    public Animal(Point position, float radius) {
        this.animalView = new AnimalView(this, Color.RED, radius);
        this.position = position;
        this.direction = Direction.getRandomDirection();
        this.energy = DEFAULT_ENERGY;
        this.energyConsumption = DEFAULT_ENERGY_CONSUMPTION;
        isAlive = true;
        age = 0;
    }

    private int getSkipTurnChance () {
        return Math.min((age - MIN_AGE_TO_SKIP_TURN) * 5, 50);
    }

    public void move() {
        if (!(age > MIN_AGE_TO_SKIP_TURN && Random.getRandom(0, 100) < getSkipTurnChance())) {
            this.position.setY(this.position.getY() + direction.getDeltaY());
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

    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    public int getMinEnergy() {
        return MIN_ENERGY;
    }

    public float getEnergyPercentage() {
        if (this.getMaxEnergy() == 0) {
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

    public int getMaxEnergyConsumption() {
        return MAX_ENERGY_CONSUMPTION;
    }
    public int getMinEnergyConsumption() {
        return MIN_ENERGY_CONSUMPTION;
    }
    public int getEnergyFoodGain() {
        return ENERGY_FOOD_GAIN;
    }

    public void addEnergy(int energy) {
        // add it
        if (energy >= 0) {
            this.energy += energy;
        }
        // normalize the energy
        if (this.energy > MAX_ENERGY) {
            this.energy = MAX_ENERGY;
        }
    }

    public void subtractEnergy(int energy) {
        // subtract it
        if (energy > 0) {
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
    }

    public boolean isAlive() {
        return isAlive;
    }

    public AnimalView getAnimalView() {
        return this.animalView;
    }

    @Override
    public String toString() {
        return "Animal: " + this.position.toString() + "; energy: " + this.energy + " consumption: " + this.energyConsumption + " %: " + this.getEnergyPercentage();
    }
}
