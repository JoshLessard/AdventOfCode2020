package com.adventofcode2020.dec04;

import java.util.Set;

public class EyeColourValidator implements FieldValidator {

    private static final Set<String> ALLOWED_VALUES = Set.of(
        "amb",
        "blu",
        "brn",
        "gry",
        "grn",
        "hzl",
        "oth"
    );

    @Override
    public boolean isValid( String value ) {
        return ALLOWED_VALUES.contains( value );
    }
}
