package area;

import utils.ConfigHandler;

public class Plains extends Area {
    public Plains(Point topLeft, Point bottomRight) {
        super(
                topLeft,
                bottomRight,
                "#4CAF50",
                ConfigHandler.getInstance().getConfigValue("PLAINS_FOOD_PREFERRED_TILE_CHANCE")
        );
    }

    @Override
    public Area getArea() {
        int BOARD_WIDTH = ConfigHandler.getInstance().getConfigValue("BOARD_WIDTH");
        int BOARD_HEIGHT = ConfigHandler.getInstance().getConfigValue("BOARD_HEIGHT");

        return new Plains(
                new Point(0, 0),
                new Point(BOARD_WIDTH, BOARD_HEIGHT)
        );
    }

    @Override
    public String getType() {
        return "area.Plains";
    }
}