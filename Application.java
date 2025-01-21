import animal.Animal;
import area.*;

import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set stage properties
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);

        // Create startButton
        primaryStage.setTitle("Simulation");
        Button startButton = new Button();
        startButton.setText("Show Gameboard");
        // Add boardView on startButton click
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create the grid matrix
                int gridWidth = 32;
                int gridHeight = 32;
                Board board = new Board(gridWidth, gridHeight);

                // Create the area.pole.Pole at the top and bottom
                Area NorthPole = new NorthPole(null, null).getArea(gridWidth, gridHeight);
                Area SouthPole = new SouthPole(null, null).getArea(gridWidth, gridHeight);
                // Create the area.Plains centered in the grid
                Area plains = new Plains(null, null).getArea(gridWidth, gridHeight);

                // Add areas to board matrix
                board.addArea(NorthPole);
                board.addArea(SouthPole);
                board.addArea(plains);

                // Create grid visualization in window
                BoardView boardView = new BoardView(1280, 720);
                Scene boardScene = boardView.createBoard(board);

                // Create animal
                Animal animal = new Animal(
                        new Point(gridWidth / 2, gridHeight / 2),
                        boardView.calcTileSize() / 2
                );
                board.addAnimal(animal);
                boardView.refreshBoard();

                primaryStage.setScene(boardScene);
            }
        });
        StackPane root = new StackPane(); // create a layout
        root.getChildren().add(startButton); // add startButton to it
        // Create and show scene
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}