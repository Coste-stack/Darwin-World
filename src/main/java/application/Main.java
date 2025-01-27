package application;

import animal.AnimalHandler;
import area.*;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import chart.Plot;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.ConfigHandler;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Main extends javafx.application.Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Set stage properties
        primaryStage.setWidth(ConfigHandler.getInstance().getConfigValue("SCREEN_WIDTH"));
        primaryStage.setHeight(ConfigHandler.getInstance().getConfigValue("SCREEN_HEIGHT"));

        // Create starting menu - formView
        Form form = new Form();
        StackPane root = form.getView();
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Run simulation on button click - create boardView
        form.getFormButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    form.validateForm();
                    form.changeConfig();
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.show();
                    return;
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.show();
                    return;
                }


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
                        ConfigHandler.getInstance().getConfigValue("SCREEN_WIDTH") / (float) 1.75,
                        ConfigHandler.getInstance().getConfigValue("SCREEN_HEIGHT") - 20
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