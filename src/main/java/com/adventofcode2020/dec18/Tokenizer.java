package com.adventofcode2020.dec18;

class Tokenizer {

    private final String string;

    Tokenizer( String string ) {
        this.string = string;
    }

    public PeekingIterator<Token> tokens() {
        return new TokenIterator( string );
    }
}
