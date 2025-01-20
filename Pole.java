import javafx.scene.paint.Color;

public class Pole extends Area {
    public Pole(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, Color.LIGHTBLUE); // Pole tiles are light blue
    }

    @Override
    public Area getArea(int gridWidth, int gridHeight) {
        return new Pole(
                new Point(0, 0),
                new Point(gridWidth, (int) Math.ceil((double) gridHeight / 10))
        );
    }

    @Override
    public String getType() {
        return "Pole";
    }
}