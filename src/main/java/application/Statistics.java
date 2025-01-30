package application;

import animal.Animal;
import board.Board;
import board.Tile;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private final Board board;
    private int iteration;
    private final Map<String, Integer> statisticsMap;
    private int animalAmount;
    private int foodAmount;
    private int avgEnergy;
    private int avgLifespan;
    private final StatisticsView statisticsView;

    public Statistics(Board board) {
        this.board = board;
        this.iteration = 0;
        this.statisticsMap = new HashMap<>();
        this.animalAmount = 0;
        this.foodAmount = 0;
        this.avgEnergy = 0;
        this.avgLifespan = 0;
        updateStatisticsMap();
        this.statisticsView = new StatisticsView(statisticsMap);
    }

    public StackPane getView() {
        return statisticsView.getView();
    }

    public void incrementIteration() {
        this.iteration++;
        this.updateData();
        this.updateStatisticsMap();
    }

    public void updateData() {
        // Change all map values to 0
        Map<String, Integer> tempMap = new HashMap<>(statisticsMap);
        tempMap.forEach((key, value) -> tempMap.put(key, 0));

        // Collect the new data from board
        List<Integer> energyList = new ArrayList<>();
        List<Integer> lifespanList = new ArrayList<>();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Tile tile = board.getBoardMatrix()[x][y];
                // Increment foodAmount value
                if (tile.hasFood()) {
                    tempMap.put("Food amount", tempMap.get("Food amount") + 1);
                }
                // Get animal data
                List<Animal> animalList = tile.getAnimalList();
                // Get animalAmount
                tempMap.put("Animal amount", tempMap.get("Animal amount") + animalList.size());
                // Get animal energy and age
                for (Animal animal : animalList) {
                    energyList.add(animal.getEnergy());
                    lifespanList.add(animal.getAge());
                }
            }
        }
        animalAmount = tempMap.get("Animal amount");
        foodAmount = tempMap.get("Food amount");

        // Get avgEnergy and avgLifespan
        int energySum = energyList
                .stream()
                .reduce(0, Integer::sum);
        int lifespanSum = lifespanList
                .stream()
                .reduce(0, Integer::sum);
        avgEnergy = energySum/energyList.size();
        avgLifespan = lifespanSum/lifespanList.size();
    }

    private void updateStatisticsMap() {
        statisticsMap.put("Iteration", iteration);
        statisticsMap.put("Animal amount", animalAmount);
        statisticsMap.put("Food amount", foodAmount);
        statisticsMap.put("Average energy", avgEnergy);
        statisticsMap.put("Average lifespan", avgLifespan);
        if (this.statisticsView != null) {
            this.statisticsView.updateStatisticsView(statisticsMap);
        }
    }

    @Override
    public String toString() {
        return "Statistics " + statisticsMap.toString();
    }
}