import board.Board;
import board.Tile;
import chart.Plot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testBoardInitializationCreatesCorrectDimensions() {
        int rows = 10;
        int columns = 15;
        Board board = new Board();

        // Verify dimensions of the board matrix
        Tile[][] matrix = board.getBoardMatrix();
        assertEquals(rows, matrix.length); // Rows
        assertEquals(columns, matrix[0].length); // Columns
    }

    @Test
    void testAssignRandomFoodProbability() {
        Board board = new Board();
        Tile[][] matrix = board.getBoardMatrix();

        // Ensure no food initially
        for (Tile[] row : matrix) {
            for (Tile tile : row) {
                assertFalse(tile.hasFood());
            }
        }

        // Place food randomly
        board.setFoodRandomly();

        // Verify that some tiles now have food
        boolean foodPlaced = false;
        for (Tile[] row : matrix) {
            for (Tile tile : row) {
                if (tile.hasFood()) {
                    foodPlaced = true;
                    break;
                }
            }
        }
        assertTrue(foodPlaced); // There should be at least one tile with food
    }
}