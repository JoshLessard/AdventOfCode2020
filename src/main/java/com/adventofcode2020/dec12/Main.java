package com.adventofcode2020.dec12;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode2020.common.Delta;
import com.adventofcode2020.common.Point;

public class Main {

    private static final Pattern COMMAND_PATTERN = Pattern.compile( "([A-Z])(\\d+)" );
    private static final Point ORIGIN = new Point( 0, 0 );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec12/input.txt" );
        System.out.println( "Part A: " + execute( input, new PartAFerrySystemCommandFactory() ) );
        System.out.println( "Part B: " + execute( input, new PartBFerrySystemCommandFactory() ) );
    }

    private static int execute( List<String> input, FerrySystemCommandFactory commandFactory ) {
        FerrySystem system = newFerrySystem(ORIGIN);
        input.stream()
            .map( i -> toFerrySystemCommand( i, commandFactory ) )
            .forEach( c -> c.applyTo( system ) );

        return system.shipManhattanDistanceFrom( ORIGIN );
    }

    private static FerrySystem newFerrySystem( Point origin ) {
        return new FerrySystem(
            new Ship( origin, Direction.EAST ),
            origin.transform( new Delta( 10, -1 ) )
        );
    }

    private static FerrySystemCommand toFerrySystemCommand( String input, FerrySystemCommandFactory commandFactory ) {
        Matcher matcher = COMMAND_PATTERN.matcher( input );
        if ( matcher.matches() ) {
            return commandFactory.build(
                matcher.group( 1 ),
                Integer.parseInt( matcher.group( 2 ) )
            );
        }

        throw new IllegalArgumentException( "Unrecognized command: " + input );
    }
}
