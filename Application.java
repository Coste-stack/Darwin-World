import animal.AnimalHandler;
import area.*;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;

import chart.Plot;
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
import utils.Random;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) {
        // Set stage properties

        primaryStage.setWidth(SCREEN_WIDTH);
        primaryStage.setHeight(SCREEN_HEIGHT);

        // Create startButton
        primaryStage.setTitle("Simulation");
        Button startButton = new Button();
        startButton.setText("Show Gameboard");
        // Add boardView on startButton click
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create plot for grid
                Plot plot = new Plot();

                // Create the grid matrix
                int gridWidth = 16;
                int gridHeight = 16;
                Board board = new Board(gridWidth, gridHeight, plot);

                // Create the area.pole.Pole at the top and bottom
                Area NorthPole = new NorthPole(null, null).getArea(gridWidth, gridHeight);
                Area SouthPole = new SouthPole(null, null).getArea(gridWidth, gridHeight);
                // Create the area.Plains centered in the grid
                Area plains = new Plains(null, null).getArea(gridWidth, gridHeight);

                // Add areas to board matrix
                board.addArea(NorthPole);
                board.addArea(SouthPole);
                board.addArea(plains);
                // Set food on board
                board.setFoodPreferredTiles();
                board.setFoodRandomly();

                // Create grid visualization in window
                BoardView boardView = new BoardView(SCREEN_WIDTH / (float) 1.75, SCREEN_HEIGHT - 20);
                StackPane boardContainer = boardView.createBoard(board);

                // Create plot visualization
                StackPane plotContainer = plot.getLineChart();

                // Create a scene
                HBox root = new HBox();
                root.getChildren().addAll(plotContainer, boardContainer);

                // Make both components scalable
                HBox.setHgrow(plotContainer, Priority.ALWAYS);
                HBox.setHgrow(boardContainer, Priority.ALWAYS);

                Scene mainScene = new Scene(root);

                // Create animals
                AnimalHandler animalHandler = new AnimalHandler(board, boardView);
                animalHandler.createAnimal(new Point(gridWidth / 2, gridHeight / 2));
                animalHandler.createAnimal(new Point(0, 0));
                for (int i = 0; i < 25; i++) {
                    animalHandler.createAnimal(new Point(Random.getRandom(0, board.getWidth()-1), Random.getRandom(0, board.getHeight()-1)));
                }
                boardView.refreshBoard();

                // Move animals
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
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