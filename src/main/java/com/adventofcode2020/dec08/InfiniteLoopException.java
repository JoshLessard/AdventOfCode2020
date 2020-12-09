package com.adventofcode2020.dec08;

class InfiniteLoopException extends RuntimeException {

    private final int accumulator;
    private final int programCounter;

    InfiniteLoopException( int accumulator, int programCounter ) {
        this.accumulator = accumulator;
        this.programCounter = programCounter;
    }

    int getAccumulator() {
        return accumulator;
    }

    int getProgramCounter() {
        return programCounter;
    }
}
