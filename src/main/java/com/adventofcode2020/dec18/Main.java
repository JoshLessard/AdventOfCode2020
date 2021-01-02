package com.adventofcode2020.dec18;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec18/input.txt" );

        System.out.println( "Part A: " + evaluateWithNoPrecedence( input ) );
        System.out.println( "Part B: " + evaluateWithPrecedence( Operator.ADD, input ) );
    }

    private static long evaluateWithNoPrecedence( List<String> input ) {
        return evaluateWithPrecedence( null, input );
    }

    private static long evaluateWithPrecedence( Operator highPrecedenceOperator, List<String> input ) {
        Evaluator evaluator = new Evaluator( highPrecedenceOperator );
        return input.stream()
            .map( Tokenizer::new )
            .mapToLong( evaluator::evaluate )
            .sum();
    }
}
