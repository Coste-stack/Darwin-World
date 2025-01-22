package area.pole;

import area.Area;
import area.Point;

import javafx.scene.paint.Color;

public abstract class Pole extends Area {
    private final PoleType poleType;
    private final static int FOOD_PREFERRED_TILE_CHANCE = 5;

    public Pole(Point topLeft, Point bottomRight, PoleType poleType) {
        super(topLeft, bottomRight, Color.LIGHTBLUE, FOOD_PREFERRED_TILE_CHANCE);
        this.poleType = poleType;
    }

    public PoleType getPoleType() {
        return poleType;
    }

    @Override
    public String getType() {
        return "area.pole.Pole - " + poleType.name();
    }
}
