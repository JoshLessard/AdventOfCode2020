package com.adventofcode2020.dec19;

class DefaultRuleMatcher implements RuleMatcher {

    private final String target;

    DefaultRuleMatcher( String target ) {
        this.target = target;
    }

    @Override
    public String matches( String string ) {
        int index = 0;
        for ( ; index < target.length(); ++index ) {
            if ( index >= string.length() || target.charAt( index ) != string.charAt( index ) ) {
                return null;
            }
        }
        return string.substring( index );
    }
}
