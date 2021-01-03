package com.adventofcode2020.dec19;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern RULE_PATTERN = Pattern.compile( "(\\d+): ([^\n]+)" );
    private static final Pattern CONCRETE_RULE_PATTERN = Pattern.compile( "\"([ab])\"" );
    private static final Pattern MESSAGE_PATTERN = Pattern.compile( "((a|b)+)" );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec19/input.txt" );
        Map<Integer, String> rulesByIndex = input.stream()
            .map( RULE_PATTERN::matcher )
            .filter( Matcher::matches )
            .collect( toMap( m -> Integer.parseInt( m.group( 1 ) ), m -> m.group( 2 ) ) );
        Map<Integer, RuleMatcher> ruleMatchersByIndex = new HashMap<>();
        rulesByIndex.forEach( (i, r) -> ruleMatchersByIndex.put( i, createRuleMatcher( rulesByIndex, i ) ) );

        long numMessages = input.stream()
            .filter( Main::isMessage )
            .filter( ruleMatchersByIndex.get( 0 )::matchesCompletely )
            .count();

        System.out.println( "Part A: " + numMessages );

        RuleMatcher ruleMatcher31 = ruleMatchersByIndex.get( 31 );
        RuleMatcher ruleMatcher42 = ruleMatchersByIndex.get( 42 );
        ruleMatchersByIndex.put( 0, new RuleMatcher0( ruleMatcher42, ruleMatcher42, ruleMatcher31 ) );

        numMessages = input.stream()
            .filter( Main::isMessage )
            .filter( ruleMatchersByIndex.get( 0 )::matchesCompletely )
            .count();

        System.out.println( "Part B: " + numMessages );
    }

    private static boolean isMessage( String string ) {
        return MESSAGE_PATTERN.matcher( string ).matches();
    }

    private static RuleMatcher createRuleMatcher( Map<Integer, String> rulesByIndex, Integer index ) {
        String rule = rulesByIndex.get( index );
        return Arrays.stream( rule.split( "\\|" ) )
            .map( String::trim )
            .map( rp -> createRuleMatcher( rulesByIndex, rp ) )
            .reduce( RuleMatcher::or )
            .orElseThrow();
    }

    private static RuleMatcher createRuleMatcher( Map<Integer, String> rulesByIndex, String rulePiece ) {
        Matcher matcher = CONCRETE_RULE_PATTERN.matcher( rulePiece );
        if ( matcher.matches() ) {
            return new DefaultRuleMatcher( matcher.group( 1 ) );
        } else {
            return Arrays.stream( rulePiece.split( " " ) )
                .map( Integer::valueOf )
                .map( i -> createRuleMatcher( rulesByIndex, i ) )
                .reduce( RuleMatcher::and )
                .orElseThrow();
        }
    }
}
