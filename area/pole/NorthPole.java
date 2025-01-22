package area.pole;

import area.Area;
import area.Point;

public class NorthPole extends Pole {
    public NorthPole(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, PoleType.NorthPole);
    }

    @Override
    public Area getArea(int gridWidth, int gridHeight) {
        return new NorthPole(
                new Point(0, 0),
                new Point(gridWidth, (int) Math.ceil((double) gridHeight / 10))
        );
    }
}