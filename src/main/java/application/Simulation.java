package application;

import animal.AnimalHandler;
import area.Area;
import area.Grassfield;
import area.Point;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import stats.Plot;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.ConfigHandler;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

public class Simulation {
    private final Board board;
    private AnimalHandler animalHandler;
    private final Statistics statistics;
    private BoardView boardView;
    private final StackPane simulationView;

    public Simulation() {
        this.board = new Board();
        this.board.addPlot(new Plot());
        this.statistics = new Statistics();
        this.simulationView = this.createView();
    }

    public StackPane getView() {
        return this.simulationView;
    }

    private StackPane createView() {
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

        // Put statistics related objects to a container
        VBox statContainer = new VBox();
        statContainer.setPadding(new Insets(10, 10, 10,10));
        statContainer.getChildren().addAll(plotContainer, this.statistics.getView());

        // Create a scene
        HBox root = new HBox();
        root.setStyle("-fx-background-color: #282A3A");
        root.getChildren().addAll(statContainer, boardContainer);

        // Make both components scalable
        HBox.setHgrow(statContainer, Priority.ALWAYS);
        HBox.setHgrow(boardContainer, Priority.ALWAYS);

        // Create animals
        animalHandler = new AnimalHandler(board, boardView);
        for (int i = 0; i < 50; i++) {
            animalHandler.createAnimal(new Point());
        }

        return new StackPane(root);
    }

    public void run() {
        runOnce();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            runOnce();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void runOnce() {
        board.setFoodRandomly();
        animalHandler.runTurn();
        boardView.refreshBoard();
        statistics.incrementIteration();
    }
}
