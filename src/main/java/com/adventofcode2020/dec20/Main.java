package com.adventofcode2020.dec20;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws IOException {
        List<Tile> tiles = new TileParser().parseTiles( getInput( "src/main/resources/dec20/input.txt" ).iterator() );
        TileGrid grid = new TileGrid( tiles );
        grid.assembleTiles();

        System.out.println( "Part A: " + grid.cornerIds().stream().mapToLong( Integer::longValue ).reduce( 1, (a, b) -> a * b ) );

        System.out.println( grid );
    }
}
