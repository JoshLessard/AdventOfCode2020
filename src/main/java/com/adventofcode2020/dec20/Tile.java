package com.adventofcode2020.dec20;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

class Tile {

    private final int id;
    private final int sideLength;
    private final TileSpace[][] tileSpaces;

    private final Map<TileSide, TileSide> sideMap = new EnumMap<>( TileSide.class );
    private boolean flippedHorizontally = false;

    Tile( int id, TileSpace[][] tileSpaces ) {
        this.id = id;
        this.sideLength = tileSpaces.length;
        this.tileSpaces = tileSpaces;

        sideMap.put( TileSide.TOP, TileSide.TOP );
        sideMap.put( TileSide.RIGHT, TileSide.RIGHT );
        sideMap.put( TileSide.BOTTOM, TileSide.BOTTOM );
        sideMap.put( TileSide.LEFT, TileSide.LEFT );
    }

    int id() {
        return id;
    }

    int sideValue( TileSide side ) {
        TileSide mappedSide = sideMap.get( side );
        TileSpace[] row = row( 0, mappedSide );
        return calculateSideValue( row );
    }

    private int calculateSideValue( TileSpace[] side ) {
        int value = 0;
        for ( int i = 0; i < side.length; ++i ) {
            TileSpace tileSpace = side[i];
            if ( tileSpace == TileSpace.OCCUPIED ) {
                value += (int) Math.pow( 2, side.length - i - 1 );
            }
        }
        return value;
    }

    int sideComplementValue( TileSide side ) {
        TileSide mappedSide = sideMap.get( side );
        TileSpace[] row = row( 0, mappedSide );
        return calculateSideComplementValue( row );
    }

    private int calculateSideComplementValue( TileSpace[] side ) {
        int value = 0;
        for ( int i = 0; i < side.length; ++i ) {
            TileSpace tileSpace = side[i];
            if ( tileSpace == TileSpace.OCCUPIED ) {
                value += (int) Math.pow( 2, i );
            }
        }
        return value;
    }

    void rotateClockwise() {
        TileSide oldMappedTopSide = sideMap.get( TileSide.TOP );
        sideMap.put( TileSide.TOP, sideMap.get( TileSide.LEFT ) );
        sideMap.put( TileSide.LEFT, sideMap.get( TileSide.BOTTOM ) );
        sideMap.put( TileSide.BOTTOM, sideMap.get( TileSide.RIGHT ) );
        sideMap.put( TileSide.RIGHT, oldMappedTopSide );
    }

    void flipHorizontally() {
        TileSide oldMappedLeftSide = sideMap.get( TileSide.LEFT );
        sideMap.put( TileSide.LEFT, sideMap.get( TileSide.RIGHT ) );
        sideMap.put( TileSide.RIGHT, oldMappedLeftSide );
        flippedHorizontally = ! flippedHorizontally;
    }

    boolean hasSideValue( int sideValue ) {
        return Stream.of( TileSide.values() )
            .mapToInt( this::sideValue )
            .filter( sv -> sv == sideValue )
            .findAny()
            .isPresent();
    }

    int sideLength() {
        return sideLength;
    }

    TileSpace[] row( int rowIndex ) {
        return row( rowIndex, sideMap.get( TileSide.TOP ) );
    }

    private TileSpace[] row( int rowIndex, TileSide side ) {
        switch ( side ) {
            case TOP:
                return getRowFromTop( rowIndex );
            case RIGHT:
                return getRowFromRight( rowIndex );
            case BOTTOM:
                return getRowFromBottom( rowIndex );
            case LEFT:
                return getRowFromLeft( rowIndex );
        }
        throw new IllegalStateException( "Tile's top row wasn't mapped properly." );
    }

    private TileSpace[] getRowFromTop( int rowIndex ) {
        TileSpace[] row = new TileSpace[sideLength];
        int y = rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int x = sideLength - 1; x >= 0; --x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int x = 0; x < sideLength; ++x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    private TileSpace[] getRowFromRight( int rowIndex ) {
        TileSpace[] row = new TileSpace[sideLength];
        int x = sideLength - 1 - rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int y = sideLength - 1; y >= 0; --y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int y = 0; y < sideLength; ++y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    private TileSpace[] getRowFromBottom( int rowIndex ) {
        TileSpace[] row = new TileSpace[sideLength];
        int y = sideLength - 1 - rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int x = 0; x < sideLength; ++x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int x = sideLength - 1; x >= 0; --x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    private TileSpace[] getRowFromLeft( int rowIndex ) {
        TileSpace[] row = new TileSpace[sideLength];
        int x = rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int y = 0; y < sideLength; ++y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int y = sideLength - 1; y >= 0; --y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder( "Tile " + id + ":\n" );
        for ( int rowIndex = 0; rowIndex < sideLength; ++rowIndex ) {
            TileSpace[] row = row( rowIndex );
            for ( int columnIndex = 0; columnIndex < sideLength; ++columnIndex ) {
                builder.append( row[columnIndex] );
            }
            builder.append( '\n' );
        }
        return builder.toString();
    }
}
