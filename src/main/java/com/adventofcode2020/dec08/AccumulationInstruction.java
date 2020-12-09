package com.adventofcode2020.dec08;

class AccumulationInstruction implements Instruction {

    private final int argument;

    AccumulationInstruction( int argument ) {
        this.argument = argument;
    }

    @Override
    public int getArgument() {
        return argument;
    }

    @Override
    public int updateAccumulator( int accumulator ) {
        return accumulator + argument;
    }

    @Override
    public int updateCounter( int counter ) {
        return counter + 1;
    }
}
