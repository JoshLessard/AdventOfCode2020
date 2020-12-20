package com.adventofcode2020.dec12;

class MoveShipToWaypointCommand implements FerrySystemCommand {

    private final int numberOfTimes;

    MoveShipToWaypointCommand( int numberOfTimes ) {
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        for ( int i = 0; i < numberOfTimes; ++i ) {
            system.transformShipToWaypoint();
        }
    }
}
