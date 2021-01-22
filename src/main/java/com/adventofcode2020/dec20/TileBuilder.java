package com.adventofcode2020.dec20;

import java.util.ArrayList;
import java.util.List;

class TileBuilder {

    private final int id;
    private final List<TileSpace[]> tileRows = new ArrayList<>();

    TileBuilder( int id ) {
        this.id = id;
    }

    void addRow( TileSpace[] row ) {
        tileRows.add( row );
    }

    Tile build() {
        TileSpace[][] tileSpaces = new TileSpace[tileRows.size()][];
        for ( int i = 0; i < tileRows.size(); ++i ) {
            TileSpace[] row = tileRows.get( i );
            tileSpaces[i] = new TileSpace[row.length];
            System.arraycopy( row, 0, tileSpaces[i], 0, row.length );
        }
        return new Tile( id, tileSpaces );
    }
}
