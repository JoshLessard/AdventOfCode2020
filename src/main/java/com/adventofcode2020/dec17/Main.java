package com.adventofcode2020.dec17;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final int NUMBER_OF_CYCLES = 6;

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec17/input.txt" );

        InfiniteConwayGrid partAGrid = buildAndMapGrid( 3, input );
        System.out.println( "Part A: " + partAGrid.numberOfActiveCubes() );

        InfiniteConwayGrid partBGrid = buildAndMapGrid( 4, input );
        System.out.println( "Part B: " + partBGrid.numberOfActiveCubes() );
    }

    private static InfiniteConwayGrid buildAndMapGrid( int numberOfDimensions, List<String> input ) {
        InfiniteConwayGrid grid = buildGrid( numberOfDimensions, input );
        for ( int cycle = 0; cycle < NUMBER_OF_CYCLES; ++cycle ) {
            grid = grid.map( Main::becomesActive );
        }
        return grid;
    }

    private static InfiniteConwayGrid buildGrid( int numberOfDimensions, List<String> input ) {
        InfiniteConwayGrid grid = new InfiniteConwayGrid( numberOfDimensions );
        for ( int y = 0; y < input.size(); ++y ) {
            String line = input.get( y );
            for ( int x = 0; x < line.length(); ++x ) {
                if ( line.charAt( x ) == '#' ) {
                    NPoint point = createStartingPoint( x, y, numberOfDimensions );
                    grid.setActive(point);
                }
            }
        }

        return grid;
    }

    private static NPoint createStartingPoint( int x, int y, int numberOfDimensions ) {
        int[] coordinates = new int[numberOfDimensions];
        coordinates[0] = x;
        coordinates[1] = y;
        for ( int d = 2; d < numberOfDimensions; ++d ) {
            coordinates[d] = 0;
        }
        return new NPoint( coordinates );
    }

    private static boolean becomesActive( boolean isActive, int numberOfActiveNeighbours ) {
        if ( isActive ) {
            return numberOfActiveNeighbours == 2 || numberOfActiveNeighbours == 3;
        } else {
            return numberOfActiveNeighbours == 3;
        }
    }
}
