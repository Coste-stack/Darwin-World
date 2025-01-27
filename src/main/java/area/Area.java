package area;

public abstract class Area {
    protected final Point topLeft;
    protected final Point bottomRight;
    protected final String tileColor;
    protected final int FOOD_PREFERRED_TILE_CHANCE; // Chance for food to spawn (0-100)

    public Area(Point topLeft, Point bottomRight, String tileColor, int FOOD_PREFERRED_TILE_CHANCE) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.tileColor = tileColor;
        this.FOOD_PREFERRED_TILE_CHANCE = FOOD_PREFERRED_TILE_CHANCE;
    }

    public Point getTopLeft() {
        return this.topLeft;
    }
    public Point getBottomRight() {
        return this.bottomRight;
    }
    public String getTileColor() {
        return this.tileColor;
    }
    public int getFoodPreferredTileChance() {
        return this.FOOD_PREFERRED_TILE_CHANCE;
    }

    public abstract Area getArea();

    public abstract String getType();
}
