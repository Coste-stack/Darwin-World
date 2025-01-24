package area;

import javafx.scene.paint.Color;

public class Grassfield extends Area {
    private final static int FOOD_PREFERRED_TILE_CHANCE = 30;

    public Grassfield(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, Color.LIGHTGREEN, FOOD_PREFERRED_TILE_CHANCE);
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