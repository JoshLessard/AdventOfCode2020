package com.adventofcode2020.dec03;

import com.adventofcode2020.common.Grid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main( String[] args ) throws Exception {
        Grid<Element> grid = buildGrid();
        long numberOfTrees1 = countTrees( grid, 1, 1 );
        long numberOfTrees2 = countTrees( grid, 3, 1 );
        long numberOfTrees3 = countTrees( grid, 5, 1 );
        long numberOfTress4 = countTrees( grid, 7, 1 );
        long numberOfTrees5 = countTrees( grid, 1, 2 );

        System.out.println( "Part A: " + numberOfTrees2 );
        System.out.println( "Part B: " + (numberOfTrees1 * numberOfTrees2 * numberOfTrees3 * numberOfTress4 * numberOfTrees5) );
    }

    private static Grid<Element> buildGrid() throws IOException {
        List<String> input = getInput();
        int width = input.get( 0 ).length();
        int height = input.size();
        Grid<Element> grid = new Grid<>( width, height );
        for ( int y = 0; y < height; ++y ) {
            String line = input.get( y );
            for ( int x = 0; x < width; ++x ) {
                if ( line.charAt( x ) == '.' ) {
                    grid.set( x, y, Element.OPEN );
                } else if ( line.charAt( x ) == '#' ) {
                    grid.set( x, y, Element.TREE );
                }
            }
        }
        return grid;
    }

    private static List<String> getInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec03/input.txt" ) ) ) {
            return reader.lines().collect( toList() );
        }
    }

    private static int countTrees( Grid<Element> grid, int deltaX, int deltaY ) {
        int x = 0;
        int y = 0;
        int numberOfTrees = 0;
        while ( x < grid.getWidth() && y < grid.getHeight() ) {
            if ( grid.get( x, y ) == Element.TREE ) {
                ++numberOfTrees;
            }
            x = (x + deltaX) % grid.getWidth();
            y += deltaY;
        }
        return numberOfTrees;
    }
}