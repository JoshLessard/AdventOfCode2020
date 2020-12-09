package com.adventofcode2020.dec08;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.adventofcode2020.common.InputUtilities.getInput;

public class Main {

    private static final ProgramFactory PROGRAM_FACTORY = new ProgramFactory();
    private static final Computer COMPUTER = new Computer();

    public static void main( String[] args ) throws IOException {
        List<Instruction> instructions = PROGRAM_FACTORY.parseProgram( getInput( "src/main/resources/dec08/input.txt" ) );
        partA( instructions );
        partB( instructions );
    }

    private static void partA( List<Instruction> instructions ) {
        try {
            COMPUTER.execute( instructions );
            throw new RuntimeException( "Expected an infinite loop." );
        } catch ( InfiniteLoopException e ) {
            System.out.println( "Part A: " + e.getAccumulator() );
        }
    }

    private static void partB( List<Instruction> instructions ) {
        int lastSwappedInstructionIndex = -1;
        while ( true ) {
            lastSwappedInstructionIndex = getNextSwappableInstructionIndex( instructions, lastSwappedInstructionIndex + 1 );
            List<Instruction> updatedInstructions = copyAndSwap( instructions, lastSwappedInstructionIndex );
            try {
                int accumulator = COMPUTER.execute( updatedInstructions );
                System.out.println( "Part B: " + accumulator );
                break;
            } catch ( InfiniteLoopException e ) {
            }
        }
    }

    private static int getNextSwappableInstructionIndex( List<Instruction> instructions, int startIndex ) {
        for ( int i = startIndex; i < instructions.size(); ++i ) {
            Instruction instruction = instructions.get( i );
            if ( instruction instanceof JumpInstruction || instruction instanceof NoopInstruction ) {
                return i;
            }
        }
        throw new RuntimeException( "Should not have reached here." );
    }

    private static List<Instruction> copyAndSwap( List<Instruction> instructions, int indexToSwap ) {
        List<Instruction> swapped = new ArrayList<>( instructions );
        Instruction instructionToSwap = instructions.get( indexToSwap );
        if ( instructionToSwap instanceof JumpInstruction ) {
            swapped.set( indexToSwap, new NoopInstruction( instructionToSwap.getArgument() ) );
        } else {
            swapped.set( indexToSwap, new JumpInstruction( instructionToSwap.getArgument() ) );
        }
        return swapped;
    }
}
