package com.adventofcode2020.dec20;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

class TileGrid {

    private final Tile[][] grid;

    TileGrid( Tile[][] grid ) {
        this.grid = new Tile[grid.length][];
        for ( int y = 0; y < grid.length; ++y ) {
            this.grid[y] = new Tile[grid[0].length];
            System.arraycopy( grid[y], 0, this.grid[y], 0, grid[y].length );
        }
    }

    Set<Integer> cornerIds() {
        int sideLength = grid.length;
        Set<Integer> cornerIds = new HashSet<>();
        cornerIds.add( grid[0][0].id() );
        cornerIds.add( grid[0][sideLength - 1].id() );
        cornerIds.add( grid[sideLength - 1][0].id() );
        cornerIds.add( grid[sideLength - 1][sideLength -1 ].id() );
        return cornerIds;
    }

    Tile toSingleTile() {
        int numberOfRowsInComponentTile = grid[0][0].numberOfRows();
        int numberOfColumnsInComponentTile = grid[0][0].numberOfColumns();
        int numberOfRows = grid.length * numberOfRowsInComponentTile;
        int numberOfColumns = grid[0].length * numberOfColumnsInComponentTile;
        TileSpace[][] singleTileGrid = new TileSpace[numberOfRows][numberOfColumns];
        for ( int y = 0; y < grid.length; ++y ) {
            for ( int tileRow = 0; tileRow < numberOfRowsInComponentTile; ++tileRow ) {
                int singleTileX = 0;
                int singleTileY = y * numberOfRowsInComponentTile + tileRow;
                for ( int x = 0; x < grid.length; ++x ) {
                    TileSpace[] tileSpaces = grid[y][x].row( tileRow );
                    System.arraycopy( tileSpaces, 0, singleTileGrid[singleTileY], singleTileX, tileSpaces.length );
                    singleTileX += numberOfColumnsInComponentTile;
                }
            }
        }

        return new Tile( 0, singleTileGrid );
    }

    TileGrid map( Function<Tile, Tile> mapper ) {
        Tile[][] newGrid = new Tile[grid.length][];
        for ( int y = 0; y < grid.length; ++y ) {
            newGrid[y] = new Tile[grid.length];
        }

        for ( int y = 0; y < grid.length; ++y ) {
            for ( int x = 0; x < grid.length; ++x ) {
                newGrid[y][x] = mapper.apply( grid[y][x] );
            }
        }

        return new TileGrid( newGrid );
    }
}
