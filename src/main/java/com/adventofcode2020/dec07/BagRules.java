package com.adventofcode2020.dec07;

import java.util.Map;

class BagRules {

    private final Map<String, Map<String, Integer>> rules;

    BagRules( Map<String, Map<String, Integer>> rules ) {
        this.rules = Map.copyOf( rules );
    }

    int numberOfBagsContaining( String bag ) {
        return (int) rules.keySet().stream()
            .filter( b -> canContain( bag, b ) )
            .count();
    }

    private boolean canContain( String bagBeingSearchedFor, String outermostBag ) {
        Map<String, Integer> contents = rules.get( outermostBag );
        if ( contents.containsKey( bagBeingSearchedFor ) ) {
            return true;
        }
        return contents
            .keySet().stream()
            .anyMatch( bag -> canContain( bagBeingSearchedFor, bag ) );
    }

    int countBagsIn( String bag ) {
        Map<String, Integer> contents = rules.get( bag );
        int directlyContainedBagCount = contents.values().stream().mapToInt( v -> v ).sum();
        int indirectlyContainedBagCount = contents
            .entrySet().stream()
            .mapToInt( e -> e.getValue() * countBagsIn( e.getKey() ) ).sum();

        return directlyContainedBagCount + indirectlyContainedBagCount;
    }
}
