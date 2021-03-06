package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;

class MoveWaypointWestCommand implements FerrySystemCommand {

    private final int units;

    MoveWaypointWestCommand( int units ) {
        this.units = units;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.transformWaypoint( new Delta( -units, 0 ) );
    }
}
