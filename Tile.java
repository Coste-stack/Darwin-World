import javafx.scene.paint.Color;

public class Tile {
    private Color fillColor;

    public Tile(Color fillColor, Area area) {
        this.fillColor = fillColor;
    }

    public Color getFillColor() {
        return this.fillColor;
    }
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
}
