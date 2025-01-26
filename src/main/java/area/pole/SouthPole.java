package area.pole;

import area.Area;
import area.Point;
import utils.ConfigHandler;

public class SouthPole extends Pole {
    public SouthPole(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight, PoleType.SouthPole);
    }

    @Override
    public Area getArea() {
        int BOARD_WIDTH = ConfigHandler.getInstance().getConfigValue("BOARD_WIDTH");
        int BOARD_HEIGHT = ConfigHandler.getInstance().getConfigValue("BOARD_HEIGHT");

        return new SouthPole(
                new Point(0, BOARD_HEIGHT - (int) Math.ceil((double) BOARD_HEIGHT / 10)),
                new Point(BOARD_WIDTH, BOARD_HEIGHT)
        );
    }
}