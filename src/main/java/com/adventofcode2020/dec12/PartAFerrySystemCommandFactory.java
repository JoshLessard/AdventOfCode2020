package com.adventofcode2020.dec12;

class PartAFerrySystemCommandFactory implements FerrySystemCommandFactory {

    @Override
    public FerrySystemCommand build( String commandName, int commandArgument ) {
        switch ( commandName ) {
            case "N":
                return new MoveShipNorthCommand( commandArgument );
            case "S":
                return new MoveShipSouthCommand( commandArgument );
            case "E":
                return new MoveShipEastCommand( commandArgument );
            case "W":
                return new MoveShipWestCommand( commandArgument );
            case "L":
                return new TurnShipLeftCommand( commandArgument );
            case "R":
                return new TurnShipRightCommand( commandArgument );
            case "F":
                return new MoveShipForwardCommand( commandArgument );
        }

        throw new IllegalArgumentException( "Unrecognized command name: " + commandName );
    }
}
