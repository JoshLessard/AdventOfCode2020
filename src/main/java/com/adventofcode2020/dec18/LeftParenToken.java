package com.adventofcode2020.dec18;

class LeftParenToken implements Token {

    @Override
    public TokenType type() {
        return TokenType.LEFT_PAREN;
    }

    @Override
    public int value() {
        throw new UnsupportedOperationException();
    }
}
