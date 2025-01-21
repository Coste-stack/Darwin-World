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

    public Board(int x, int y, Plot plot) {
        this.BOARD_WIDTH = x;
        this.BOARD_HEIGHT = y;
        this.boardMatrix = new Tile[x][y];
        this.plot = plot;
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
        this.boardMatrix[animal.getPosition().getX()][animal.getPosition().getY()].setAnimal(animal);
    }

    public void setFoodRandomly() {
        for (int i = 0; i < this.BOARD_WIDTH; i++) {
            for (int j = 0; j < this.BOARD_HEIGHT; j++) {
                if (Random.getRandom(1, 100) <= 5) {
                    this.boardMatrix[i][j].setHasFood(true);
                }
            }
        }
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

    public void addAnimalSeriesData(int iteration, int animalAmount) {
        adjustAxisBounds(iteration, animalAmount);
        this.plot.getAnimalSeries().getData().add(new XYChart.Data<>(iteration, animalAmount));
    }

    public void addFoodSeriesData(int iteration, int foodAmount) {
        adjustAxisBounds(iteration, foodAmount);
        this.plot.getFoodSeries().getData().add(new XYChart.Data<>(iteration, foodAmount));
    }
}