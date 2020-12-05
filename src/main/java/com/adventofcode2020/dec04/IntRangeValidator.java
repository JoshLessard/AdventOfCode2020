package com.adventofcode2020.dec04;

public class IntRangeValidator implements FieldValidator {

    private final int minValue;
    private final int maxValue;

    IntRangeValidator( int minValue, int maxValue ) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean isValid( String value ) {
        try {
            int intValue = Integer.parseInt( value );
            return minValue <= intValue && intValue <= maxValue;
        } catch ( NumberFormatException e ) {
            return false;
        }
    }
}
