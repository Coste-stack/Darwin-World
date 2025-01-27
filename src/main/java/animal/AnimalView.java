package animal;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class AnimalView {
    private final Animal animal;
    private Color fillColor;
    private StackPane stackPane;

    private static final Color GOOD_ENERGY_COLOR = Color.GREEN;
    private static final Color MEDIUM_ENERGY_COLOR = Color.YELLOW;
    private static final Color BAD_ENERGY_COLOR = Color.RED;

    public AnimalView(Animal animal, Color fillColor, float radius) {
        this.animal = animal;
        this.fillColor = fillColor;
        this.createAnimal(radius);
    }

    private void createAnimal(float radius) {
        Circle innerCircle = new Circle();
        innerCircle.setFill(Color.web("#bab6ae"));
        innerCircle.setCenterX(radius);
        innerCircle.setCenterY(radius);
        innerCircle.setRadius(radius * 0.8);

        // Create the rectangle with height of energy fill
        Rectangle rectangle = new Rectangle(
                radius * 2,
                radius * animal.getEnergyPercentage() * 2
        );
        rectangle.setX(0);
        rectangle.setY(0);

        // Intersect the circle and rectangle fill
        Shape intersection = Shape.intersect(innerCircle, rectangle);
        // Set color based on energy
        if (animal.getEnergyPercentage() >= 0.66) {
            intersection.setFill(GOOD_ENERGY_COLOR);
        } else if (animal.getEnergyPercentage() >= 0.33) {
            intersection.setFill(MEDIUM_ENERGY_COLOR);
        } else {
            intersection.setFill(BAD_ENERGY_COLOR);
        }

        // Rotate the inner circle
        Group group = new Group(innerCircle, intersection);
        group.setRotate(180);

        Circle outerCircle = new Circle();
        outerCircle.setFill(this.fillColor);
        outerCircle.setCenterX(radius);
        outerCircle.setCenterY(radius);
        outerCircle.setRadius(radius);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(outerCircle, group);
        this.stackPane = stackPane;
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public StackPane getStackPane() {
        return this.stackPane;
    }

    public void scaleView(float radius) {
        this.stackPane = null;
        this.createAnimal(radius);
    }
}
