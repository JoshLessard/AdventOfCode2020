package com.adventofcode2020.common;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Point {

    private final int x;
    private final int y;

    public Point( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Point transform( Delta delta ) {
        return new Point( x + delta.getDeltaX(), y + delta.getDeltaY() );
    }

    public int manhattanDistanceFrom( Point point ) {
        return Math.abs( this.x - point.x ) + Math.abs( this.y - point.y );
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Point rotateAbout( Point anchor, double radians ) {
        return new Point(
            (int) Math.round( cos( radians ) * (this.x - anchor.x) - sin( radians ) * (this.y - anchor.y) + anchor.x ),
            (int) Math.round( sin( radians ) * (this.x - anchor.x) + cos( radians ) * (this.y - anchor.y) + anchor.y )
        );
    }

    public Delta deltaFrom( Point point ) {
        return new Delta(
            this.x - point.x,
            this.y - point.y
        );
    }
}
