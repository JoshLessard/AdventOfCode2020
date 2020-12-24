package com.adventofcode2020.dec15;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    public static void main( String[] args ) throws IOException {
        List<Integer> startingNumbers = getStartingNumbers();
        Map<Integer, NumberStatistics> statsByNumber = getStartingStatsByNumber( startingNumbers );
        Integer mostRecentlySpokenNumber = startingNumbers.get( startingNumbers.size() - 1 );
        for ( int turn = statsByNumber.size() + 1; turn <= 2020; ++turn ) {
            NumberStatistics statistics = statsByNumber.get( mostRecentlySpokenNumber );
            Integer nextNumber = statistics.isFirstTimeSpoken() ? 0 : statistics.differenceBetweenLastTwoTurns();
            statsByNumber.computeIfAbsent( nextNumber, n -> new NumberStatistics() ).addTurn( turn );
            mostRecentlySpokenNumber = nextNumber;
        }

        System.out.println( "Part A: " + mostRecentlySpokenNumber );

        for ( int turn = 2021; turn <= 30000000; ++ turn ) {
            NumberStatistics statistics = statsByNumber.get( mostRecentlySpokenNumber );
            Integer nextNumber = statistics.isFirstTimeSpoken() ? 0 : statistics.differenceBetweenLastTwoTurns();
            statsByNumber.computeIfAbsent( nextNumber, n -> new NumberStatistics() ).addTurn( turn );
            mostRecentlySpokenNumber = nextNumber;
        }

        System.out.println( "Part B: " + mostRecentlySpokenNumber );
    }

    private static List<Integer> getStartingNumbers() throws IOException {
        String input = getInput( "src/main/resources/dec15/input.txt" ).get( 0 );
        String[] split = input.split( "," );
        return Stream.of( split )
            .map( Integer::parseInt )
            .collect( toList() );
    }

    private static Map<Integer, NumberStatistics> getStartingStatsByNumber( List<Integer> startingNumbers ) {
        Map<Integer, NumberStatistics> startingStatsByNumber = new HashMap<>();
        for ( int i = 0; i < startingNumbers.size(); ++i ) {
            Integer startingNumber = startingNumbers.get( i );
            NumberStatistics statistics = new NumberStatistics();
            statistics.addTurn( i + 1 );
            startingStatsByNumber.put( startingNumber, statistics );
        }

        return startingStatsByNumber;
    }
}
