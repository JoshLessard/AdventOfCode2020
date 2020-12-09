package com.adventofcode2020.dec08;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

class ProgramFactory {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile( "([a-z]+) ([+-]\\d+)" );

    List<Instruction> parseProgram( List<String> input ) {
        return input.stream()
            .map( this::toInstruction )
            .collect( toList() );
    }

    private Instruction toInstruction( String input ) {
        Matcher matcher = INSTRUCTION_PATTERN.matcher( input );
        if ( matcher.matches() ) {
            String operation = matcher.group( 1 );
            int argument = Integer.parseInt( matcher.group( 2 ) );
            switch ( operation ) {
                case "acc":
                    return new AccumulationInstruction( argument );
                case "jmp":
                    return new JumpInstruction( argument );
                case "nop":
                    return new NoopInstruction( argument );
            }
        }
        throw new IllegalArgumentException( '"' + input + "\" is not a valid instruction." );
    }
}
