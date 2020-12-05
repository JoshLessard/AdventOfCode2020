package com.adventofcode2020.dec04;

@FunctionalInterface
public interface FieldValidator {

    boolean isValid( String value );
}
