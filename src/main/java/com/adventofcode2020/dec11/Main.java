package com.adventofcode2020.dec11;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import com.adventofcode2020.common.Grid;

public class Main {

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec11/input.txt" );
        Grid<Element> grid = buildGrid( input );

        Grid<Element> partAGrid = mapToSteadyState( grid, e -> true, Main::mapElementForPartA );
        System.out.println( "Part A: " + partAGrid.count( e -> e.equals( Element.OCCUPIED_SEAT ) ) );

        Grid<Element> partBGrid = mapToSteadyState( grid, e -> e.equals( Element.EMPTY_SEAT ) || e.equals( Element.OCCUPIED_SEAT ), Main::mapElementForPartB );
        System.out.println( "Part B: " + partBGrid.count( e -> e.equals( Element.OCCUPIED_SEAT ) ) );
    }

    private static Grid<Element> buildGrid( List<String> input ) {
        int width = input.get( 0 ).length();
        int height = input.size();
        Grid<Element> grid = new Grid<>( width, height );
        for ( int y = 0; y < input.size(); ++y ) {
            String line = input.get( y );
            for ( int x = 0; x < line.length(); ++x ) {
                Element element = Element.parse( line.charAt( x ) );
                grid.set( x, y, element );
            }
        }
        return grid;
    }

    private static Grid<Element> mapToSteadyState( Grid<Element> previousGrid, Predicate<Element> neighbourFilter, BiFunction<Element, List<Element>, Element> elementMapper ) {
        Grid<Element> currentGrid = previousGrid.map( neighbourFilter, elementMapper );
        while ( ! previousGrid.equals( currentGrid ) ) {
            previousGrid = currentGrid;
            currentGrid = previousGrid.map( neighbourFilter, elementMapper );
        }
        return currentGrid;
    }

    private static Element mapElementForPartA( Element element, List<Element> neighbours ) {
        int numberOfOccupiedSeats = (int) neighbours.stream().filter( e -> e.equals( Element.OCCUPIED_SEAT ) ).count();
        if ( element.equals( Element.EMPTY_SEAT ) && numberOfOccupiedSeats == 0 ) {
            return Element.OCCUPIED_SEAT;
        } else if ( element.equals( Element.OCCUPIED_SEAT ) && numberOfOccupiedSeats >= 4 ) {
            return Element.EMPTY_SEAT;
        } else {
            return element;
        }
    }

    private static Element mapElementForPartB( Element element, List<Element> neighbours ) {
        int numberOfOccupiedSeats = (int) neighbours.stream().filter( e -> e.equals( Element.OCCUPIED_SEAT ) ).count();
        if ( element.equals( Element.EMPTY_SEAT ) && numberOfOccupiedSeats == 0 ) {
            return Element.OCCUPIED_SEAT;
        } else if ( element.equals( Element.OCCUPIED_SEAT ) && numberOfOccupiedSeats >= 5 ) {
            return Element.EMPTY_SEAT;
        } else {
            return element;
        }
    }
}
