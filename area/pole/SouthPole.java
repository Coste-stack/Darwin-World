package area.pole;

import area.Area;
import area.Point;

public class SouthPole extends Pole {
    public SouthPole(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, PoleType.SouthPole);
    }

    @Override
    public Area getArea(int gridWidth, int gridHeight) {
        return new SouthPole(
                new Point(0, gridHeight - (int) Math.ceil((double) gridHeight / 10)),
                new Point(gridWidth, gridHeight)
        );
    }
}