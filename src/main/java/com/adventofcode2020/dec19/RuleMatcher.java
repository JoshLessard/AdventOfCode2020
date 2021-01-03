package com.adventofcode2020.dec19;

interface RuleMatcher {

    default boolean matchesCompletely( String string ) {
        String leftToMatch = matches( string );
        return leftToMatch != null && leftToMatch.isEmpty();
    }

    String matches( String string );

    default RuleMatcher and( RuleMatcher other ) {
        return string -> {
            String leftToMatch = matches( string );
            return leftToMatch == null
                ? null
                : other.matches( leftToMatch );
        };
    }

    default RuleMatcher or( RuleMatcher other ) {
        return string -> {
            String leftToMatch = matches( string );
            return leftToMatch == null
                ? other.matches( string )
                : leftToMatch;
        };
    }
}
