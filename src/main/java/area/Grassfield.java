package area;

import javafx.scene.paint.Color;
import utils.ConfigHandler;

public class Grassfield extends Area {
    public Grassfield(Point topLeft, Point bottomRight) {
        super(
                topLeft,
                bottomRight, 
                Color.LIGHTGREEN,
                ConfigHandler.getInstance().getConfig("GRASSFIELD_FOOD_PREFERRED_TILE_CHANCE")
        );
    }

    @Override
    public Area getArea() {
        int BOARD_WIDTH = ConfigHandler.getInstance().getConfig("BOARD_WIDTH");
        int BOARD_HEIGHT = ConfigHandler.getInstance().getConfig("BOARD_HEIGHT");

        return new Grassfield(
                new Point(0, 0),
                new Point(BOARD_WIDTH, BOARD_HEIGHT)
        );
    }

    @Override
    public String getType() {
        return "area.Grassfield";
    }
}