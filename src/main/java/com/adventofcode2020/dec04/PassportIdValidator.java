package com.adventofcode2020.dec04;

import java.util.regex.Pattern;

public class PassportIdValidator implements FieldValidator {

    private static final Pattern PATTERN = Pattern.compile( "\\d{9}" );

    @Override
    public boolean isValid( String value ) {
        return PATTERN.matcher( value ).matches();
    }
}
