package com.adventofcode2020.dec01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.binarySearch;
import static java.util.stream.Collectors.toCollection;

public class Main1 {

    public static void main( String[] args ) throws Exception {
        List<Integer> expenses = getSortedExpenses();
        for ( int i = 0; i < expenses.size() - 1; ++i ) {
            int expense1 = expenses.get( i );
            int expense2 = 2020 - expense1;
            if ( binarySearch( expenses, expense2 ) >= 0 ) {
                System.out.println( "Answer: " + (expense1 * expense2) );
                break;
            }
        }
    }

    private static List<Integer> getSortedExpenses() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec01/input.txt" ) ) ) {
            return reader.lines()
                .map( Integer::parseInt )
                .sorted()
                .collect( toCollection( ArrayList::new ) );
        }
    }
}
