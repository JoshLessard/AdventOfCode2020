package com.adventofcode2020.dec18;

class MultiplicationToken implements Token {

    @Override
    public TokenType type() {
        return TokenType.MULTIPLICATION;
    }

    @Override
    public int value() {
        throw new UnsupportedOperationException();
    }
}
