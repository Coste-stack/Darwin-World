package chart;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class Plot {
    private final LineChart<Number, Number> lineChart;
    private final XYChart.Series<Number, Number> series;
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

        series = new XYChart.Series<>();
        series.setName("Animals");

        //Setting the data to Line chart
        lineChart.getData().add(series);
    }

    public StackPane getLineChart() {
        StackPane lineChartContainer = new StackPane();
        lineChartContainer.getChildren().add(lineChart);
        return lineChartContainer;
    }

    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

    public NumberAxis getXAxis() {
        return xAxis;
    }

    public NumberAxis getYAxis() {
        return yAxis;
    }
}