import board.Board;
import board.Tile;
import chart.Plot;
import org.junit.jupiter.api.Test;
import utils.ConfigHandler;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testBoardInitializationCreatesCorrectDimensions() {
        Board board = new Board();

        // Verify dimensions of the board matrix
        Tile[][] matrix = board.getBoardMatrix();
        assertEquals(ConfigHandler.getInstance().getConfig("BOARD_WIDTH"), matrix.length); // Rows
        assertEquals(ConfigHandler.getInstance().getConfig("BOARD_HEIGHT"), matrix[0].length); // Columns
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