package com.adventofcode2020.dec24;

import static java.util.Collections.emptySet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.adventofcode2020.common.Point;

class HexGrid {

    private final Set<Point> blackTiles;

    HexGrid() {
        this( emptySet() );
    }

    private HexGrid( Set<Point> blackTiles ) {
        this.blackTiles = new HashSet<>( blackTiles );
    }

    void flip( List<HexDirection> pathToTile ) {
        flip( tileAt( pathToTile ) );
    }

    private Point tileAt( List<HexDirection> path ) {
        Point point = new Point( 0, 0 );
        for ( HexDirection direction : path) {
            point = direction.applyTo( point );
        }
        return point;
    }

    private void flip( Point tile ) {
        if ( blackTiles.contains( tile ) ) {
            blackTiles.remove( tile );
        } else {
            blackTiles.add( tile );
        }
    }

    private Colour getColour( Point tile ) {
        return blackTiles.contains( tile ) ? Colour.BLACK : Colour.WHITE;
    }

    int blackTileCount() {
        return blackTiles.size();
    }

    HexGrid map( TileFlipPredicate predicate ) {
        Set<Point> tilesToConsiderFlipping = getSelvesAndNeighbours( blackTiles );
        Set<Point> newBlackTiles = blackTilesAfterFlipping( tilesToConsiderFlipping, predicate );
        return new HexGrid( newBlackTiles );
    }

    private Set<Point> getSelvesAndNeighbours( Set<Point> selves ) {
        Set<Point> selvesAndNeighbours = new HashSet<>( selves );
        selves.forEach( s -> selvesAndNeighbours.addAll( getNeighbours( s ) ) );
        return selvesAndNeighbours;
    }

    private Set<Point> getNeighbours( Point tile ) {
        return getNeighbours( tile, t -> true );
    }

    private Set<Point> getNeighbours( Point tile, Predicate<Point> shouldInclude ) {
        Set<Point> neighbours = new HashSet<>();
        for ( HexDirection direction : HexDirection.values() ) {
            Point neighbour = direction.applyTo( tile );
            if ( shouldInclude.test( neighbour ) ) {
                neighbours.add( neighbour );
            }
        }
        return neighbours;
    }

    private Set<Point> blackTilesAfterFlipping( Set<Point> tiles, TileFlipPredicate predicate ) {
        Set<Point> newBlackTiles = new HashSet<>();
        for ( Point tile : tiles ) {
            Colour currentColour = getColour( tile );
            int blackNeighbours = getNeighbours( tile, t -> getColour( t ) == Colour.BLACK ).size();
            if ( currentColour == Colour.WHITE && predicate.shouldFlip( currentColour, blackNeighbours ) ) {
                newBlackTiles.add( tile );
            } else if ( currentColour == Colour.BLACK && ! predicate.shouldFlip( currentColour, blackNeighbours ) ) {
                newBlackTiles.add( tile );
            }
        }
        return newBlackTiles;
    }
}
