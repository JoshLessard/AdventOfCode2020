package com.adventofcode2020.dec04;

import java.util.regex.Pattern;

public class HairColourValidator implements FieldValidator {

    private static final Pattern PATTERN = Pattern.compile( "#[0-9a-z]{6}" );

    @Override
    public boolean isValid( String value ) {
        return PATTERN.matcher( value ).matches();
    }
}
