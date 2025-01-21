package animal;

import area.Point;

import javafx.scene.paint.Color;

public class Animal {
    private Point position;         // Logical grid position of the animal
    private AnimalView animalView;  // Visual representation of the animal
    private Direction direction;    // Direction facing the front of the animal

    public Animal(Point position, float radius) {
        this.position = position;
        this.animalView = new AnimalView(Color.RED, radius);
        this.direction = Direction.UP; // Default direction
    }

    public void move() {
        this.position.setX(this.position.getX() + direction.getDeltaX());
        this.position.setY(this.position.getY() + direction.getDeltaY());
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

    public AnimalView getAnimalView() {
        return this.animalView;
    }
}
