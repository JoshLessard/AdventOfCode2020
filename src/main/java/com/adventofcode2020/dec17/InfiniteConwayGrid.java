package com.adventofcode2020.dec17;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

class InfiniteConwayGrid {

    private final int numberOfDimensions;
    private final Set<NPoint> activePoints;

    InfiniteConwayGrid( int numberOfDimensions ) {
        this( numberOfDimensions, Set.of() );
    }

    private InfiniteConwayGrid( int numberOfDimensions, Set<NPoint> activePoints ) {
        this.numberOfDimensions = numberOfDimensions;
        this.activePoints = new HashSet<>( activePoints );
    }

    void setActive( NPoint point ) {
        activePoints.add( point );
    }

    InfiniteConwayGrid map( BiPredicate<Boolean, Integer> mapper ) {
        Set<NPoint> pointsToConsider = new HashSet<>( activePoints );
        activePoints.forEach( p -> pointsToConsider.addAll( getNeighbours( p ) ) );
        Set<NPoint> newActivePoints = pointsToConsider.stream()
            .filter( p -> mapper.test( activePoints.contains( p ), countActiveNeighbours( p ) ) )
            .collect( toSet() );
        return new InfiniteConwayGrid( numberOfDimensions, newActivePoints );
    }

    private Collection<NPoint> getNeighbours( NPoint point ) {
        Set<NPoint> neighbours = generatePartialNeighbourCoordinates( 0, point ).stream()
            .map( NPoint::new )
            .collect( toSet() );
        neighbours.remove( point );
        return neighbours;
    }

    private List<int[]> generatePartialNeighbourCoordinates( int startingDimension, NPoint point ) {
        if ( startingDimension >= numberOfDimensions ) {
            LinkedList<int[]> emptyCoordinates = new LinkedList<>();
            emptyCoordinates.add( new int[numberOfDimensions] );
            return emptyCoordinates;
        }

        List<int[]> partialNeighbourCoordinates = generatePartialNeighbourCoordinates( startingDimension + 1, point );
        List<int[]> neighbourCoordinates = new LinkedList<>();
        neighbourCoordinates.addAll( copyAndSet( partialNeighbourCoordinates, startingDimension, point.getCoordinate( startingDimension ) - 1 ) );
        neighbourCoordinates.addAll( copyAndSet( partialNeighbourCoordinates, startingDimension, point.getCoordinate( startingDimension ) ) );
        neighbourCoordinates.addAll( copyAndSet( partialNeighbourCoordinates, startingDimension, point.getCoordinate( startingDimension ) + 1 ) );
        return neighbourCoordinates;
    }

    private Collection<int[]> copyAndSet( List<int[]> partialNeighbourCoordinates, int dimension, int value ) {
        List<int[]> copy = partialNeighbourCoordinates.stream()
            .map( this::copy )
            .collect( toList() );
        copy.forEach( c -> c[dimension] = value );
        return copy;
    }

    private int[] copy( int[] coordinates ) {
        int[] copy = new int[coordinates.length];
        System.arraycopy( coordinates, 0, copy, 0, coordinates.length );
        return copy;
    }

    private int countActiveNeighbours( NPoint point ) {
        return (int) getNeighbours( point ).stream()
            .filter( activePoints::contains )
            .count();
    }

    int numberOfActiveCubes() {
        return activePoints.size();
    }
}
