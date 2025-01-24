package com.myapp.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    // Private constructor to prevent instantiation
    private Random() {}

    // Static method to generate a random number in the range [min, max]
    public static int getRandom(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min cannot be greater than Max.");
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}