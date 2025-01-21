package animal;

import area.Point;
import board.Board;
import board.BoardView;

import java.util.ArrayList;
import java.util.List;

public class AnimalHandler {
    private final Board board;
    private final BoardView boardView;
    List<Animal> animalList;

    public AnimalHandler(Board board, BoardView boardView) {
        this.board = board;
        this.boardView = boardView;
        this.animalList = new ArrayList<>();
    }

    public void createAnimal(Point position) {
        Animal animal = new Animal(position, boardView.calcTileSize() / 2);
        board.addAnimal(animal);
        animalList.add(animal);
    }

    public void moveAnimals() {
        for (Animal animal : animalList) {
            // Get current position of the animal
            Point currentPosition = animal.getPosition();

            // Calculate the new position (move X+1 for debugging)
            int newX = currentPosition.getX() + 1;
            int newY = currentPosition.getY();

            // Ensure the new position is within the grid boundaries
            if (newX >= 0 && newX < board.getWidth() && newY >= 0 && newY < board.getHeight()) {
                // Remove the animal from its current position in the grid matrix
                board.getBoardMatrix()[currentPosition.getX()][currentPosition.getY()].setAnimal(null);

                // Update the animal's position
                currentPosition.setX(newX);
                currentPosition.setY(newY);

                // Place the animal in the new position in the grid matrix
                board.getBoardMatrix()[newX][newY].setAnimal(animal);
            }
        }

    }
}
