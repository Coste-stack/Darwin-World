package application;

import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private int iteration;
    private final Map<String, Integer> statisticsMap;
    private int animalAmount;
    private int foodAmount;
    private int avgEnergy;
    private int avgLifespan;
    private final StatisticsView statisticsView;

    public Statistics() {
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

    public Map<String, Integer> getStatisticsMap() {
        return new HashMap<>(statisticsMap);
    }

    public void incrementIteration() {
        iteration++;
        updateStatisticsMap();
    }
    public int getIteration() {
        return iteration;
    }

    public int getAnimalAmount() {
        return animalAmount;
    }
    public void setAnimalAmount(int animalAmount) {
        this.animalAmount = animalAmount;
        updateStatisticsMap();
    }

    public int getFoodAmount() {
        return foodAmount;
    }
    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
        updateStatisticsMap();
    }

    public int getAvgEnergy() {
        return avgEnergy;
    }
    public void setAvgEnergy(int avgEnergy) {
        this.avgEnergy = avgEnergy;
        updateStatisticsMap();
    }

    public int getAvgLifespan() {
        return avgLifespan;
    }
    public void setAvgLifespan(int avgLifespan) {
        this.avgLifespan = avgLifespan;
        updateStatisticsMap();
    }

    @Override
    public String toString() {
        return "Statistics " + statisticsMap.toString();
    }
}