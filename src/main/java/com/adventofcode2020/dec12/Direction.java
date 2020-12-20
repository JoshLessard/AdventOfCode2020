package com.adventofcode2020.dec12;

import com.adventofcode2020.common.Delta;

enum Direction {

    NORTH {
        @Override
        Delta deltaFor( int units ) {
            return new Delta( 0, -units );
        }

        @Override
        Direction left() {
            return Direction.WEST;
        }

        @Override
        Direction right() {
            return Direction.EAST;
        }
    },
    SOUTH {
        @Override
        Delta deltaFor( int units ) {
            return new Delta( 0, units );
        }

        @Override
        Direction left() {
            return Direction.EAST;
        }

        @Override
        Direction right() {
            return Direction.WEST;
        }
    },
    EAST {
        @Override
        Delta deltaFor( int units ) {
            return new Delta( units, 0 );
        }

        @Override
        Direction left() {
            return Direction.NORTH;
        }

        @Override
        Direction right() {
            return Direction.SOUTH;
        }
    },
    WEST {
        @Override
        Delta deltaFor( int units ) {
            return new Delta( -units, 0 );
        }

        @Override
        Direction left() {
            return Direction.SOUTH;
        }

        @Override
        Direction right() {
            return Direction.NORTH;
        }
    };

    abstract Delta deltaFor( int units );
    abstract Direction left();
    abstract Direction right();
}
