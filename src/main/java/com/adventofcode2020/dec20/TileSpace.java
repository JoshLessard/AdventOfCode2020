package com.adventofcode2020.dec20;

import java.util.stream.Stream;

enum TileSpace {

    OCCUPIED( '#' ),
    EMPTY( '.' );

    private final char representation;

    TileSpace( char representation ) {
        this.representation = representation;
    }

    static TileSpace parse( char representation ) {
        return Stream.of( TileSpace.values() )
            .filter( s -> s.representation == representation )
            .findFirst()
            .orElseThrow( IllegalArgumentException::new );
    }

    @Override
    public String toString() {
        return String.valueOf( representation );
    }
}
