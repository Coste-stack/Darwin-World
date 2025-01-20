import javafx.scene.paint.Color;

public class Board {
    private final Tile[][] boardMatrix;
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;

    public Board(int x, int y) {
        this.BOARD_WIDTH = x;
        this.BOARD_HEIGHT = y;
        this.boardMatrix = new Tile[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Color fillColor = (i + j) % 2 == 0 ? Color.GRAY : Color.BLACK;
                Tile tile = new Tile(fillColor);
                this.boardMatrix[i][j] = tile;
            }
        }
    }

    public Tile[][] getBoardMatrix() {
        return this.boardMatrix;
    }
    public int getWidth() {
        return this.BOARD_WIDTH;
    }
    public int getHeight() {
        return this.BOARD_HEIGHT;
    }

    public void addArea(Area area) {
        for (int i = area.getTopLeft().x(); i < area.getBottomRight().x(); i++) {
            for (int j = area.getTopLeft().y(); j < area.getBottomRight().y(); j++) {
                this.boardMatrix[i][j].setArea(area);
            }
        }
    }

    public void addAnimal(Animal animal) {
        this.boardMatrix[animal.getPosition().x()][animal.getPosition().y()].setAnimal(animal);
    }
}