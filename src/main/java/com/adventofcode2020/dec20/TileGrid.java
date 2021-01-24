package com.adventofcode2020.dec20;

import java.util.HashSet;
import java.util.Set;

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
        int tileLength = grid[0][0].sideLength();
        int singleTileLength = grid.length * tileLength;
        TileSpace[][] singleTileGrid = new TileSpace[singleTileLength][singleTileLength];
        for ( int i = 0; i < singleTileLength; ++i ) {
            singleTileGrid[i] = new TileSpace[singleTileLength];
        }

        for ( int y = 0; y < grid.length; ++y ) {
            for ( int tileRow = 0; tileRow < tileLength; ++tileRow ) {
                int singleTileX = 0;
                int singleTileY = y * tileLength + tileRow;
                for ( int x = 0; x < grid.length; ++x ) {
                    TileSpace[] tileSpaces = grid[y][x].row( tileRow );
                    System.arraycopy( tileSpaces, 0, singleTileGrid[singleTileY], singleTileX, tileSpaces.length );
                    singleTileX += tileLength;
                }
            }
        }

        return new Tile( 0, singleTileGrid );
    }
}
