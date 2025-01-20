import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class BoardView {
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;

    public BoardView(int BOARD_WIDTH, int BOARD_HEIGHT) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
    }

    public Scene createBoard(Board gridMatrix) {
        GridPane gridPanel = new GridPane();
        for (int i = 0; i < this.BOARD_WIDTH; i++) {
            for (int j = 0; j < this.BOARD_HEIGHT; j++) {
                Rectangle tile = new Rectangle(50, 50);
                tile.setFill(gridMatrix.getBoardMatrix()[i][j].getFillColor()); // Alternate colors
                tile.setStroke(gridMatrix.getBoardMatrix()[i][j].getStrokeColor());

                gridPanel.add(tile, j, i); // Add tile to the grid at column j, row i
            }
        }

        Scene scene = new Scene(gridPanel, this.BOARD_WIDTH * 50, this.BOARD_HEIGHT * 50);
        return scene;
    }
}
