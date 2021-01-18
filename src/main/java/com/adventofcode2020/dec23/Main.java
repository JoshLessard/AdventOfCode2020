package com.adventofcode2020.dec23;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws IOException {
        partA();
        partB();
    }

    private static void partA() throws IOException {
        CupCircle cups = buildCupCircle();
        for ( int i = 0; i < 100; ++i ) {
            cups.takeTurn();
        }

        System.out.println( "Part A: " + cups.cupsAfter( 1 ) );
    }

    private static void partB() throws IOException {
        CupCircle cups = buildCupCircle();
        for ( int cup = 10; cup <= 1000000; ++cup ) {
            cups.addCup( cup );
        }

        for ( int i = 0; i < 10000000; ++i ) {
            cups.takeTurn();
        }

        List<Integer> cupsAfter = cups.cupsAfter( 1, 2 );
        System.out.println( "Part B: " + (long) cupsAfter.get( 0 ) * cupsAfter.get( 1 ) );
    }

    private static CupCircle buildCupCircle() throws IOException {
        CupCircle cups = new CupCircle();
        String input = getInput( "src/main/resources/dec23/input.txt" ).get( 0 );
        input.chars()
            .map( c -> c - '0' )
            .forEach( cups::addCup );
        return cups;
    }
}
