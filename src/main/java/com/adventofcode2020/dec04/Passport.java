package com.adventofcode2020.dec04;

import java.util.Map;
import java.util.Set;

public class Passport {

    private static final Set<String> REQUIRED_FIELDS = Set.of(
        "byr",
        "iyr",
        "eyr",
        "hgt",
        "hcl",
        "ecl",
        "pid"
    );

    private static final Map<String, FieldValidator> FIELD_VALIDATORS = Map.of(
        "byr", new IntRangeValidator( 1920, 2002 ),
        "iyr", new IntRangeValidator( 2010, 2020 ),
        "eyr", new IntRangeValidator( 2020, 2030 ),
        "hgt", new HeightValidator(),
        "hcl", new HairColourValidator(),
        "ecl", new EyeColourValidator(),
        "pid", new PassportIdValidator(),
        "cid", v -> true
    );

    private final Map<String, String> fields;

    public Passport( Map<String, String> fields ) {
        this.fields = Map.copyOf( fields );
    }

    public boolean hasRequiredFields() {
        return fields.keySet().containsAll( REQUIRED_FIELDS );
    }

    public boolean allFieldsValid() {
        return fields.entrySet().stream()
            .allMatch( e -> FIELD_VALIDATORS.get( e.getKey() ).isValid( e.getValue() ) );
    }
}
