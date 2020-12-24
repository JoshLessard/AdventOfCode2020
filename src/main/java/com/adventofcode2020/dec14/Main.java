package com.adventofcode2020.dec14;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern BITMASK_PATTERN = Pattern.compile( "mask = ([01X]{36})" );
    private static final Pattern MEMORY_PATTERN = Pattern.compile( "mem\\[(\\d+)] = (\\d+)" );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec14/input.txt" );
        System.out.println( "Part A: " + sumOfMemoryCells( createPartAMemory( input ) ) );
        System.out.println( "Part B: " + sumOfMemoryCells( createPartBMemory( input ) ) );
    }

    private static Map<Long, MemoryCell> createPartAMemory( List<String> input ) {
        MemoryCellBitmask currentBitmask = null;
        Map<Long, MemoryCell> cellsByAddress = new HashMap<>();
        for ( String inputLine : input ) {
            Matcher bitmaskMatcher = BITMASK_PATTERN.matcher( inputLine );
            Matcher memoryMatcher = MEMORY_PATTERN.matcher( inputLine );
            if ( bitmaskMatcher.matches() ) {
                currentBitmask = new MemoryCellBitmask( bitmaskMatcher.group( 1 ) );
            } else if ( memoryMatcher.matches() ) {
                cellsByAddress.put( Long.parseLong( memoryMatcher.group( 1 ) ), currentBitmask.map( new MemoryCell( Long.parseLong( memoryMatcher.group( 2 ) ) ) ) );
            } else {
                throw new IllegalArgumentException();
            }
        }

        return cellsByAddress;
    }

    private static Map<Long, MemoryCell> createPartBMemory( List<String> input ) {
        MemoryAddressBitmask currentBitmask = null;
        Map<Long, MemoryCell> cellsByAddress = new HashMap<>();
        for ( String inputLine : input ) {
            Matcher bitmaskMatcher = BITMASK_PATTERN.matcher( inputLine );
            Matcher memoryMatcher = MEMORY_PATTERN.matcher( inputLine );
            if ( bitmaskMatcher.matches() ) {
                currentBitmask = new MemoryAddressBitmask( bitmaskMatcher.group( 1 ) );
            } else if ( memoryMatcher.matches() ) {
                MemoryCell address = new MemoryCell( Long.parseLong( memoryMatcher.group( 1 ) ) );
                MemoryCell memoryCell = new MemoryCell( Long.parseLong( memoryMatcher.group( 2 ) ) );
                currentBitmask.mapToMemoryAddresses( address ).forEach(a -> cellsByAddress.put( a.asLong(), memoryCell ) );
            } else {
                throw new IllegalArgumentException();
            }
        }

        return cellsByAddress;
    }

    private static long sumOfMemoryCells( Map<Long, MemoryCell> memory ) {
        return memory.values().stream()
            .mapToLong( MemoryCell::asLong )
            .sum();
    }
}
