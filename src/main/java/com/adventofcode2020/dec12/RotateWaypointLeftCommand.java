package com.adventofcode2020.dec12;

class RotateWaypointLeftCommand implements FerrySystemCommand {

    private final int degrees;

    RotateWaypointLeftCommand( int degrees ) {
        this.degrees = degrees;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.rotateWaypointAroundShip( -degrees );
    }
}
