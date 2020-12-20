package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;

class MoveWaypointNorthCommand implements FerrySystemCommand {

    private final int units;

    MoveWaypointNorthCommand( int units ) {
        this.units = units;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.transformWaypoint( new Delta( 0, -units ) );
    }
}
