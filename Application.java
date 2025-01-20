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
                int gridWidth = 64;
                int gridHeight = 64;
                Board board = new Board(gridWidth, gridHeight);

                // Create the Pole at the top
                Area pole = new Pole(null, null).getArea(gridWidth, gridHeight);

                // Create the Plains centered in the grid
                Area plains = new Plains(null, null).getArea(gridWidth, gridHeight);

                // Add areas to board matrix
                board.addArea(pole);
                board.addArea(plains);

                // Create grid visualization in window
                BoardView boardView = new BoardView(1280, 720);
                Scene boardScene = boardView.createBoard(board);
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