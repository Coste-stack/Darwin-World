package area.pole;

import area.Area;
import area.Point;
import utils.ConfigHandler;

public class NorthPole extends Pole {
    public NorthPole(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, PoleType.NorthPole);
    }

    @Override
    public Area getArea() {
        int BOARD_WIDTH = ConfigHandler.getInstance().getConfigValue("BOARD_WIDTH");
        int BOARD_HEIGHT = ConfigHandler.getInstance().getConfigValue("BOARD_HEIGHT");

        return new NorthPole(
            new Point(0, 0),
            new Point(BOARD_WIDTH, (int) Math.ceil((double) BOARD_HEIGHT / 10))
        );
    }
}