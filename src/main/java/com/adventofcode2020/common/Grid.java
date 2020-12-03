package com.adventofcode2020.common;

public class Grid<T> {

    private final int width;
    private final int height;
    private final Object[][] grid;

    public Grid( int width, int height ) {
        this.width = width;
        this.height = height;
        this.grid = new Object[width][height];
    }

    public void set( int x, int y, T element ) {
        grid[x][y] = element;
    }

    public T get( int x, int y ) {
        //noinspection unchecked
        return (T) grid[x][y];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for ( int y = 0; y < height; ++y ) {
            for ( int x = 0; x < width; ++x ) {
                builder.append( grid[x][y] );
            }
            builder.append( '\n' );
        }
        return builder.toString();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
