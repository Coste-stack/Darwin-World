package area;

import javafx.scene.paint.Color;

public class Plains extends Area {
    public Plains(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, Color.LIGHTGREEN); // Plain tiles are light green
    }

    @Override
    public Area getArea(int gridWidth, int gridHeight) {
        // Create a "area.Plains" area centered in the grid.
        int centerX = gridWidth / 2;
        int centerY = gridHeight / 2;

        int offsetWidth = gridWidth / 4;  // area.Plains take up half the width
        int offsetHeight = gridHeight / 4; // area.Plains take up half the height

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
