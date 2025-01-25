package area;

import javafx.scene.paint.Color;
import utils.ConfigHandler;

public class Plains extends Area {
    public Plains(Point topLeft, Point bottomRight) {
        super(
                topLeft,
                bottomRight,
                Color.GREEN.brighter(),
                ConfigHandler.getInstance().getConfig("PLAINS_FOOD_PREFERRED_TILE_CHANCE")
        );
    }

    @Override
    public Area getArea() {
        int BOARD_WIDTH = ConfigHandler.getInstance().getConfig("BOARD_WIDTH");
        int BOARD_HEIGHT = ConfigHandler.getInstance().getConfig("BOARD_HEIGHT");

        int centerX = BOARD_WIDTH / 2;
        int centerY = BOARD_HEIGHT / 2;

        int offsetWidth = BOARD_WIDTH / 4;
        int offsetHeight = BOARD_HEIGHT / 4;

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
