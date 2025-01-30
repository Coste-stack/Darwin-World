package application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class StatisticsView {
    private final StackPane stackPane;
    private final Map<String, Text> statTexts;

    public StatisticsView(Map<String, Integer> statisticsMap) {
        this.statTexts = new HashMap<>();

        // Create the 'statContainer'
        this.stackPane = new StackPane();
        this.stackPane.setPadding(new Insets(10, 25, 10, 25));
        VBox statContainer = new VBox(8);
        statContainer.setPadding(new Insets(10, 10, 10,10));
        // Style the background
        BackgroundFill bgFill = new BackgroundFill(
                Color.rgb(40, 40, 40, 0.9),
                new CornerRadii(10),
                Insets.EMPTY
        );
        statContainer.setBackground(new Background(bgFill));
        // Add drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.7));
        shadow.setRadius(15);
        statContainer.setEffect(shadow);

        // Create title
        Text title = new Text("Statistics");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.web("#C78C3F"));

        // Create separator to add between title and texts
        Separator separator = new Separator();
        separator.setPrefWidth(200);
        separator.setHalignment(HPos.LEFT);
        separator.setPadding(new Insets(5, 0, 10, 0));
        separator.setBackground(new Background(bgFill));

        // Add title and separator to 'statContainer'
        statContainer.getChildren().addAll(title, separator);

        // Add all stat texts to 'statContainer'
        statisticsMap.forEach((key, value) -> {
            HBox entry = new HBox(10);
            entry.setAlignment(Pos.CENTER_LEFT);

            Text label = new Text(key + ":");
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setFill(Color.web("#A0A6B5"));

            Text valueText = new Text(Integer.toString(value));
            valueText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            valueText.setFill(Color.LIGHTGRAY);

            entry.getChildren().addAll(label, valueText);
            statContainer.getChildren().add(entry);
            this.statTexts.put(key, valueText);
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
                text.setText(Integer.toString(value));
            } else {
                System.err.println("Warning: Key '" + key + "' not found in statTexts map.");
            }
        });
    }
}
