package com.adventofcode2020.dec08;

class NoopInstruction implements Instruction {

    private final int argument;

    NoopInstruction( int argument ) {
        this.argument = argument;
    }

    @Override
    public int getArgument() {
        return argument;
    }

    @Override
    public int updateAccumulator( int accumulator ) {
        return accumulator;
    }

    @Override
    public int updateCounter( int counter ) {
        return counter + 1;
    }
}
