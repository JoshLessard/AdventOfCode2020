package com.adventofcode2020.dec20;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TileParser {

    private static final Pattern TILE_HEADER_PATTERN = Pattern.compile( "Tile (\\d+):" );

    List<Tile> parseTiles( Iterator<String> input ) {
        List<Tile> tiles = new ArrayList<>();
        while ( input.hasNext() ) {
            String header = input.next();
            Matcher headerMatcher = TILE_HEADER_PATTERN.matcher( header );
            if ( headerMatcher.matches() ) {
                int tileId = Integer.parseInt( headerMatcher.group( 1 ) );
                TileBuilder tileBuilder = new TileBuilder( tileId );
                String line;
                while ( input.hasNext() && ! ( line = input.next() ).isBlank() ) {
                    tileBuilder.addRow( toTileRow( line ) );
                }
                tiles.add( tileBuilder.build() );
            } else {
                throw new IllegalArgumentException( "Expected a tile header." );
            }
        }

        return tiles;
    }

    private TileSpace[] toTileRow( String line ) {
        TileSpace[] row = new TileSpace[line.length()];
        for ( int i = 0; i < line.length(); ++i ) {
            row[i] = TileSpace.parse( line.charAt( i ) );
        }
        return row;
    }
}
