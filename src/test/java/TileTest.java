import area.Area;
import area.Plains;
import board.Board;
import board.Tile;
import animal.Animal;
import area.Point;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void testAddAnimalToTile() {
        Tile tile = new Tile(Color.GREEN);

        // Add an animal
        Animal animal = new Animal(new Point(2, 2));
        tile.addAnimal(animal);

        // Ensure animal was added
        assertEquals(1, tile.getAnimalList().size());
        assertEquals(animal, tile.getAnimalList().getFirst());
    }

    @Test
    void testTileWithFoodAllowsConsumption() {
        Tile tile = new Tile(Color.GRAY);
        Area plains = new Plains(null, null);
        tile.setArea(plains);

        // Place food on the tile
        tile.setHasFood(true);
        assertTrue(tile.hasFood());

        // Remove food and verify
        tile.setHasFood(false);
        assertFalse(tile.hasFood());
    }
}