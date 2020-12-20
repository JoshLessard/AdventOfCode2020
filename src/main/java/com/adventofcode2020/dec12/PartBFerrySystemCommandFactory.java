package com.adventofcode2020.dec12;

class PartBFerrySystemCommandFactory implements FerrySystemCommandFactory {

    @Override
    public FerrySystemCommand build( String commandName, int commandArgument ) {
        switch ( commandName ) {
            case "N":
                return new MoveWaypointNorthCommand( commandArgument );
            case "S":
                return new MoveWaypointSouthCommand( commandArgument );
            case "E":
                return new MoveWaypointEastCommand( commandArgument );
            case "W":
                return new MoveWaypointWestCommand( commandArgument );
            case "L":
                return new RotateWaypointLeftCommand( commandArgument );
            case "R":
                return new RotateWaypointRightCommand( commandArgument );
            case "F":
                return new MoveShipToWaypointCommand( commandArgument );
        }

        throw new IllegalArgumentException( "Unrecognized command name: " + commandName );
    }
}
