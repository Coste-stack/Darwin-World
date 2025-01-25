import animal.AnimalHandler;
import area.*;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import chart.Plot;
import utils.ConfigHandler;
import utils.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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

        // Create startButton
        primaryStage.setTitle("Simulation");
        Button startButton = new Button();
        startButton.setText("Show Gameboard");
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

                Scene mainScene = new Scene(root);

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

                primaryStage.setScene(mainScene);
            }
        });
        StackPane root = new StackPane(); // create a layout
        root.getChildren().add(startButton); // add startButton to it
        // Create and show scene
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}