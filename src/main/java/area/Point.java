package area;

import utils.ConfigHandler;
import utils.Random;

public class Point {
    private int x;
    private int y;

    public Point() {
        this.x = Random.getRandom(0, ConfigHandler.getInstance().getConfigValue("BOARD_WIDTH")-1);
        this.y = Random.getRandom(0, ConfigHandler.getInstance().getConfigValue("BOARD_HEIGHT")-1);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}