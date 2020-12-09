package com.adventofcode2020.dec08;

interface Instruction {

    int getArgument();
    int updateAccumulator( int accumulator );
    int updateCounter( int counter );
}
