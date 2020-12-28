package com.adventofcode2020.dec17;

import java.util.Arrays;

class NPoint {

    private final int[] coordinates;

    NPoint( int... coordinates ) {
        this.coordinates = new int[coordinates.length];
        System.arraycopy( coordinates, 0, this.coordinates, 0, coordinates.length );
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof NPoint)) return false;
        NPoint nPoint = (NPoint) o;
        return Arrays.equals(coordinates, nPoint.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    int getCoordinate( int dimension ) {
        return coordinates[dimension];
    }
}
