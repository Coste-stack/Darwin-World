package board;

import animal.Animal;
import area.Area;
import area.Grassfield;

import chart.Plot;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import utils.Random;

public class Board {
    private final Tile[][] boardMatrix;
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;
    private final Plot plot;
    private final static int primaryFoodChance = 80; // Chance for food to appear on foodPrefered tiles
    private final static int secondaryFoodChance = 100 - primaryFoodChance; // Chance for food to appear on non foodPrefered tiles
    private final static int foodSpawnInterval = 2; // Determines the interval (in turns) between food spawn
    private int foodSpawnCounter;

    public Board(int x, int y, Plot plot) {
        this.BOARD_WIDTH = x;
        this.BOARD_HEIGHT = y;
        this.boardMatrix = new Tile[x][y];
        this.plot = plot;
        this.foodSpawnCounter = foodSpawnInterval;
        Area grassfield = new Grassfield(null, null).getArea(BOARD_WIDTH, BOARD_HEIGHT);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Color fillColor = (i + j) % 2 == 0 ? Color.GRAY : Color.BLACK;
                Tile tile = new Tile(fillColor);
                tile.setArea(grassfield);
                this.boardMatrix[i][j] = tile;
            }
        }
    }

    public Tile[][] getBoardMatrix() {
        return this.boardMatrix;
    }
    public int getWidth() {
        return this.BOARD_WIDTH;
    }
    public int getHeight() {
        return this.BOARD_HEIGHT;
    }

    public void addArea(Area area) {
        for (int i = area.getTopLeft().getX(); i < area.getBottomRight().getX(); i++) {
            for (int j = area.getTopLeft().getY(); j < area.getBottomRight().getY(); j++) {
                this.boardMatrix[i][j].setArea(area);
            }
        }
    }

    public void addAnimal(Animal animal) {
        this.boardMatrix[animal.getPosition().getX()][animal.getPosition().getY()].addAnimal(animal);
    }

    public void setFoodPreferredTiles() {
        for (int i = 0; i < this.BOARD_WIDTH; i++) {
            for (int j = 0; j < this.BOARD_HEIGHT; j++) {
                Tile tile = this.boardMatrix[i][j];
                tile.setFoodPreferred(false);
                if (Random.getRandom(1, 500) <= tile.getArea().getFoodPreferedTileChance()) {
                    tile.setFoodPreferred(true);
                }
            }
        }
    }

    public void setFoodRandomly() {
        foodSpawnCounter++;
        if (foodSpawnCounter > foodSpawnInterval-1) {
            foodSpawnCounter = 0;
            for (int i = 0; i < this.BOARD_WIDTH; i++) {
                for (int j = 0; j < this.BOARD_HEIGHT; j++) {
                    Tile tile = this.boardMatrix[i][j];
                    if (!tile.hasFood()) {
                        if (tile.isFoodPreferred()) {
                            if (Random.getRandom(1, 500) <= primaryFoodChance) {
                                tile.setHasFood(true);
                            }
                        } else {
                            if (Random.getRandom(1, 500) <= secondaryFoodChance) {
                                tile.setHasFood(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public int calcAnimalDistToNearestPole(Animal animal) {
        return Math.min(
                calcAnimalDistToNorthPole(animal),
                calcAnimalDistToSouthPole(animal)
        );
    }

    private int calcAnimalDistToNorthPole(Animal animal) {
        // Check if current position is at pole
        if (boardMatrix[animal.getPosition().getX()][animal.getPosition().getY()].getArea().getType().equals("area.pole.NorthPole")) {
            return 0;
        }
        // Calculate the distance using one axis
        int i;
        for (i = animal.getPosition().getY(); i >= 0; i--) {
            if (boardMatrix[animal.getPosition().getX()][i].getArea().getType().equals("area.pole.NorthPole")) {
                break;
            }
        }
        return i;
    }

    private int calcAnimalDistToSouthPole(Animal animal) {
        // Check if current position is at pole
        if (boardMatrix[animal.getPosition().getX()][animal.getPosition().getY()].getArea().getType().equals("area.pole.SouthPole")) {
            return 0;
        }
        // Calculate the distance using one axis
        int i;
        for (i = animal.getPosition().getY(); i < this.BOARD_HEIGHT; i++) {
            if (boardMatrix[animal.getPosition().getX()][i].getArea().getType().equals("area.pole.SouthPole")) {
                break;
            }
        }
        return i;
    }

    public void addAnimalSeriesData(int iteration, int animalAmount) {
        adjustAxisBounds(iteration, animalAmount);
        this.plot.getAnimalSeries().getData().add(new XYChart.Data<>(iteration, animalAmount));
    }

    public void addFoodSeriesData(int iteration, int foodAmount) {
        adjustAxisBounds(iteration, foodAmount);
        this.plot.getFoodSeries().getData().add(new XYChart.Data<>(iteration, foodAmount));
    }

    private void adjustAxisBounds(int iteration, int amount) {
        // Adjust the X-axis upper bound in blocks
        int currentXAxisUpperBound = (int) this.plot.getXAxis().getUpperBound();
        int newXAxisUpperBound = ((iteration / 10) + 1) * 10;
        if (newXAxisUpperBound > currentXAxisUpperBound) {
            this.plot.getXAxis().setUpperBound(newXAxisUpperBound);
        }

        // Adjust the Y-axis upper bound in blocks
        int currentYAxisUpperBound = (int) this.plot.getYAxis().getUpperBound();
        int newYAxisUpperBound = ((amount / 10) + 1) * 10;
        if (newYAxisUpperBound > currentYAxisUpperBound) {
            this.plot.getYAxis().setUpperBound(newYAxisUpperBound);
        }
    }
}