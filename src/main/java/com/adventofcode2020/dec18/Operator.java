package com.adventofcode2020.dec18;

import java.util.function.ToLongBiFunction;

enum Operator {

    ADD( Long::sum ),
    MULTIPLY( (a, b) -> a * b );

    private final ToLongBiFunction<Long, Long> function;

    Operator( ToLongBiFunction<Long, Long> function ) {
        this.function = function;
    }

    long applyTo( long leftValue, long rightValue ) {
        return function.applyAsLong( leftValue, rightValue );
    }
}
