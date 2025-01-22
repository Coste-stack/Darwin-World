package animal;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AnimalView {
    private Color fillColor;
    private StackPane stackPane;
    private Circle circle;

    private float radius;
    private final float radiusDefault;

    public AnimalView(Color fillColor, float radius) {
        this.fillColor = fillColor;
        this.radius = radius;
        this.radiusDefault = radius;
        this.createAnimal();
    }

    private void createAnimal() {
        circle = new Circle();
        circle.setFill(this.fillColor);
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(radius);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(circle);
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
    public float getRadius() {
        return this.radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
        circle.setRadius(radius);
    }
    public float getDefaultRadius() {
        return radiusDefault;
    }
}
