package com.adventofcode2020.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

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

    @Override
    public boolean equals( Object o ) {
        if ( ! (o instanceof Grid) ) {
            return false;
        }
        Grid<T> that = (Grid<T>) o;
        return this.width == that.width
            && this.height == that.height
            && equals( this.grid, that.grid );
    }

    private boolean equals( Object[][] thisGrid, Object[][] thatGrid ) {
        for ( int x = 0; x < width; ++x ) {
            for ( int y = 0; y < height; ++y ) {
                if ( ! thisGrid[x][y].equals( thatGrid[x][y] ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public Grid<T> map( Predicate<T> neighbourFilter, BiFunction<T, List<T>, T> mapper ) {
        Grid<T> newGrid = new Grid<>( width, height );
        for ( int x = 0; x < width; ++x ) {
            for ( int y = 0; y < height; ++y ) {
                T element = get( x, y );
                T mappedElement = mapper.apply( element, getNeighbours( x, y, neighbourFilter ) );
                newGrid.set( x, y, mappedElement );
            }
        }
        return newGrid;
    }

    private List<T> getNeighbours( int x, int y, Predicate<T> neighbourFilter ) {
        List<T> neighbours = new ArrayList<>();
        findNeighbour( x, y, +1, -1, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y, +1,  0, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y, +1, +1, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y,  0, +1, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y, -1, +1, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y, -1,  0, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y, -1, -1, neighbourFilter ).ifPresent( neighbours::add );
        findNeighbour( x, y,  0, -1, neighbourFilter ).ifPresent( neighbours::add );
        return neighbours;
    }

    private Optional<T> findNeighbour( int x, int y, int deltaX, int deltaY, Predicate<T> neighbourFilter ) {
        while ( true ) {
            x += deltaX;
            y += deltaY;
            if ( ! withinBounds( x, y ) ) {
                return Optional.empty();
            }
            T element = get( x, y );
            if ( neighbourFilter.test( element ) ) {
                return Optional.of( element );
            }
        }
    }

    private boolean withinBounds( int x, int y ) {
        return
            x >= 0 && x < width
            &&
            y >= 0 && y < height;
    }

    public int count( Predicate<T> filter ) {
        int count = 0;
        for ( int x = 0; x < width; ++x ) {
            for ( int y = 0; y < height; ++y ) {
                if ( filter.test( get( x, y ) ) ) {
                    ++count;
                }
            }
        }
        return count;
    }
}
