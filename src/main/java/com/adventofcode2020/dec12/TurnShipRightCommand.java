package com.adventofcode2020.dec12;

class TurnShipRightCommand implements FerrySystemCommand {

    private final int degrees;

    TurnShipRightCommand( int degrees ) {
        this.degrees = degrees;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.rotateShip( degrees );
    }
}
