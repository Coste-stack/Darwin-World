package area.pole;

import area.Area;
import area.Point;
import javafx.scene.paint.Color;

public abstract class Pole extends Area {
    private final PoleType poleType;

    public Pole(Point topLeft, Point bottomRight, PoleType poleType) {
        super(topLeft, bottomRight, Color.LIGHTBLUE); // Poles are light blue
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
