package com.myapp.animal;

import com.myapp.utils.Random;

public enum Direction {
    UP          ( 0, -1),
    DOWN        ( 0,  1),
    RIGHT       ( 1,  0),
    LEFT        (-1,  0),
    UP_RIGHT    ( 1, -1),
    UP_LEFT     (-1, -1),
    DOWN_RIGHT  ( 1,  1),
    DOWN_LEFT   (-1,  1);


    private final int deltaX; // Change in X for this direction
    private final int deltaY; // Change in Y for this direction

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public static Direction getRandomDirection()  {
        Direction[] directions = values();
        return directions[Random.getRandom(0, directions.length-1)];
    }

    public int getDeltaX() {
        return this.deltaX;
    }

    public int getDeltaY() {
        return this.deltaY;
    }
}