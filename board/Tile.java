package board;

import animal.Animal;
import area.Area;
import javafx.scene.paint.Color;

public class Tile {
    private static final Color foodColor = Color.GREEN;
    private Color fillColor;
    private boolean isFoodPreferred;

    private Animal animal;
    private Area area;
    private boolean hasFood;

    public Tile(Color fillColor) {
        this.fillColor = fillColor;
        this.hasFood = false;
    }
    public Tile(Color fillColor, boolean hasFood) {
        this.fillColor = fillColor;
        this.hasFood = hasFood;
        if(hasFood) {
            this.fillColor = foodColor;
        }
    }

    public Color getFillColor() {
        return this.fillColor;
    }
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Animal getAnimal() {
        return this.animal;
    }
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public boolean hasFood() {
        return this.hasFood;
    }
    public void setHasFood(boolean hasFood) {
        this.hasFood = hasFood;
        if (hasFood) {
            this.fillColor = foodColor;
        } else {
            this.fillColor = area.getTileColor();
        }
    }

    public Area getArea() {
        return this.area;
    }
    public void setArea(Area area) {
        this.area = area;
        this.fillColor = area.getTileColor();
    }

    public boolean isFoodPreferred() {
        return this.isFoodPreferred;
    }
    public void setFoodPreferred(boolean isFoodPreferred) {
        this.isFoodPreferred = isFoodPreferred;
    }
}
