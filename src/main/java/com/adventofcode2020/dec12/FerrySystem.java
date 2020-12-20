package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;
import com.adventofcode2020.common.Point;

class FerrySystem {

    private final Ship ship;
    private Point waypoint;

    FerrySystem( Ship ship, Point waypoint ) {
        this.ship = ship;
        this.waypoint = waypoint;
    }

    void transformShip( Delta delta ) {
        ship.transform( delta );
    }

    void moveShipForward( int units ) {
        ship.forward( units );
    }

    void rotateShip( int degrees ) {
        ship.rotate( degrees );
    }

    int shipManhattanDistanceFrom( Point point ) {
        return ship.manhattanDistanceFrom( point );
    }

    void transformWaypoint( Delta delta ) {
        waypoint = waypoint.transform( delta );
    }

    void rotateWaypointAroundShip( int degrees ) {
        waypoint = waypoint.rotateAbout( ship.getPosition(), Math.PI * degrees / 180 );
    }

    void transformShipToWaypoint() {
        Delta deltaToWaypoint = waypoint.deltaFrom( ship.getPosition() );
        ship.transform( deltaToWaypoint );
        waypoint = waypoint.transform( deltaToWaypoint );
    }
}
