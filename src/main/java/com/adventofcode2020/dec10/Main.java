package com.adventofcode2020.dec10;

import java.io.IOException;
import java.util.*;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;

public class Main {

    public static void main( String[] args ) throws IOException {
        List<Integer> adapters = getJoltages();

        System.out.println( "Part A: " + partA( adapters ) );
        System.out.println( "Part B: " + partB( adapters ) );
    }

    private static List<Integer> getJoltages() throws IOException {
        List<Integer> input =  getInput( "src/main/resources/dec10/input.txt" ).stream()
            .map( Integer::parseInt )
            .collect( toList() );
        input.add( 0 );
        sort( input );
        int largestAdapter = input.get( input.size() - 1 );
        input.add( largestAdapter + 3 );
        return input;
    }

    private static int partA( List<Integer> adapters ) {
        int oneJoltDifferences = 0;
        int threeJoltDifferences = 0;
        for (int i = 0; i < adapters.size() - 1; ++i ) {
            int difference = adapters.get( i + 1 ) - adapters.get( i );
            if ( difference == 1 ) {
                ++oneJoltDifferences;
            } else if ( difference == 3 ) {
                ++threeJoltDifferences;
            }
        }
        return oneJoltDifferences * threeJoltDifferences;
    }

    private static long partB( List<Integer> adapters ) {
        Map<Integer, Long> pathsByAdapter = new HashMap<>();
        pathsByAdapter.put( adapters.get( adapters.size() - 1 ), 1L );
        for ( int i = adapters.size() - 1; i > 0; --i ) {
            int currentAdapter = adapters.get( i );
            long numberOfPathsToCurrentAdapter = pathsByAdapter.get( currentAdapter );
            getReachableAdapters( adapters, i )
                .forEach( a -> pathsByAdapter.put( a, pathsByAdapter.getOrDefault( a, 0L ) + numberOfPathsToCurrentAdapter ) );
        }

        return pathsByAdapter.get( 0 );
    }

    private static List<Integer> getReachableAdapters( List<Integer> adapters, int fromIndex ) {
        List<Integer> reachableAdapters = new ArrayList<>();
        int fromAdapter = adapters.get( fromIndex );
        for ( int i = fromIndex - 1; i >= 0; --i ) {
            int toAdapter = adapters.get( i );
            if ( fromAdapter - toAdapter <= 3 ) {
                reachableAdapters.add( toAdapter );
            } else {
                break;
            }
        }
        return reachableAdapters;
    }
}
