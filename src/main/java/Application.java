import animal.AnimalHandler;
import area.*;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import chart.Plot;
import javafx.geometry.Orientation;
import utils.ConfigHandler;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set stage properties
        primaryStage.setWidth(ConfigHandler.getInstance().getConfig("SCREEN_WIDTH"));
        primaryStage.setHeight(ConfigHandler.getInstance().getConfig("SCREEN_HEIGHT"));

        // Create config input field container
        FlowPane inputContainer = new FlowPane(Orientation.VERTICAL);
        inputContainer.setHgap(20);
        inputContainer.setVgap(10);

        // Add all values in config to form
        ConfigHandler.getInstance().getConfig().forEach((key, value) -> {
            VBox formItem = new VBox();

            Label label = new Label(key);
            label.setStyle("-fx-text-fill:white;");
            formItem.getChildren().add(label);

            TextField input = new TextField(value.toString());
            input.setStyle("-fx-background-color:#4A64A4; -fx-text-fill:white;");
            formItem.getChildren().add(input);

            inputContainer.getChildren().add(formItem);
        });

        // Create startButton
        primaryStage.setTitle("Simulation");
        Button startButton = new Button();
        startButton.setStyle("-fx-background-color:#4A64A4; -fx-text-fill:white;");
        startButton.setPadding(new Insets(10, 10, 10, 10));
        startButton.setText("Show Gameboard");
        // Put the button in a container
        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().add(startButton);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);

        // Create whole form container
        VBox formLayout = new VBox();
        formLayout.setMaxWidth(formLayout.getWidth());
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(10, 10, 10, 10));
        formLayout.getChildren().addAll(inputContainer, buttonContainer);

        // Show scene
        StackPane root = new StackPane(formLayout);
        root.setStyle("-fx-background-color:#282A3A");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Add boardView on startButton click
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Board board = new Board();
                board.addPlot(new Plot());

                // Create the area.pole.Pole at the top and bottom
                Area NorthPole = new NorthPole(null, null).getArea();
                Area SouthPole = new SouthPole(null, null).getArea();
                // Create the area.Plains centered in the grid
                Area plains = new Plains(null, null).getArea();

                // Add areas to board matrix
                board.addArea(NorthPole);
                board.addArea(SouthPole);
                board.addArea(plains);
                // Determine preferred food tiles on board
                board.setFoodPreferredTiles();

                // Create grid visualization in window
                BoardView boardView = new BoardView(board,
                        ConfigHandler.getInstance().getConfig("SCREEN_WIDTH") / (float) 1.75,
                        ConfigHandler.getInstance().getConfig("SCREEN_HEIGHT") - 20
                );
                StackPane boardContainer = boardView.createBoard();

                // Create plot visualization - if plot exists
                StackPane plotContainer;
                if (board.getPlot() != null) {
                    plotContainer = board.getPlot().getLineChart();
                } else {
                    plotContainer = new StackPane();
                }

                // Create a scene
                HBox root = new HBox();
                root.getChildren().addAll(plotContainer, boardContainer);

                // Make both components scalable
                HBox.setHgrow(plotContainer, Priority.ALWAYS);
                HBox.setHgrow(boardContainer, Priority.ALWAYS);

                // Create animals
                AnimalHandler animalHandler = new AnimalHandler(board, boardView);
                for (int i = 0; i < 50; i++) {
                    animalHandler.createAnimal(new Point());
                }

                // Run the simulation
                board.setFoodRandomly();
                animalHandler.runTurn();
                boardView.refreshBoard();
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                    board.setFoodRandomly();
                    animalHandler.runTurn();
                    boardView.refreshBoard();
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

                Scene mainScene = new Scene(root);
                primaryStage.setScene(mainScene);
            }
        });
    }
}