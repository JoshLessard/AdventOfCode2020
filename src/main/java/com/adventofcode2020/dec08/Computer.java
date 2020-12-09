package com.adventofcode2020.dec08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Computer {

    int execute( List<Instruction> instructions ) {
        int accumulator = 0;
        int programCounter = 0;
        Set<Instruction> executedInstructions = new HashSet<>();

        while ( programCounter < instructions.size() && ! executedInstructions.contains( instructions.get( programCounter ) ) ) {
            Instruction currentInstruction = instructions.get( programCounter );
            accumulator = currentInstruction.updateAccumulator( accumulator );
            programCounter = currentInstruction.updateCounter( programCounter );
            executedInstructions.add( currentInstruction );
        }

        if ( programCounter != instructions.size() ) {
            throw new InfiniteLoopException( accumulator, programCounter );
        }

        return accumulator;
    }
}
