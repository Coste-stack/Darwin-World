package animal;

import area.Point;

import javafx.scene.paint.Color;

public class Animal {
    private Point position;
    private AnimalView animalView;

    public Animal(Point position, float radius) {
        this.position = position;
        this.animalView = new AnimalView(Color.RED, radius);
    }

    public Point getPosition() {
        return this.position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public AnimalView getAnimalView() {
        return this.animalView;
    }
}
