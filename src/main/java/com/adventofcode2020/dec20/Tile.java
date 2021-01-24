package com.adventofcode2020.dec20;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

class Tile {

    private final int id;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final TileSpace[][] tileSpaces;

    private final Map<TileSide, TileSide> sideMap = new EnumMap<>( TileSide.class );
    private boolean flippedHorizontally = false;

    Tile( int id, TileSpace[][] tileSpaces ) {
        this.id = id;
        this.numberOfRows = tileSpaces.length;
        this.numberOfColumns = tileSpaces[0].length;
        this.tileSpaces = tileSpaces;

        sideMap.put( TileSide.TOP, TileSide.TOP );
        sideMap.put( TileSide.RIGHT, TileSide.RIGHT );
        sideMap.put( TileSide.BOTTOM, TileSide.BOTTOM );
        sideMap.put( TileSide.LEFT, TileSide.LEFT );
    }

    private Tile( int id, TileSpace[][] tileSpaces, Map<TileSide, TileSide> sideMap, boolean flippedHorizontally ) {
        this.id = id;
        this.numberOfRows = tileSpaces.length;
        this.numberOfColumns = tileSpaces[0].length;
        this.tileSpaces = tileSpaces;
        this.sideMap.putAll( sideMap );
        this.flippedHorizontally = flippedHorizontally;
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

    int numberOfRows() {
        switch ( sideMap.get( TileSide.TOP ) ) {
            case TOP:
            case BOTTOM:
                return numberOfRows;
            case LEFT:
            case RIGHT:
                return numberOfColumns;
        }
        throw new IllegalStateException( "Tile's top row wasn't mapped properly." );
    }

    int numberOfColumns() {
        switch ( sideMap.get( TileSide.TOP ) ) {
            case TOP:
            case BOTTOM:
                return numberOfColumns;
            case LEFT:
            case RIGHT:
                return numberOfRows;
        }
        throw new IllegalStateException( "Tile's top row wasn't mapped properly." );
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
        TileSpace[] row = new TileSpace[numberOfColumns];
        int y = rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int x = numberOfColumns - 1; x >= 0; --x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int x = 0; x < numberOfColumns; ++x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    private TileSpace[] getRowFromRight( int rowIndex ) {
        TileSpace[] row = new TileSpace[numberOfRows];
        int x = numberOfColumns - 1 - rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int y = numberOfRows - 1; y >= 0; --y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int y = 0; y < numberOfRows; ++y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    private TileSpace[] getRowFromBottom( int rowIndex ) {
        TileSpace[] row = new TileSpace[numberOfColumns];
        int y = numberOfRows - 1 - rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int x = 0; x < numberOfColumns; ++x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int x = numberOfColumns - 1; x >= 0; --x ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    private TileSpace[] getRowFromLeft( int rowIndex ) {
        TileSpace[] row = new TileSpace[numberOfRows];
        int x = rowIndex;
        int columnIndex = 0;
        if ( flippedHorizontally ) {
            for ( int y = 0; y < numberOfRows; ++y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        } else {
            for ( int y = numberOfRows - 1; y >= 0; --y ) {
                row[columnIndex++] = tileSpaces[y][x];
            }
        }
        return row;
    }

    Tile removeBorder() {
        int newNumberOfRows = numberOfRows - 2;
        int newNumberOfColumns = numberOfColumns - 2;
        TileSpace[][] newTileSpaces = new TileSpace[newNumberOfRows][];
        for ( int y = 0; y < newTileSpaces.length; ++y ) {
            newTileSpaces[y] = new TileSpace[newNumberOfColumns];
        }

        for ( int y = 1; y < numberOfRows - 1; ++y ) {
            for ( int x = 1; x < numberOfColumns - 1; ++x ) {
                newTileSpaces[y - 1][x - 1] = tileSpaces[y][x];
            }
        }

        return new Tile( id, newTileSpaces, sideMap, flippedHorizontally );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder( "Tile " + id + ":\n" );
        for ( int rowIndex = 0; rowIndex < numberOfRows(); ++rowIndex ) {
            TileSpace[] row = row( rowIndex );
            for ( int columnIndex = 0; columnIndex < numberOfColumns(); ++columnIndex ) {
                builder.append( row[columnIndex] );
            }
            builder.append( '\n' );
        }
        return builder.toString();
    }
}
