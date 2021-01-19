package com.adventofcode2020.dec24;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws IOException {
        HexGrid grid = buildGrid();
        System.out.println( "Part A: " + grid.blackTileCount() );

        for ( int i = 0; i < 100; ++i ) {
            grid = grid.map( Main::shouldFlip );
        }
        System.out.println( "Part B: " + grid.blackTileCount() );
    }

    private static HexGrid buildGrid() throws IOException {
        HexGrid grid = new HexGrid();
        getInput( "src/main/resources/dec24/input.txt" ).stream()
            .map( Main::toDirections )
            .forEach( grid::flip );
        return grid;
    }

    private static List<HexDirection> toDirections( String input ) {
        List<HexDirection> directions = new ArrayList<>();
        for ( int i = 0; i < input.length(); ++i ) {
            switch ( input.charAt( i ) ) {
                case 'e':
                    directions.add( HexDirection.EAST );
                    break;
                case 'w':
                    directions.add( HexDirection.WEST );
                    break;
                case 'n':
                    directions.add( input.charAt( ++i ) == 'w' ? HexDirection.NORTHWEST : HexDirection.NORTHEAST );
                    break;
                case 's':
                    directions.add( input.charAt( ++i ) == 'w' ? HexDirection.SOUTHWEST : HexDirection.SOUTHEAST );
                    break;
            }
        }
        return directions;
    }

    private static boolean shouldFlip( Colour colour, int blackNeighbours ) {
        if ( colour == Colour.BLACK ) {
            return blackNeighbours == 0 || blackNeighbours > 2;
        } else {
            return blackNeighbours == 2;
        }
    }
}
