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
        // Create start button
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Show Gameboard");
        // Add logic to start button
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Board board = new Board(8, 8);
                BoardView boardView = new BoardView(8, 8);
                Scene boardScene = boardView.createBoard(board);
                primaryStage.setScene(boardScene);
            }
        });
        StackPane root = new StackPane(); // create a layout
        root.getChildren().add(btn); // add start button to it
        // Create and show scene
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}