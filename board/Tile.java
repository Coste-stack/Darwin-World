package board;

import animal.Animal;
import area.Area;
import javafx.scene.paint.Color;

public class Tile {
    private Color fillColor;
    private Animal animal;
    private Area area;

    public Tile(Color fillColor) {
        this.fillColor = fillColor;
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

    public Area getArea() {
        return this.area;
    }
    public void setArea(Area area) {
        this.area = area;
        this.fillColor = area.getTileColor();
    }
}
