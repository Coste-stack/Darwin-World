package com.myapp.board;

import com.myapp.animal.Animal;
import com.myapp.area.Area;
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

    private Animal getAnimalWithMostEnergy() {
        if (this.animalList.isEmpty()) {
            return null;
        }
        Animal maxEnergyAnimal = this.animalList.getFirst();
        for (Animal animal : this.animalList) {
            if (animal.getEnergy() > maxEnergyAnimal.getEnergy()) {
                maxEnergyAnimal = animal;
            }
        }
        return maxEnergyAnimal;
    }

    public List<Animal> getAnimalListWithMostEnergy() {
        if (this.animalList.isEmpty()) {
            return null;
        }
        Animal maxEnergyAnimal = this.getAnimalWithMostEnergy();
        List<Animal> maxEnergyAnimalList = new ArrayList<>();
        for (Animal animal : this.animalList) {
            if (animal.getEnergy() == maxEnergyAnimal.getEnergy()) {
                maxEnergyAnimalList.add(animal);
            }
        }
        return maxEnergyAnimalList;
    }

    private Animal getOldestAnimal(List<Animal> animalList) {
        if (animalList.isEmpty()) {
            return null;
        }
        Animal oldestAnimal = animalList.getFirst();
        for (Animal animal : animalList) {
            if (animal.getAge() > oldestAnimal.getAge()) {
                oldestAnimal = animal;
            }
        }
        return oldestAnimal;
    }

    public List<Animal> getOldestAnimalList(List<Animal> animalList) {
        if (animalList.isEmpty()) {
            return null;
        }
        Animal oldestAnimal = this.getOldestAnimal(animalList);
        List<Animal> oldestAnimalList = new ArrayList<>();
        for (Animal animal : animalList) {
            if (animal.getAge() == oldestAnimal.getAge()) {
                oldestAnimalList.add(animal);
            }
        }
        return oldestAnimalList;
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
