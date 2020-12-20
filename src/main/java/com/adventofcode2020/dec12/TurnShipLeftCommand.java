package com.adventofcode2020.dec12;

class TurnShipLeftCommand implements FerrySystemCommand {

    private final int degrees;

    TurnShipLeftCommand( int degrees ) {
        this.degrees = degrees;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.rotateShip( -degrees );
    }
}
