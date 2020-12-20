package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;
import com.adventofcode2020.common.Point;

class Ship {

    private Point position;
    private Direction direction;

    Ship( Point startingPosition, Direction startingDirection ) {
        this.position = startingPosition;
        this.direction = startingDirection;
    }

    void transform( Delta delta ) {
        position = position.transform( delta );
    }

    void rotate( int degrees ) {
        ensureRightAngle( degrees );
        int numberOfTurns = Math.abs( degrees / 90 );
        for ( int i = 0; i < numberOfTurns; ++i ) {
            direction = degrees > 0 ? direction.right() : direction.left();
        }
    }

    void forward( int units ) {
        transform( direction.deltaFor( units ) );
    }

    int manhattanDistanceFrom( Point point ) {
        return position.manhattanDistanceFrom( point );
    }

    Point getPosition() {
        return position;
    }

    private void ensureRightAngle( int degrees ) {
        if ( degrees % 90 != 0 ) {
            throw new IllegalArgumentException( "Only right angles are supported." );
        }
    }
}
