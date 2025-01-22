package board;

import animal.Animal;
import area.Area;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private static final Color foodColor = Color.GREEN;
    private Color fillColor;
    private boolean isFoodPreferred;

    private List<Animal> animalList;
    private Area area;
    private boolean hasFood;

    public Tile(Color fillColor) {
        animalList = new ArrayList<>();
        this.fillColor = fillColor;
        this.hasFood = false;
    }
    public Tile(Color fillColor, boolean hasFood) {
        animalList = new ArrayList<>();
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

    public List<Animal> getAnimalList() {
        return this.animalList;
    }
    public void addAnimal(Animal animal) {
        this.animalList.add(animal);
    }
    public void removeAnimal(Animal animal) {
        this.animalList.remove(animal);
    }
    public void clearAnimalList() {
        this.animalList.clear();
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
