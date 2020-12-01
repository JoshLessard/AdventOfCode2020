package com.adventofcode2020.dec01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

public class Main2 {

    public static void main( String[] args ) throws Exception {
        List<Integer> expenses = getSortedExpenses();
        for ( int i = 0; i < expenses.size() - 2; ++i ) {
            int expense1 = expenses.get( i );
            for ( int j = i + 1; j < expenses.size() - 1; ++j ) {
                int expense2 = expenses.get( j );
                for ( int k = j + 1; k < expenses.size(); ++k ) {
                    int expense3 = expenses.get( k );
                    if ( expense1 + expense2 + expense3 == 2020 ) {
                        System.out.println( "Answer: " + (expense1 * expense2 * expense3) );
                        System.exit( 0 );
                    } else if ( expense1 + expense2 + expense3 > 2020 ) {
                        break;
                    }
                }
            }
        }
    }

    private static List<Integer> getSortedExpenses() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec01/input.txt" ) ) ) {
            return reader.lines()
                .map( Integer::parseInt )
                .distinct()
                .sorted().collect( toCollection( ArrayList::new ) );
        }
    }
}
