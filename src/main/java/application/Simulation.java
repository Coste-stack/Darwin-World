package application;

import animal.AnimalHandler;
import area.Area;
import area.Grassfield;
import area.Plains;
import area.Point;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import chart.Plot;
import javafx.geometry.Insets;
import utils.ConfigHandler;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

public class Simulation {
    private Board board;
    private BoardView boardView;
    private AnimalHandler animalHandler;
    private final StackPane simulationView;

    public Simulation() {
        this.simulationView = this.createView();
    }

    public StackPane getView() {
        return this.simulationView;
    }

    private StackPane createView() {
        board = new Board();
        board.addPlot(new Plot());

        // Create the area.pole.Pole at the top and bottom
        Area NorthPole = new NorthPole(null, null).getArea();
        Area SouthPole = new SouthPole(null, null).getArea();
        // Create the area.Plains centered in the grid
        Area grassfield = new Grassfield(null, null).getArea();

        // Add areas to board matrix
        board.addArea(NorthPole);
        board.addArea(SouthPole);
        board.addArea(grassfield);
        // Determine preferred food tiles on board
        board.setFoodPreferredTiles();

        // Create grid visualization in window
        boardView = new BoardView(board,
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
        plotContainer.setPadding(new Insets(10, 0, 10,0));

        // Create a scene
        HBox root = new HBox();
        root.setStyle("-fx-background-color: #282A3A");
        root.getChildren().addAll(plotContainer, boardContainer);

        // Make both components scalable
        HBox.setHgrow(plotContainer, Priority.ALWAYS);
        HBox.setHgrow(boardContainer, Priority.ALWAYS);

        // Create animals
        animalHandler = new AnimalHandler(board, boardView);
        for (int i = 0; i < 50; i++) {
            animalHandler.createAnimal(new Point());
        }

        return new StackPane(root);
    }

    public void run() {
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
    }

}
