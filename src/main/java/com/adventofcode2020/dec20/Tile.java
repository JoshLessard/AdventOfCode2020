package com.adventofcode2020.dec20;

import java.util.EnumMap;
import java.util.Map;

class Tile {

    private final int id;
    private final int sideLength;
    private final TileSpace[][] tileSpaces;
    private final Map<TileSide, Integer> sideValues = new EnumMap<>( TileSide.class );
    private final Map<TileSide, Integer> sideComplementValues = new EnumMap<>( TileSide.class );

    private final Map<TileSide, TileSide> sideMap = new EnumMap<>( TileSide.class );
    private boolean flippedHorizontally = false;

    Tile( int id, TileSpace[][] tileSpaces ) {
        this.id = id;
        this.sideLength = tileSpaces.length;
        this.tileSpaces = tileSpaces;

        storeSideValues( TileSide.TOP, getTop() );
        storeSideValues( TileSide.RIGHT, getRight() );
        storeSideValues( TileSide.BOTTOM, getBottom() );
        storeSideValues( TileSide.LEFT, getLeft() );

        sideMap.put( TileSide.TOP, TileSide.TOP );
        sideMap.put( TileSide.RIGHT, TileSide.RIGHT );
        sideMap.put( TileSide.BOTTOM, TileSide.BOTTOM );
        sideMap.put( TileSide.LEFT, TileSide.LEFT );
    }

    private void storeSideValues( TileSide side, TileSpace[] sideSpaces ) {
        sideValues.put( side, calculateSideValue( sideSpaces ) );
        sideComplementValues.put( side, calculateSideComplementValue( sideSpaces ) );
    }

    static int calculateSideValue( TileSpace[] side ) {
        int value = 0;
        for ( int i = 0; i < side.length; ++i ) {
            TileSpace tileSpace = side[i];
            if ( tileSpace == TileSpace.OCCUPIED ) {
                value += (int) Math.pow( 2, side.length - i - 1 );
            }
        }
        return value;
    }

    static int calculateSideComplementValue( TileSpace[] side ) {
        int value = 0;
        for ( int i = 0; i < side.length; ++i ) {
            TileSpace tileSpace = side[i];
            if ( tileSpace == TileSpace.OCCUPIED ) {
                value += (int) Math.pow( 2, i );
            }
        }
        return value;
    }

    private TileSpace[] getTop() {
        TileSpace[] top = new TileSpace[sideLength];
        for ( int x = 0; x < sideLength; ++x ) {
            top[x] = tileSpaces[0][x];
        }
        return top;
    }

    private TileSpace[] getRight() {
        TileSpace[] right = new TileSpace[sideLength];
        for ( int y = 0; y < sideLength; ++y ) {
            right[y] = tileSpaces[y][sideLength - 1];
        }
        return right;
    }

    private TileSpace[] getBottom() {
        TileSpace[] bottom = new TileSpace[sideLength];
        for ( int x = 0; x < sideLength; ++x ) {
            bottom[x] = tileSpaces[sideLength - 1][sideLength - x - 1];
        }
        return bottom;
    }

    private TileSpace[] getLeft() {
        TileSpace[] left = new TileSpace[sideLength];
        for ( int y = 0; y < sideLength; ++y ) {
            left[y] = tileSpaces[sideLength - y - 1][0];
        }
        return left;
    }

    int id() {
        return id;
    }

    int sideValue( TileSide side ) {
        return sideValues.get( side );
    }

    int sideComplementValue( TileSide side ) {
        return sideComplementValues.get( side );
    }

    void rotateClockwise() {
        int oldTopValue = sideValues.get( TileSide.TOP );
        int oldTopComplementValue = sideComplementValues.get( TileSide.TOP );
        sideValues.put( TileSide.TOP, sideValues.get( TileSide.LEFT ) );
        sideComplementValues.put( TileSide.TOP, sideComplementValues.get( TileSide.LEFT ) );
        sideValues.put( TileSide.LEFT, sideValues.get( TileSide.BOTTOM ) );
        sideComplementValues.put( TileSide.LEFT, sideComplementValues.get( TileSide.BOTTOM ) );
        sideValues.put( TileSide.BOTTOM, sideValues.get( TileSide.RIGHT ) );
        sideComplementValues.put( TileSide.BOTTOM, sideComplementValues.get( TileSide.RIGHT ) );
        sideValues.put( TileSide.RIGHT, oldTopValue );
        sideComplementValues.put( TileSide.RIGHT, oldTopComplementValue );




        TileSide oldMappedTopSide = sideMap.get( TileSide.TOP );
        sideMap.put( TileSide.TOP, sideMap.get( TileSide.LEFT ) );
        sideMap.put( TileSide.LEFT, sideMap.get( TileSide.BOTTOM ) );
        sideMap.put( TileSide.BOTTOM, sideMap.get( TileSide.RIGHT ) );
        sideMap.put( TileSide.RIGHT, oldMappedTopSide );
    }

    void flipHorizontally() {
        int oldTopValue = sideValues.get( TileSide.TOP );
        int oldTopComplementValue = sideComplementValues.get( TileSide.TOP );
        sideValues.put( TileSide.TOP, oldTopComplementValue );
        sideComplementValues.put( TileSide.TOP, oldTopValue );

        int oldBottomValue = sideValues.get( TileSide.BOTTOM );
        int oldBottomComplementValue = sideComplementValues.get( TileSide.BOTTOM );
        sideValues.put( TileSide.BOTTOM, oldBottomComplementValue );
        sideComplementValues.put( TileSide.BOTTOM, oldBottomValue );

        int oldLeftValue = sideValues.get( TileSide.LEFT );
        int oldLeftComplementValue = sideComplementValues.get( TileSide.LEFT );
        int oldRightValue = sideValues.get( TileSide.RIGHT );
        int oldRightComplementValue = sideComplementValues.get( TileSide.RIGHT );
        sideValues.put( TileSide.LEFT, oldRightComplementValue );
        sideComplementValues.put( TileSide.LEFT, oldRightValue );
        sideValues.put( TileSide.RIGHT, oldLeftComplementValue );
        sideComplementValues.put( TileSide.RIGHT, oldLeftValue );



        TileSide oldMappedLeftSide = sideMap.get( TileSide.LEFT );
        sideMap.put( TileSide.LEFT, sideMap.get( TileSide.RIGHT ) );
        sideMap.put( TileSide.RIGHT, oldMappedLeftSide );
        flippedHorizontally = ! flippedHorizontally;
    }

    boolean hasSideValue( int sideValue ) {
        return sideValues.containsValue( sideValue );
    }

    int sideLength() {
        return sideLength;
    }

    TileSpace[] row( int rowIndex ) {
        switch ( sideMap.get( TileSide.TOP ) ) {
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
