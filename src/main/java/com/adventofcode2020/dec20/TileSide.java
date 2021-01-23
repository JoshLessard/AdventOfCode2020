package com.adventofcode2020.dec20;

import com.adventofcode2020.common.Delta;
import com.adventofcode2020.common.Point;

enum TileSide {
    TOP( 0, -1 ) {
        @Override
        TileSide complement() {
            return BOTTOM;
        }
    },
    RIGHT( 1, 0 ) {
        @Override
        TileSide complement() {
            return LEFT;
        }
    },
    BOTTOM( 0, 1 ) {
        @Override
        TileSide complement() {
            return TOP;
        }
    },
    LEFT( -1, 0 ) {
        @Override
        TileSide complement() {
            return RIGHT;
        }
    };

    private final Delta delta;

    TileSide( int deltaX, int deltaY ) {
        this.delta = new Delta( deltaX, deltaY );
    }

    abstract TileSide complement();

    Point applyTo( Point point ) {
        return point.transform( delta );
    }
}
