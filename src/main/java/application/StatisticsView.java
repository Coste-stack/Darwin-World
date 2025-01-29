package application;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class StatisticsView {
    private final StackPane stackPane;
    private final Map<String, Text> statTexts;

    public StatisticsView(Map<String, Integer> statisticsMap) {
        this.statTexts = new HashMap<>();

        // Create the containers
        this.stackPane = new StackPane();
        VBox statContainer = new VBox();
        statContainer.setPadding(new Insets(10, 10, 10,10));

        // Add title and all stat texts to 'statContainer'
        Text title = new Text("Statistics");
        title.setFill(Color.WHITE);
        statContainer.getChildren().add(title);
        statisticsMap.forEach((key, value) -> {
            Text text = new Text(key + ": " + value);
            text.setFill(Color.WHITE);
            statContainer.getChildren().add(text);
            this.statTexts.put(key, text);
        });
        this.stackPane.getChildren().add(statContainer);
    }

    public StackPane getView() {
        return this.stackPane;
    }

    public void updateStatisticsView(Map<String, Integer> statisticsMap) {
        statisticsMap.forEach((key, value) -> {
            Text text = this.statTexts.get(key);
            if (text != null) {
                text.setText(key + ": " + value);
            } else {
                System.err.println("Warning: Key '" + key + "' not found in statTexts map.");
            }
        });
    }
}
