package animal;

import area.Point;
import board.Board;
import board.BoardView;

public class AnimalHandler {
    private final Board board;
    private final BoardView boardView;

    public AnimalHandler(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
    }

    public void createAnimal(Point position) {
        Animal animal = new Animal(position, boardView.calcTileSize() / 2);
        board.addAnimal(animal);
    }
}
