package com.adventofcode2020.dec12;

class RotateWaypointRightCommand implements FerrySystemCommand {

    private final int degrees;

    RotateWaypointRightCommand( int degrees ) {
        this.degrees = degrees;
    }

    @Override
    public void applyTo( FerrySystem system ) {
        system.rotateWaypointAroundShip( degrees );
    }
}
