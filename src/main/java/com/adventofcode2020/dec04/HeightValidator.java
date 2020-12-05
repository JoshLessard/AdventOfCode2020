package com.adventofcode2020.dec04;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeightValidator implements FieldValidator {

    private static final Pattern CM_PATTERN = Pattern.compile( "(\\d+)cm" );
    private static final Pattern IN_PATTERN = Pattern.compile( "(\\d+)in" );

    @Override
    public boolean isValid( String value ) {
        Matcher matcher = CM_PATTERN.matcher( value );
        if ( matcher.matches() ) {
            return isValidCM( Integer.parseInt( matcher.group( 1 ) ) );
        }

        matcher = IN_PATTERN.matcher( value );
        if ( matcher.matches() ) {
            return isValidIN( Integer.parseInt( matcher.group( 1 ) ) );
        }

        return false;
    }

    private boolean isValidCM( int cm ) {
        return 150 <= cm && cm <= 193;
    }

    private boolean isValidIN( int in ) {
        return 59 <= in && in <= 76;
    }
}
