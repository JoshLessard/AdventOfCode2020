package com.adventofcode2020.dec20;

import java.util.EnumMap;
import java.util.Map;

class Tile {

    private final int id;
    private final int sideLength;
    private final TileSpace[][] tileSpaces;
    private final Map<TileSide, Integer> sideValues = new EnumMap<>( TileSide.class );
    private final Map<TileSide, Integer> flippedSideValues = new EnumMap<>( TileSide.class );

    Tile( int id, TileSpace[][] tileSpaces ) {
        this.id = id;
        this.sideLength = tileSpaces.length;
        this.tileSpaces = tileSpaces;

        storeSideValues( TileSide.TOP, getTop() );
        storeSideValues( TileSide.RIGHT, getRight() );
        storeSideValues( TileSide.BOTTOM, getBottom() );
        storeSideValues( TileSide.LEFT, getLeft() );
    }

    private void storeSideValues( TileSide side, TileSpace[] sideSpaces ) {
        sideValues.put( side, calculateSideValue( sideSpaces ) );
        flippedSideValues.put( side, calculateFlippedSideValue( sideSpaces ) );
    }

    private int calculateSideValue( TileSpace[] side ) {
        int value = 0;
        for ( int i = 0; i < sideLength; ++i ) {
            TileSpace tileSpace = side[i];
            if ( tileSpace == TileSpace.OCCUPIED ) {
                value += (int) Math.pow( 2, sideLength - i - 1 );
            }
        }
        return value;
    }

    private int calculateFlippedSideValue( TileSpace[] side ) {
        int value = 0;
        for ( int i = 0; i < sideLength; ++i ) {
            TileSpace tileSpace = side[i];
            if ( tileSpace == TileSpace.OCCUPIED ) {
                value += (int) Math.pow( 2, i );
            }
        }
        return value;
    }

    private TileSpace[] getTop() {
        TileSpace[] top = new TileSpace[sideLength];
        for ( int i = 0; i < sideLength; ++i ) {
            top[i] = tileSpaces[0][i];
        }
        return top;
    }

    private TileSpace[] getRight() {
        TileSpace[] right = new TileSpace[sideLength];
        for ( int i = 0; i < sideLength; ++i ) {
            right[i] = tileSpaces[i][sideLength - 1];
        }
        return right;
    }

    private TileSpace[] getBottom() {
        TileSpace[] bottom = new TileSpace[sideLength];
        for ( int i = 0; i < sideLength; ++i ) {
            bottom[i] = tileSpaces[sideLength - 1][sideLength - i - 1];
        }
        return bottom;
    }

    private TileSpace[] getLeft() {
        TileSpace[] left = new TileSpace[sideLength];
        for ( int i = 0; i < sideLength; ++i ) {
            left[i] = tileSpaces[sideLength - i - 1][0];
        }
        return left;
    }

    int id() {
        return id;
    }

    int sideValue( TileSide side ) {
        return sideValues.get( side );
    }

    int flippedSideValue( TileSide side ) {
        return flippedSideValues.get( side );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder( "Tile " + id + ":\n" );
        for ( TileSpace[] row : tileSpaces ) {
            for ( TileSpace tileSpace : row ) {
                builder.append( tileSpace );
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
