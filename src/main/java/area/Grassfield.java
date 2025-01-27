package area;

import utils.ConfigHandler;

public class Grassfield extends Area {
    public Grassfield(Point topLeft, Point bottomRight) {
        super(
                topLeft,
                bottomRight,
                "#8BC34A",
                ConfigHandler.getInstance().getConfigValue("GRASSFIELD_FOOD_PREFERRED_TILE_CHANCE")
        );
    }

    @Override
    public Area getArea() {
        int BOARD_WIDTH = ConfigHandler.getInstance().getConfigValue("BOARD_WIDTH");
        int BOARD_HEIGHT = ConfigHandler.getInstance().getConfigValue("BOARD_HEIGHT");

        int centerX = BOARD_WIDTH / 2;
        int centerY = BOARD_HEIGHT / 2;

        int offsetWidth = BOARD_WIDTH / 4;
        int offsetHeight = BOARD_HEIGHT / 4;

        return new Grassfield(
                new Point(centerX - offsetWidth, centerY - offsetHeight),
                new Point(centerX + offsetWidth, centerY + offsetHeight)
        );
    }

    @Override
    public String getType() {
        return "area.Grassfield";
    }
}
