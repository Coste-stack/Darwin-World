package animal;

public enum Direction {
    UP(0, 0, -1),            // Move up: Y++
    UP_RIGHT(45, 1, -1),     // Move diagonally right and up: X++, Y++
    RIGHT(90, 1, 0),        // Move right: X++
    DOWN_RIGHT(135, 1, 1), // Move diagonally right and down: X++, Y--
    DOWN(180, 0, 1),       // Move down: Y--
    DOWN_LEFT(225, -1, 1), // Move diagonally left and down: X--, Y--
    LEFT(270, -1, 0),       // Move left: X--
    UP_LEFT(315, -1, -1);    // Move diagonally left and up: X--, Y++

    private final int angle;  // Angle in degrees
    private final int deltaX; // Change in X for this direction
    private final int deltaY; // Change in Y for this direction

    Direction(int angle, int deltaX, int deltaY) {
        this.angle = angle;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getAngle() {
        return this.angle;
    }

    public int getDeltaX() {
        return this.deltaX;
    }

    public int getDeltaY() {
        return this.deltaY;
    }
}