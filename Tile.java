import javafx.scene.paint.Color;

public class Tile {
    private Color fillColor;
    private Color strokeColor;

    public Tile(Color fillColor, Color strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    public Color getFillColor() {
        return this.fillColor;
    }
    public Color getStrokeColor() {
        return this.strokeColor;
    }
}
