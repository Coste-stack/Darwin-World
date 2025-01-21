package area;

import javafx.scene.paint.Color;

public abstract class Area {
    protected final Point topLeft;
    protected final Point bottomRight;
    protected final Color tileColor;

    public Area(Point topLeft, Point bottomRight, Color tileColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.tileColor = tileColor;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }
    public Point getBottomRight() {
        return this.bottomRight;
    }
    public Color getTileColor() {
        return this.tileColor;
    }

    public abstract Area getArea(int gridWidth, int gridHheight);

    public abstract String getType();
}
