package com.adventofcode2020.dec14;

class MemoryCellBitmask {

    private static final int LENGTH = 36;

    private final char[] bits;

    MemoryCellBitmask( String bits ) {
        ensureCorrectLength( bits );
        this.bits = bits.toCharArray();
    }

    private void ensureCorrectLength( String bits ) {
        if ( bits.length() != LENGTH ) {
            throw new IllegalArgumentException( "Bitmask must be 36 bits long." );
        }
    }

    MemoryCell map( MemoryCell memoryCell ) {
        boolean[] mappedMemoryCellBits = new boolean[MemoryCell.LENGTH];
        for ( int i = 0; i < mappedMemoryCellBits.length; ++i ) {
            if ( bits[i] == '0' ) {
                mappedMemoryCellBits[i] = false;
            } else if ( bits[i] == '1' ) {
                mappedMemoryCellBits[i] = true;
            } else {
                mappedMemoryCellBits[i] = memoryCell.getAt( i );
            }
        }

        return new MemoryCell( mappedMemoryCellBits );
    }
}
