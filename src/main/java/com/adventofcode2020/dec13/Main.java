package com.adventofcode2020.dec13;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main( String[] args ) throws IOException  {
        List<String> input = getInput( "src/main/resources/dec13/input.txt" );
        partA( input );
        partB( input );
    }

    private static void partA( List<String> input ) {
        int earliestDepartingTimestamp = Integer.parseInt( input.get( 0 ) );
        BusSchedule busSchedule = Stream.of( input.get( 1 ).split( "," ) )
            .filter( s -> ! "x".equals( s ) )
            .map( Integer::parseInt )
            .map( b -> new BusSchedule( b, earliestDepartureTimeOnOrAfter( earliestDepartingTimestamp, b ) ) )
            .reduce( new BusSchedule( Integer.MAX_VALUE, Integer.MAX_VALUE ), (s1, s2) -> s1.leavesEarlierThan( s2 ) ? s1 : s2 );

        System.out.println( "Part A: " + busSchedule.busNumber * (busSchedule.departureTime - earliestDepartingTimestamp) );
    }

    private static int earliestDepartureTimeOnOrAfter( int earliestDepartingTimestamp, int bus ) {
        int numberOfTrips = (int) Math.ceil( (double) earliestDepartingTimestamp / bus );
        return numberOfTrips * bus;
    }

    private static final class BusSchedule {

        private final int busNumber;
        private final int departureTime;

        BusSchedule( int busNumber, int departureTime ) {
            this.busNumber = busNumber;
            this.departureTime = departureTime;
        }

        boolean leavesEarlierThan( BusSchedule other ) {
            return this.departureTime < other.departureTime;
        }
    }

    private static void partB( List<String> input ) {
        String[] buses = input.get( 1 ).split( "," );
        List<ChineseRemainderTheoremPair> pairs = getPairs( buses );
        long N = getN( pairs );
        long sumbNx = getSumbNx( pairs, N );

        System.out.println( "Part B: " + (N - sumbNx % N) );
    }

    private static List<ChineseRemainderTheoremPair> getPairs( String[] buses ) {
        List<ChineseRemainderTheoremPair> pairs = new ArrayList<>();
        for (int i = 0; i < buses.length; ++i ) {
            if ( ! "x".equals( buses[i] ) ) {
                pairs.add( new ChineseRemainderTheoremPair( i, Integer.parseInt( buses[i] ) ) );
            }
        }
        return pairs;
    }

    private static long getN( List<ChineseRemainderTheoremPair> pairs ) {
        return pairs.stream()
            .mapToLong( p -> p.divisor )
            .reduce( 1L, (a, b) -> a * b );
    }

    private static long getSumbNx( List<ChineseRemainderTheoremPair> pairs, long N ) {
        return pairs.stream()
            .mapToLong( p -> calculatebNx( p, N ) )
            .sum();
    }

    private static long calculatebNx( ChineseRemainderTheoremPair pair, long N ) {
        long Ni = N / pair.divisor;
        return pair.b * Ni * getInverse( Ni, pair.divisor );
    }

    private static int getInverse( long Ni, int divisor ) {
        for ( int i = 1; ; ++i ) {
            if ( Ni * i % divisor == 1 ) {
                return i;
            }
        }
    }

    private static final class ChineseRemainderTheoremPair {

        private final int b;
        private final int divisor;

        ChineseRemainderTheoremPair( int b, int divisor ) {
            this.b = b;
            this.divisor = divisor;
        }
    }
}
