package com.adventofcode2020.dec12;

class MoveShipForwardCommand implements FerrySystemCommand {

    private final int units;

    MoveShipForwardCommand( int units ) {
        this.units = units;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.moveShipForward( units );
    }
}
