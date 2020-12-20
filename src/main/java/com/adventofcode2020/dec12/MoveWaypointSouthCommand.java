package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;

class MoveWaypointSouthCommand implements FerrySystemCommand {

    private final int units;

    MoveWaypointSouthCommand( int units ) {
        this.units = units;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.transformWaypoint( new Delta( 0, units ) );
    }
}
