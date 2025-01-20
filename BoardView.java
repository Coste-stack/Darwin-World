import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

public class BoardView {
    private final double SCREEN_WIDTH;
    private final double SCREEN_HEIGHT;

    public BoardView(double SCREEN_WIDTH, double SCREEN_HEIGHT) {
        this.SCREEN_WIDTH = SCREEN_WIDTH;
        this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    public Scene createBoard(Board gridMatrix) {
        int gridWidth = gridMatrix.getWidth();
        int gridHeight = gridMatrix.getHeight();

        // Determine an appropriate tile size
        double tileSizeWidth = Math.floor(SCREEN_WIDTH / gridWidth);
        double tileSizeHeight = Math.floor(SCREEN_HEIGHT / gridHeight);
        double tileSize = Math.min(tileSizeWidth, tileSizeHeight);

        // Determine the panel sizes
        double panelWidth = tileSize * gridWidth;
        double panelHeight = tileSize * gridHeight;

        // Create TilePane container - to set its width, height and alignment
        StackPane container = new StackPane();
        container.setPrefSize(panelWidth, panelHeight);
        container.setMaxSize(panelWidth, panelHeight);
        container.setMinSize(panelWidth, panelHeight);
        StackPane.setAlignment(container, Pos.CENTER_RIGHT);

        // Create the TilePane
        TilePane gridPanel = new TilePane();
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

        // Create the scene with the root StackPane
        Scene boardScene = new Scene(root);
        return boardScene;
    }

    // Helper method to convert JavaFX Color to CSS-compatible RGB string
    private String toRgbString(Color color) {
        return "rgb(" +
                (int) (color.getRed() * 255) + "," +
                (int) (color.getGreen() * 255) + "," +
                (int) (color.getBlue() * 255) + ")";
    }
}