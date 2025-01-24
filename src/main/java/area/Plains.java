package area;

import javafx.scene.paint.Color;

public class Plains extends Area {
    private final static int FOOD_PREFERRED_TILE_CHANCE = 50;

    public Plains(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, Color.GREEN.brighter(), FOOD_PREFERRED_TILE_CHANCE);
    }

    @Override
    public Area getArea(int gridWidth, int gridHeight) {
        int centerX = gridWidth / 2;
        int centerY = gridHeight / 2;

        int offsetWidth = gridWidth / 4;
        int offsetHeight = gridHeight / 4;

        return new Plains(
                new Point(centerX - offsetWidth, centerY - offsetHeight),
                new Point(centerX + offsetWidth, centerY + offsetHeight)
        );
    }

    @Override
    public String getType() {
        return "area.Plains";
    }
}
