package com.adventofcode2020.dec09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;

public class Main {

    private static final int PREAMBLE_LENGTH = 25;

    public static void main( String[] args ) throws IOException {
        List<Long> input = getInputNumbers();
        long firstInvalidNumber = firstInvalidNumber( input );
        long encryptionWeakness = encryptionWeakness( input, firstInvalidNumber );

        System.out.println( "Part A: " + firstInvalidNumber );
        System.out.println( "Part B: " + encryptionWeakness );
    }

    private static List<Long> getInputNumbers() throws IOException {
        return getInput( "src/main/resources/dec09/input.txt" ).stream()
            .map( Long::parseLong )
            .collect( toList() );
    }

    private static long firstInvalidNumber( List<Long> numbers ) {
        int currentIndex = PREAMBLE_LENGTH;
        while ( true ) {
            long currentNumber = numbers.get( currentIndex );
            List<Long> preamble = new ArrayList<>( numbers.subList( currentIndex - PREAMBLE_LENGTH, currentIndex ) );
            sort( preamble );
            boolean foundPair = preamble.stream()
                .filter( n -> currentNumber - n != n )
                .anyMatch( n -> binarySearch( preamble, currentNumber - n ) >= 0 );
            if ( foundPair ) {
                ++currentIndex;
            } else {
                return currentNumber;
            }
        }
    }

    private static long encryptionWeakness( List<Long> numbers, long targetSum ) {
        for ( int firstIndex = 0; firstIndex < numbers.size() - 2; ++firstIndex ) {
            long smallestNumber = numbers.get( firstIndex );
            long largestNumber = numbers.get( firstIndex );
            int nextIndex = firstIndex;
            long currentSum = numbers.get( nextIndex++ );
            while ( nextIndex < numbers.size() && currentSum < targetSum ) {
                long currentNumber = numbers.get( nextIndex++ );
                smallestNumber = Math.min( smallestNumber, currentNumber );
                largestNumber = Math.max( largestNumber, currentNumber );
                currentSum += currentNumber;
                if ( currentSum == targetSum ) {
                    return smallestNumber + largestNumber;
                }
            }
        }
        throw new IllegalStateException();
    }
}
