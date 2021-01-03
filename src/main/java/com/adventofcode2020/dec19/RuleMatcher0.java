package com.adventofcode2020.dec19;

class RuleMatcher0 implements RuleMatcher {

    private final RuleMatcher ruleMatcher1;
    private final RuleMatcher ruleMatcher2;
    private final RuleMatcher ruleMatcher3;

    RuleMatcher0( RuleMatcher ruleMatcher1, RuleMatcher ruleMatcher2, RuleMatcher ruleMatcher3 ) {
        this.ruleMatcher1 = ruleMatcher1;
        this.ruleMatcher2 = ruleMatcher2;
        this.ruleMatcher3 = ruleMatcher3;
    }

    @Override
    public String matches( String string ) {
        String remainingToMatch;
        while ( true ) {
            remainingToMatch = ruleMatcher1.matches( string );
            if ( remainingToMatch == null ) {
                return null;
            } else {
                string = remainingToMatch;
                String otherRemainingToMatch = secondAndThirdMatch( remainingToMatch );
                if ( otherRemainingToMatch != null ) {
                    return otherRemainingToMatch;
                }
            }
        }
    }

    private String secondAndThirdMatch( String string ) {
        int numberOfMatches = 0;
        String remainingToMatch;
        while ( true ) {
            remainingToMatch = ruleMatcher2.matches( string );
            if ( remainingToMatch == null ) {
                return null;
            } else {
                string = remainingToMatch;
                ++numberOfMatches;
                String otherRemainingToMatch = thirdMatches( remainingToMatch, numberOfMatches );
                if ( otherRemainingToMatch != null ) {
                    return otherRemainingToMatch;
                }
            }
        }
    }

    private String thirdMatches( String string, int requireNumberOfMatches ) {
        int numberOfMatches = 0;
        String remainingToMatch;
        while ( true ) {
            remainingToMatch = ruleMatcher3.matches( string );
            if ( remainingToMatch == null ) {
                return null;
            } else {
                string = remainingToMatch;
                ++numberOfMatches;
                if ( numberOfMatches == requireNumberOfMatches ) {
                    return remainingToMatch;
                }
            }
        }
    }
}