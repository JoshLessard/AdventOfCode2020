package com.adventofcode2020.dec18;

class IntegerToken implements Token {

    private final int value;

    IntegerToken( int value ) {
        this.value = value;
    }

    @Override
    public TokenType type() {
        return TokenType.INTEGER;
    }

    @Override
    public int value() {
        return value;
    }
}
