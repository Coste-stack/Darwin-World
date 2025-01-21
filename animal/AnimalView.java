package animal;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AnimalView {
    private Color fillColor;
    private StackPane stackPane;

    public AnimalView(Color fillColor, float radius) {
        this.fillColor = fillColor;
        this.createAnimal(radius);
    }

    private void createAnimal(float radius) {
        Circle circle = new Circle();
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
}
