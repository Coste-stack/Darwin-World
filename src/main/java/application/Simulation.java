package application;

import animal.AnimalHandler;
import area.Area;
import area.Grassfield;
import area.Point;
import area.pole.NorthPole;
import area.pole.SouthPole;
import board.Board;
import board.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import stats.Plot;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
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
    private BoardView boardView;
    private AnimalHandler animalHandler;
    private final Statistics statistics;
    private final StackPane simulationView;
    private Timeline timeline;

    public Simulation() {
        this.board = new Board();
        this.board.addPlot(new Plot());
        this.statistics = new Statistics(board);
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

        // Get text statistics
        StackPane statisticsView = this.statistics.getView();

        // Create stackpane to manipulate simulation state
        StackPane simManipView = new StackPane();

        Button simStateButton = getSimStateButton();

        simManipView.getChildren().add(simStateButton);

        // Put elements into bottom statistics container
        HBox bottomStatistics = new HBox();
        bottomStatistics.getChildren().addAll(statisticsView, simManipView);

        // Put statistics related objects to a container
        VBox statContainer = new VBox(20);
        statContainer.setPadding(new Insets(10, 10, 10,10));
        statContainer.getChildren().addAll(plotContainer, bottomStatistics);

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

    private Button getSimStateButton() {
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

    public void run() {
        runOnce();
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            runOnce();
        }));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    private void runOnce() {
        board.setFoodRandomly();
        animalHandler.runTurn();
        boardView.refreshBoard();
        statistics.incrementIteration();
    }
}
