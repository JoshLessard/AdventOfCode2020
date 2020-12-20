package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;

class MoveShipSouthCommand implements FerrySystemCommand {

    private final int units;

    public MoveShipSouthCommand( int units ) {
        this.units = units;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.transformShip( new Delta( 0, units ) );
    }
}
