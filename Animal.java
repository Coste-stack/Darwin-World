import javafx.scene.paint.Color;

public class Animal {
    private Point position;
    private AnimalView animalView;

    public Animal(Point position) {
        this.position = position;
        this.animalView = new AnimalView(Color.RED);
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
