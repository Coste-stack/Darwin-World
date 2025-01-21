package area;

import javafx.scene.paint.Color;

public class Grassfield extends Area {
    public Grassfield(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, Color.LIGHTGREEN);
    }

    @Override
    public Area getArea(int gridWidth, int gridHeight) {
        return new Grassfield(
                new Point(0, 0),
                new Point(gridWidth, gridHeight)
        );
    }

    @Override
    public String getType() {
        return "area.Grassfield";
    }
}