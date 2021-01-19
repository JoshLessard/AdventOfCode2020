package com.adventofcode2020.dec24;

import com.adventofcode2020.common.Delta;
import com.adventofcode2020.common.Point;

enum HexDirection {

    EAST( new Delta( 2, 0 ) ),
    SOUTHEAST( new Delta( 1, 1 ) ),
    SOUTHWEST( new Delta( -1, 1 ) ),
    WEST( new Delta( -2, 0 ) ),
    NORTHWEST( new Delta( -1, -1 ) ),
    NORTHEAST( new Delta( 1, -1 ) );

    private final Delta delta;

    HexDirection( Delta delta ) {
        this.delta = delta;
    }

    Point applyTo( Point point ) {
        return point.transform( delta );
    }
}
