package com.adventofcode2020.dec20;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode2020.common.Point;

class TileGrid {

    private final List<Tile> tiles;
    private final Tile[][] grid;
    private final Map<Integer, List<Tile>> tilesBySideValue = new HashMap<>();
    private final Map<Integer, Point> tileLocationsById = new HashMap<>();

    TileGrid( List<Tile> tiles ) {
        this.tiles = new ArrayList<>( tiles );
        this.grid = initializeGrid( (int) Math.sqrt( tiles.size() ) );
        tiles.forEach( this::cacheSideValues );
    }

    private void cacheSideValues( Tile tile ) {
        tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.TOP ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideComplementValue( TileSide.TOP ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.RIGHT ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideComplementValue( TileSide.RIGHT ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.BOTTOM ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideComplementValue( TileSide.BOTTOM ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.LEFT ), t -> new ArrayList<>() ).add( tile );
        tilesBySideValue.computeIfAbsent( tile.sideComplementValue( TileSide.LEFT ), t -> new ArrayList<>() ).add( tile );
    }

    private Tile[][] initializeGrid( int sideLength ) {
        Tile[][] grid = new Tile[sideLength][];
        for ( int i = 0; i < sideLength; ++i ) {
            grid[i] = new Tile[sideLength];
        }
        return grid;
    }

    void assembleTiles() {
        List<Tile> cornerTiles = getCornerTiles();
        Tile topLeftCornerTile = cornerTiles.remove( 0 );
        while ( ! getBorderEdges( topLeftCornerTile ).equals( Set.of( TileSide.LEFT, TileSide.TOP ) ) ) {
            topLeftCornerTile.rotateClockwise();
        }
        addToGrid( topLeftCornerTile, 0, 0 );
        Deque<Tile> tilesToProcess = new LinkedList<>();
        tilesToProcess.add( topLeftCornerTile );
        while ( ! tilesToProcess.isEmpty() ) {
            Tile tileToBeMatched = tilesToProcess.removeFirst();
            for ( TileSide side : TileSide.values() ) {
                int sideComplementValue = tileToBeMatched.sideComplementValue( side );
                if ( hasSingleMatchingTile( sideComplementValue ) ) {
                    Tile matchingTile = getMatchingTile( sideComplementValue );
                    if ( ! matchingTile.hasSideValue( sideComplementValue ) ) {
                        matchingTile.flipHorizontally();
                    }
                    while ( matchingTile.sideValue( side.complement() ) != sideComplementValue ) {
                        matchingTile.rotateClockwise();
                    }

                    Point locationOfTileToBeMatched = tileLocationsById.get( tileToBeMatched.id() );
                    Point locationOfMatchingTile = side.applyTo( locationOfTileToBeMatched );
                    addToGrid( matchingTile, locationOfMatchingTile.x, locationOfMatchingTile.y );
                    tilesToProcess.add( matchingTile );
                }
            }
        }
    }

    private Tile getMatchingTile( int sideComplementValue ) {
        return tilesBySideValue.get( sideComplementValue ).get( 0 );
    }

    private void addToGrid( Tile tile, int x, int y ) {
        if ( grid[y][x] != null ) {
            throw new IllegalArgumentException( "Grid location (" + x + ", " + y + ") is already occupied." );
        }
        grid[y][x] = tile;
        tileLocationsById.put( tile.id(), new Point( x, y ) );
        Stream.of( TileSide.values() )
            .forEach( s -> evictSideValue( tile, s ) );
    }

    private void evictSideValue( Tile tile, TileSide side ) {
        tilesBySideValue.get( tile.sideValue( side ) ).remove( tile );
        tilesBySideValue.get( tile.sideComplementValue( side ) ).remove( tile );
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

    private List<Tile> getCornerTiles() {
        return tiles.stream()
            .filter( this::isCorner )
            .collect( toList() );
    }

    private boolean isCorner( Tile tile ) {
        Set<TileSide> borderEdges = getBorderEdges( tile );
        return borderEdges.size() == 2;
    }

    private Set<TileSide> getBorderEdges( Tile tile ) {
        return Stream.of( TileSide.values() )
            .filter( s -> isBorderEdge( tile, tile.sideValue( s ) ) )
            .filter( s -> isBorderEdge( tile, tile.sideComplementValue( s ) ) )
            .collect( toSet() );
    }

    private boolean isBorderEdge( Tile tile, int sideValue ) {
        return hasSingleMatchingTile( sideValue ) && getMatchingTile( sideValue ) == tile;
    }

    private boolean hasSingleMatchingTile( int sideValue ) {
        return tilesBySideValue.get( sideValue ).size() == 1;
    }
}
