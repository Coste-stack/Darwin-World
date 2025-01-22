package animal;

import area.Point;

import javafx.scene.paint.Color;

public class Animal {
    private AnimalView animalView; // Visual representation of the animal
    private Point position; // Grid position of the animal
    private Direction direction; // Direction facing the front of the animal
    private static final int DEFAULT_ENERGY = 100;
    private int energy;
    private boolean isAlive;

    public Animal(Point position, float radius) {
        this.animalView = new AnimalView(Color.RED, radius);
        this.position = position;
        this.direction = Direction.getRandomDirection();
        this.energy = DEFAULT_ENERGY;
        isAlive = true;
    }

    public void move() {
        this.position.setX(this.position.getX() + direction.getDeltaX());
        this.position.setY(this.position.getY() + direction.getDeltaY());
        this.subtractEnergy(5);
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

    public int getEnergy() {
        return this.energy;
    }

    public void addEnergy(int energy) {
        // add it
        if (energy >= 0) {
            this.energy += energy;
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
        return "Animal - " + this.position.toString() + " - " + this.energy;
    }
}
