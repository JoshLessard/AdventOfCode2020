package com.adventofcode2020.dec18;

import java.util.Stack;

class Evaluator {

    private final Operator lowPrecedenceOperator;

    Evaluator( Operator highPrecedenceOperator ) {
        this.lowPrecedenceOperator = getLowPrecedenceOperator( highPrecedenceOperator );
    }

    private Operator getLowPrecedenceOperator( Operator highPrecedenceOperator ) {
        if ( highPrecedenceOperator == null ) {
            return null;
        } else {
            return highPrecedenceOperator.equals( Operator.ADD ) ? Operator.MULTIPLY : Operator.ADD;
        }
    }

    long evaluate( Tokenizer tokenizer ) {
        return evaluateExpression( tokenizer.tokens(), new Stack<>() );
    }

    private long evaluateExpression( PeekingIterator<Token> tokens, Stack<TokenType> parenStack ) {
        long value = evaluateTerm( tokens, parenStack );
        while ( tokens.hasNext() ) {
            if ( tokens.peek().type().equals( TokenType.RIGHT_PAREN ) ) {
                if ( parenStack.isEmpty() || ! parenStack.pop().equals( TokenType.LEFT_PAREN ) ) {
                    throw new IllegalStateException( "Unbalanced right parenthesis." );
                }
                tokens.next();
                return value;
            }
            Operator operator = evaluateOperator( tokens.next() );
            value = operator.applyTo( value, evaluateTerm( tokens, parenStack ) );
        }
        return value;
    }

    private Operator evaluateOperator( Token token ) {
        if ( token.type().equals( TokenType.ADDITION ) ) {
            return Operator.ADD;
        } else if ( token.type().equals( TokenType.MULTIPLICATION ) ) {
            return Operator.MULTIPLY;
        }

        throw new IllegalStateException( "Expected operator, but found " + token );
    }

    private long evaluateTerm( PeekingIterator<Token> tokens, Stack<TokenType> parenStack ) {
        long value = evaluateValue( tokens, parenStack );
        while ( tokens.hasNext() ) {
            if ( tokens.peek().type().equals( TokenType.RIGHT_PAREN ) ) {
                return value;
            } else {
                Operator operator = evaluateOperator( tokens.peek() );
                if ( operator.equals( lowPrecedenceOperator ) ) {
                    return value;
                } else {
                    tokens.next();
                    value = operator.applyTo( value, evaluateValue( tokens, parenStack ) );
                }
            }
        }

        return value;
    }

    private long evaluateValue( PeekingIterator<Token> tokens, Stack<TokenType> parenStack ) {
        Token token = tokens.next();
        if ( token.type().equals( TokenType.INTEGER ) ) {
            return token.value();
        } else if ( token.type().equals( TokenType.LEFT_PAREN ) ) {
            parenStack.push( TokenType.LEFT_PAREN );
            return evaluateExpression( tokens, parenStack );
        }

        throw new IllegalStateException( "Unexpected token: " + token );
    }
}