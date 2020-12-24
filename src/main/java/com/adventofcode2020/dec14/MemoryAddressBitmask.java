package com.adventofcode2020.dec14;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

class MemoryAddressBitmask {

    private static final int LENGTH = 36;

    private final char[] bits;

    MemoryAddressBitmask( String bits ) {
        ensureCorrectLength( bits );
        this.bits = bits.toCharArray();
    }

    private void ensureCorrectLength(String bits) {
        if ( bits.length() != LENGTH ) {
            throw new IllegalArgumentException( "Bitmask must be 36 bits long." );
        }
    }

    public List<MemoryCell> mapToMemoryAddresses( MemoryCell address ) {
        List<char[]> mappedBitCopies = copy( mapBits( address ), (int) Math.pow( 2, countXs() ) );
        for ( int copyIndex = 0; copyIndex < mappedBitCopies.size(); ++copyIndex ) {
            int xBitmask = copyIndex;
            char[] mappedBits = mappedBitCopies.get( copyIndex );
            for ( int bitIndex = 0; bitIndex < LENGTH; ++bitIndex ) {
                if ( mappedBits[bitIndex] == 'X' ) {
                    mappedBits[bitIndex] = xBitmask % 2 == 0 ? '0' : '1';
                    xBitmask /= 2;
                }
            }
        }

        return mappedBitCopies.stream()
            .map( this::toBits )
            .map( MemoryCell::new )
            .collect( toList() );
    }

    private boolean[] toBits( char[] chars ) {
        boolean[] bits = new boolean[LENGTH];
        for ( int i = 0; i < chars.length; ++i ) {
            bits[i] = chars[i] == '1';
        }
        return bits;
    }

    private List<char[]> copy( char[] bits, int numberOfCopies ) {
        List<char[]> copies = new ArrayList<>();
        for ( int i = 0; i < numberOfCopies; ++i ) {
            char[] copy = new char[LENGTH];
            System.arraycopy( bits, 0, copy, 0, LENGTH );
            copies.add( copy );
        }
        return copies;
    }

    private int countXs() {
        int numberOfXs = 0;
        for ( int i = 0; i < LENGTH; ++i ) {
            if ( bits[i] == 'X' ) {
                ++numberOfXs;
            }
        }
        return numberOfXs;
    }

    private char[] mapBits( MemoryCell address ) {
        char[] mappedBits = new char[LENGTH];
        for ( int i = 0; i < LENGTH; ++i ) {
            if ( bits[i] == '0' ) {
                mappedBits[i] = address.getAt( i ) ? '1' : '0';
            } else if ( bits[i] == '1' ) {
                mappedBits[i] = '1';
            } else {
                mappedBits[i] = 'X';
            }
        }
        return mappedBits;
    }
}
