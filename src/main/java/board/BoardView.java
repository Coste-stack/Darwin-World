package board;

import animal.Animal;
import animal.AnimalView;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

public class BoardView {
    private final float SCREEN_WIDTH;
    private final float SCREEN_HEIGHT;
    private Board gridMatrix;
    private TilePane gridPanel;
    private int iteration = 1;

    public BoardView(Board gridMatrix, float SCREEN_WIDTH, float SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
        this.gridMatrix = gridMatrix;
    }

    public float calcTileSize() {
        int gridWidth = gridMatrix.getWidth();
        int gridHeight = gridMatrix.getHeight();
        float tileSizeWidth = (float) Math.floor(SCREEN_WIDTH / gridWidth);
        float tileSizeHeight = (float) Math.floor(SCREEN_HEIGHT / gridHeight);
        return Math.min(tileSizeWidth, tileSizeHeight);
    }

    public StackPane createBoard() {
        int gridWidth = gridMatrix.getWidth();
        int gridHeight = gridMatrix.getHeight();

        // Determine an appropriate tile size
        float tileSize = calcTileSize();

        // Determine the panel sizes
        float panelWidth = tileSize * gridWidth;
        float panelHeight = tileSize * gridHeight;

        // Create TilePane container - to set its width, height and alignment
        StackPane container = new StackPane();
        container.setPrefSize(panelWidth, panelHeight);
        container.setMaxSize(panelWidth, panelHeight);
        container.setMinSize(panelWidth, panelHeight);
        StackPane.setAlignment(container, Pos.CENTER_RIGHT);

        // Create the TilePane
        this.gridPanel = new TilePane();
        gridPanel.setOrientation(Orientation.VERTICAL);
        // Ensure no spacing between tiles
        gridPanel.setHgap(0);
        gridPanel.setVgap(0);
        // Apply specified column and row count
        gridPanel.setPrefRows(gridHeight);
        gridPanel.setPrefColumns(gridWidth);
        // Enforce tile size
        gridPanel.setPrefTileWidth(tileSize);
        gridPanel.setPrefTileHeight(tileSize);

        // Add tiles to the grid
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                // Use StackPane as a tile
                StackPane tile = new StackPane();
                tile.setStyle("-fx-background-color: " +
                        toRgbString(gridMatrix.getBoardMatrix()[i][j].getFillColor()) +
                        ";");

                gridPanel.getChildren().add(tile); // Add each tile to the TilePane
            }
        }
        container.getChildren().add(gridPanel);

        // Add a container to help align scene to the right
        StackPane root = new StackPane();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.setMaxSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(container);
        return root;
    }

    // Helper method to convert JavaFX Color to CSS-compatible RGB string
    private String toRgbString(Color color) {
        return "rgb(" +
                (int) (color.getRed() * 255) + "," +
                (int) (color.getGreen() * 255) + "," +
                (int) (color.getBlue() * 255) + ")";
    }

    public void refreshBoard() {
        if (gridPanel != null) {
            int gridWidth = gridMatrix.getWidth();
            int gridHeight = gridMatrix.getHeight();

            // Clear the current tiles in the gridPanel
            gridPanel.getChildren().clear();

            // Collect data for plot series
            int animalAmount = 0;
            int foodAmount = 0;

            // Re-create the tiles based on the current gridMatrix state
            for (int i = 0; i < gridWidth; i++) {
                for (int j = 0; j < gridHeight; j++) {
                    Tile tile = gridMatrix.getBoardMatrix()[i][j];
                    // Update food data for series
                    if (tile.hasFood()) {
                        foodAmount++;
                    }

                    // Create tile
                    GridPane tileView = new GridPane();
                    tileView.setStyle("-fx-background-color: " + toRgbString(gridMatrix.getBoardMatrix()[i][j].getFillColor()) + ";");

                    int row = 0;
                    int column = 0;
                    for (Animal animal : tile.getAnimalList()) {
                        AnimalView animalView = animal.getAnimalView();
                        if (animalView != null) {
                            // Display animalView in tile
                            if (row < 2) {
                                // if on the tile is more than one animal - decrease its size to allow up to 4
                                if (tile.getAnimalList().size() > 1) {
                                    animalView.scaleView(this.calcTileSize() / 4);
                                } else {
                                    animalView.scaleView(this.calcTileSize() / 2);
                                }
                                tileView.add(animalView.getStackPane(), column, row);
                                column++;
                                if (column >= 2) {
                                    column = 0;
                                    row++;
                                }
                            }
                        }
                    }

                    // Add tile to panel
                    gridPanel.getChildren().add(tileView);
                }
            }

            // Add data to plot
            gridMatrix.addAnimalSeriesData(iteration, animalAmount);
            gridMatrix.addFoodSeriesData(iteration, foodAmount);

            // Log iteration
            System.out.println("Iteration: " + iteration + " - Animals: " + animalAmount + " - Food: " + foodAmount);

            iteration++;
        }
    }
}