package com.myapp.area;

import javafx.scene.paint.Color;

public abstract class Area {
    protected final Point topLeft;
    protected final Point bottomRight;
    protected final Color tileColor;
    protected final int FOOD_PREFERRED_TILE_CHANCE; // Chance for food to spawn (0-100)

    public Area(Point topLeft, Point bottomRight, Color tileColor, int FOOD_PREFERRED_TILE_CHANCE) {
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
    public Color getTileColor() {
        return this.tileColor;
    }
    public int getFoodPreferedTileChance() {
        return this.FOOD_PREFERRED_TILE_CHANCE;
    }

    public abstract Area getArea(int gridWidth, int gridHeight);

    public abstract String getType();
}
