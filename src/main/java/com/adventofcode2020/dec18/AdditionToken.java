package com.adventofcode2020.dec18;

class AdditionToken implements Token {

    @Override
    public TokenType type() {
        return TokenType.ADDITION;
    }

    @Override
    public int value() {
        throw new UnsupportedOperationException();
    }
}
