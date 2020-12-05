package com.adventofcode2020.dec05;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.stream.Collectors.toCollection;

public class Main {

    public static void main( String[] args ) throws Exception {
        List<String> input = getInput( "src/main/resources/dec05/input.txt" );
        SortedSet<Integer> seatIds = input.stream()
            .map( Main::toSeatId )
            .collect( toCollection( TreeSet::new ) );

        System.out.println( "Highest seat ID: " + seatIds.last() );
        System.out.println( "Your seat ID: " + findMissingSeatId( seatIds ) );
    }

    private static int toSeatId( String seatCode ) {
        return row( seatCode ) * 8 + seat( seatCode );
    }

    private static int row( String code ) {
        int low = 0;
        int high = 127;
        int rangeSize = high - low + 1;
        String rowCode = code.substring( 0, 7 );
        for ( int i = 0; i < rowCode.length(); ++i ) {
            rangeSize /= 2;
            switch ( rowCode.charAt( i ) ) {
                case 'F' -> high -= rangeSize;
                case 'B' -> low += rangeSize;
            }
        }

        assert low == high;
        return low;
    }

    private static int seat( String code ) {
        int low = 0;
        int high = 7;
        int rangeSize = high - low + 1;
        String seatCode = code.substring( 7 );
        for ( int i = 0; i < seatCode.length(); ++i ) {
            rangeSize /= 2;
            switch( seatCode.charAt( i ) ) {
                case 'L' -> high -= rangeSize;
                case 'R' -> low += rangeSize;
            }
        }

        assert low == high;
        return low;
    }

    private static int findMissingSeatId( SortedSet<Integer> seatIds ) {
        int previousSeatId = seatIds.first() - 1;
        for ( int seatId : seatIds ) {
            if ( seatId != previousSeatId + 1 ) {
                return previousSeatId + 1;
            }
            previousSeatId = seatId;
        }

        throw new NoSuchElementException();
    }
}
