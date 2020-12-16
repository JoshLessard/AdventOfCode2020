package com.adventofcode2020.dec11;

import java.util.stream.Stream;

enum Element {

    FLOOR( '.' ),
    EMPTY_SEAT( 'L' ),
    OCCUPIED_SEAT( '#' );

    private final char character;

    Element( char character ) {
        this.character = character;
    }

    @Override
    public String toString() {
        return String.valueOf( character );
    }

    static Element parse( char character ) {
        return Stream.of( Element.values() )
            .filter( e -> e.character == character )
            .findFirst()
            .orElseThrow( () -> new IllegalArgumentException( "Unrecognized input character: " + character ) );
    }
}
