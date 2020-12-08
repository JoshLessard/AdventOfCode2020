package com.adventofcode2020.dec07;

import java.io.IOException;
import java.util.List;

import static com.adventofcode2020.common.InputUtilities.getInput;

public class Main {

    private static final BagRulesFactory RULES_FACTORY = new BagRulesFactory();

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec07/input.txt" );
        BagRules rules = RULES_FACTORY.build( input );

        System.out.println( "Part A: " + rules.numberOfBagsContaining( "shiny gold" ) );
        System.out.println( "Part B: " + rules.countBagsIn( "shiny gold" ) );
    }
}
