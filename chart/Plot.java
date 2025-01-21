package chart;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class Plot {
    private final LineChart<Number, Number> lineChart;
    private final XYChart.Series<Number, Number> animalSeries;
    private final XYChart.Series<Number, Number> foodSeries;
    private final NumberAxis xAxis;
    private final NumberAxis yAxis;

    public Plot() {
        // Defining X axis
        xAxis = new NumberAxis(0, 10, 1);
        xAxis.setLabel("Iteration");

        // Defining Y axis
        yAxis = new NumberAxis(0, 10, 1);
        yAxis.setLabel("Amount");

        // Create the LineChart
        lineChart = new LineChart<>(xAxis, yAxis);

        // Create series for animal amount
        animalSeries = new XYChart.Series<>();
        animalSeries.setName("Animals");
        // Create series for food amount
        foodSeries = new XYChart.Series<>();
        foodSeries.setName("Food");

        //Setting the data to Line chart
        lineChart.getData().add(animalSeries);
        lineChart.getData().add(foodSeries);
    }

    public StackPane getLineChart() {
        StackPane lineChartContainer = new StackPane();
        lineChartContainer.getChildren().add(lineChart);
        return lineChartContainer;
    }

    public XYChart.Series<Number, Number> getAnimalSeries() {
        return animalSeries;
    }

    public XYChart.Series<Number, Number> getFoodSeries() {
        return foodSeries;
    }

    public NumberAxis getXAxis() {
        return xAxis;
    }

    public NumberAxis getYAxis() {
        return yAxis;
    }
}