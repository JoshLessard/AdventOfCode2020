package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;

class MoveShipEastCommand implements FerrySystemCommand {

    private final int units;

    MoveShipEastCommand( int units ) {
        this.units = units;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.transformShip( new Delta( units, 0 ) );
    }
}
