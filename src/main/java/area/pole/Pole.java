package area.pole;

import area.Area;
import area.Point;

import javafx.scene.paint.Color;
import utils.ConfigHandler;

public abstract class Pole extends Area {
    private final PoleType poleType;

    public Pole(Point topLeft, Point bottomRight, PoleType poleType) {
        super(
                topLeft,
                bottomRight,
                Color.LIGHTBLUE,
                ConfigHandler.getInstance().getConfigValue("POLE_FOOD_PREFERRED_TILE_CHANCE")
        );
        this.poleType = poleType;
    }

    public PoleType getPoleType() {
        return poleType;
    }

    @Override
    public String getType() {
        return "area.pole." + poleType.name();
    }
}
