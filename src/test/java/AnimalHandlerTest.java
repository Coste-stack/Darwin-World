import animal.AnimalHandler;
import animal.Animal;
import area.Point;
import board.Board;
import board.BoardView;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalHandlerTest {

    @BeforeAll
    static void initJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @Test
    void testAnimalIsCreatedAndStoredCorrectly() {
        Board board = new Board(); // Create a 10x10 board
        BoardView boardView = new BoardView(board, 500, 500);
        AnimalHandler handler = new AnimalHandler(board, boardView);

        // Test creation of an animal at a specific point
        Point location = new Point(5, 5);
        handler.createAnimal(location);

        // Assert an animal exists
        assertEquals(1, handler.getAnimalList().size());
        Animal animal = handler.getAnimalList().getFirst();
        assertEquals(location, animal.getPosition());
    }

    @Test
    void testRunTurnMovesAnimals() {
        Board board = new Board();
        BoardView boardView = new BoardView(board,500, 500);
        AnimalHandler handler = new AnimalHandler(board, boardView);

        // Create a sample animal
        Point startPoint = new Point(3, 3);
        handler.createAnimal(new Point(3, 3));
        Animal animal = handler.getAnimalList().getFirst();

        // Before running a turn, animal is at the expected location
        assertEquals(startPoint.getX(), animal.getPosition().getX());
        assertEquals(startPoint.getY(), animal.getPosition().getY());

        // Run a turn and ensure the animal moves
        handler.moveAnimals();

        // After the turn, the animal’s position should change
        Point updatedPosition = animal.getPosition();
        assertTrue(startPoint.getX() != updatedPosition.getX()
                || startPoint.getY() != updatedPosition.getY()
        );

        // Assert it remains within board boundaries (if wrapping or limiting)
        assertTrue(updatedPosition.getX() >= 0 && updatedPosition.getX() < 10);
        assertTrue(updatedPosition.getY() >= 0 && updatedPosition.getY() < 10);
    }
}