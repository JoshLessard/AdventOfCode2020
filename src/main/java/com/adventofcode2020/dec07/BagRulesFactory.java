package com.adventofcode2020.dec07;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class BagRulesFactory {

    private static final Pattern CONTAINS_NO_OTHER_BAGS_RULE = Pattern.compile( "^([a-z ]+) bags contain no other bags." );
    private static final Pattern CONTAINS_OTHER_BAGS_RULE = Pattern.compile( "^([a-z ]+) bags contain (\\d+) ([a-z ]+) bags?([^.]+)?\\.$" );
    private static final Pattern EXTRA_BAGS_RULE = Pattern.compile( "(\\d+) ([a-z ]+) bags?" );

    BagRules build( List<String> rawRules ) {
        Map<String, Map<String, Integer>> rules = new HashMap<>();
        for ( String line : rawRules ) {
            Matcher emptyMatcher = CONTAINS_NO_OTHER_BAGS_RULE.matcher( line );
            if ( emptyMatcher.matches() ) {
                rules.put( emptyMatcher.group( 1 ), new HashMap<>() );
            } else {
                Matcher containsMatcher = CONTAINS_OTHER_BAGS_RULE.matcher( line );
                if ( containsMatcher.matches() ) {
                    Map<String, Integer> contents = new HashMap<>();
                    contents.put( containsMatcher.group( 3 ), Integer.parseInt( containsMatcher.group( 2 ) ) );
                    if ( containsMatcher.group( 4 ) != null ) {
                        Stream.of( containsMatcher.group( 4 ).split( ", " ) )
                            .map( EXTRA_BAGS_RULE::matcher )
                            .filter( Matcher::matches )
                            .forEach( m -> contents.put( m.group( 2 ), Integer.parseInt( m.group( 1 ) ) ) );
                    }
                    rules.put( containsMatcher.group( 1 ), contents );
                }
            }
        }

        return new BagRules( rules );
    }
}
