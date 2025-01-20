import javafx.scene.paint.Color;

public class Board {
    private Tile[][] boardMatrix;
    private int x;
    private int y;

    public Board(int x, int y) {
        this.x = x;
        this.y = y;
        this.boardMatrix = new Tile[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Tile tile = new Tile(Color.GRAY, Color.BLACK);
                this.boardMatrix[i][j] = tile;
            }
        }
    }

    public Tile[][] getBoardMatrix() {
        return this.boardMatrix;
    }
    public int getWidth() {
        return this.x;
    }
    public int getHeight() {
        return this.y;
    }
}