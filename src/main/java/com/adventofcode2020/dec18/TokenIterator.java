package com.adventofcode2020.dec18;

import java.util.NoSuchElementException;

class TokenIterator implements PeekingIterator<Token> {

    private final char[] chars;
    private int index = 0;
    private Token peekedToken;

    TokenIterator( String string ) {
        this.chars = string.toCharArray();
        skipWhitespace();
    }

    private void skipWhitespace() {
        while ( index < chars.length && Character.isWhitespace( chars[index] ) ) {
            ++index;
        }
    }

    @Override
    public boolean hasNext() {
        return peekedToken != null || index < chars.length;
    }

    @Override
    public Token next() {
        ensureHasNext();
        if ( peekedToken == null ) {
            Token token = nextToken();
            skipWhitespace();
            return token;
        } else {
            Token token = peekedToken;
            peekedToken = null;
            return token;
        }
    }

    private void ensureHasNext() {
        if ( ! hasNext() ) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Token peek() {
        ensureHasNext();
        if ( peekedToken == null ) {
            peekedToken = nextToken();
            skipWhitespace();
        }
        return peekedToken;
    }

    private Token nextToken() {
        if ( chars[index] == '(' ) {
            ++index;
            return new LeftParenToken();
        }
        if ( chars[index] == ')' ) {
            ++index;
            return new RightParenToken();
        }
        if ( chars[index] == '+' ) {
            ++index;
            return new AdditionToken();
        }
        if ( chars[index] == '*' ) {
            ++index;
            return new MultiplicationToken();
        }
        if ( Character.isDigit( chars[index] ) ) {
            StringBuilder builder = new StringBuilder();
            while ( index < chars.length && Character.isDigit( chars[index] ) ) {
                builder.append( chars[index++] );
            }
            return new IntegerToken( Integer.parseInt( builder.toString() ) );
        }
        throw new IllegalStateException( "Unrecognized token: " + chars[index] );
    }
}
