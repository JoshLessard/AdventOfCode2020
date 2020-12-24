package com.adventofcode2020.dec14;

class MemoryCell {

    public static final int LENGTH = 36;

    private final boolean[] bits = new boolean[36];

    MemoryCell( long longValue ) {
        this( toBooleans( longValue ) );
    }

    private static boolean[] toBooleans( long longValue ) {
        boolean[] bits = new boolean[LENGTH];
        long currentPlaceValue = (long) Math.pow( 2, LENGTH - 1 );
        for ( int i = 0; i < LENGTH; ++i, currentPlaceValue /= 2 ) {
            if ( longValue >= currentPlaceValue ) {
                bits[i] = true;
                longValue -= currentPlaceValue;
            } else {
                bits[i] = false;
            }
        }
        return bits;
    }

    MemoryCell( boolean[] bits ) {
        ensureCorrectLength( bits );
        System.arraycopy( bits, 0, this.bits, 0, LENGTH );
    }

    private void ensureCorrectLength( boolean[] bits ) {
        if ( bits.length != LENGTH ) {
            throw new IllegalArgumentException( "Memory cell length must be 36." );
        }
    }

    boolean getAt( int index ) {
        return bits[index];
    }

    long asLong() {
        long longValue = 0L;
        long currentPlaceValue = 1;
        for ( int i = bits.length - 1; i >= 0; --i, currentPlaceValue *= 2 ) {
            if ( bits[i] ) {
                longValue += currentPlaceValue;
            }
        }

        return longValue;
    }
}
