package application;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.Map;

public class SimulationMenuView {
    private final StackPane stackPane;
    private final Map<String, Text> statTexts;
    private final Timeline timeline;

    public SimulationMenuView(Map<String, Integer> statisticsMap, Timeline timeline) {
        this.timeline = timeline;
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

        // Create top container
        HBox topContainer = new HBox(8);
        topContainer.setAlignment(Pos.CENTER_LEFT);
        // Create title
        Text title = new Text("Statistics");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.web("#C78C3F"));
        // Create the button
        Button simStateButton = getSimStateButtonView();
        // Create a spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Makes the spacer take up all the available space
        // Add elements to the topContainer
        topContainer.getChildren().addAll(title, spacer, simStateButton);

        // Create separator to add between title and texts
        Separator separator = new Separator();
        separator.setPrefWidth(200);
        separator.setHalignment(HPos.LEFT);
        separator.setPadding(new Insets(5, 0, 10, 0));
        separator.setBackground(new Background(bgFill));

        // Add title and separator to 'statContainer'
        statContainer.getChildren().addAll(topContainer, separator);

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

    private Button getSimStateButtonView() {
        Button simStateButton = getSimStateButtonLogic();
        // Style the button itself
        simStateButton.setStyle(
                "-fx-background-color: rgb(60, 60, 60);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 8 15;" +
                        "-fx-font-size: 14px;"
        );
        // Add hover effect
        simStateButton.setOnMouseEntered(e -> simStateButton.setStyle(
                "-fx-background-color: rgb(80, 80, 80);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 8 15;" +
                        "-fx-font-size: 14px;"
        ));
        simStateButton.setOnMouseExited(e -> simStateButton.setStyle(
                "-fx-background-color: rgb(60, 60, 60);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 8 15;" +
                        "-fx-font-size: 14px;"
        ));
        // Ensure the button uses fixed dimensions
        simStateButton.setPrefWidth(100);
        simStateButton.setMinWidth(Button.USE_PREF_SIZE);
        simStateButton.setMaxWidth(Button.USE_PREF_SIZE);
        simStateButton.setTextOverrun(OverrunStyle.ELLIPSIS); // Handle overflow
        return simStateButton;
    }

    private Button getSimStateButtonLogic() {
        Button simStateButton = new Button("Stop");
        simStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (timeline == null) {
                        throw new Exception("Timeline is null");
                    }
                    switch (timeline.getStatus()) {
                        case RUNNING:
                            timeline.pause();
                            simStateButton.setText("Resume");
                            break;
                        case PAUSED:
                            timeline.play();
                            simStateButton.setText("Stop");
                            break;
                        case STOPPED:
                            timeline.playFromStart();
                            simStateButton.setText("Stop");
                            break;
                        default:
                            throw new Exception("Unknown simulation status");
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });
        return simStateButton;
    }
}
