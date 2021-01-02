package com.adventofcode2020.dec18;

class RightParenToken implements Token {

    @Override
    public TokenType type() {
        return TokenType.RIGHT_PAREN;
    }

    @Override
    public int value() {
        throw new UnsupportedOperationException();
    }
}
