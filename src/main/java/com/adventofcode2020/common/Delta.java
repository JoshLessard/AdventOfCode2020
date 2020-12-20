package com.adventofcode2020.common;

public class Delta {

    private final int deltaX;
    private final int deltaY;

    public Delta( int deltaX, int deltaY ) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    int getDeltaX() {
        return deltaX;
    }

    int getDeltaY() {
        return deltaY;
    }
}
