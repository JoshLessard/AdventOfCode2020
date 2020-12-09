package com.adventofcode2020.dec08;

class JumpInstruction implements Instruction {

    private final int argument;

    JumpInstruction( int argument ) {
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
        return counter + argument;
    }
}
