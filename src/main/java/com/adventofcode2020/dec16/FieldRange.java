package com.adventofcode2020.dec16;

class FieldRange {

    final int min;
    final int max;

    FieldRange( int min, int max ) {
        ensureValidRange( min, max );
        this.min = min;
        this.max = max;
    }

    private void ensureValidRange(int min, int max) {
        if ( max < min) {
            throw new IllegalArgumentException( "Invalid range specified." );
        }
    }

    public boolean contains( int value ) {
        return min <= value && value <= max;
    }
}
