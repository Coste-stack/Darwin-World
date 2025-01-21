package animal;

import area.Point;

import javafx.scene.paint.Color;

public class Animal {
    private Point position;         // Logical grid position of the animal
    private int energy;
    private AnimalView animalView;  // Visual representation of the animal
    private Direction direction;    // Direction facing the front of the animal

    public Animal(Point position, float radius) {
        this.position = position;
        this.energy = 1000;
        this.animalView = new AnimalView(Color.RED, radius);
        this.direction = Direction.getRandomDirection();
    }

    public void move() {
        this.position.setX(this.position.getX() + direction.getDeltaX());
        this.position.setY(this.position.getY() + direction.getDeltaY());
        this.energy -= 25;
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
        if (energy < 0) {
            this.energy -= energy;
        }
        // normalize current energy
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public AnimalView getAnimalView() {
        return this.animalView;
    }

    @Override
    public String toString() {
        return "Animal - " + this.position.toString() + " - " + this.energy;
    }
}
