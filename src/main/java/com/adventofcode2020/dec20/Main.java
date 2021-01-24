package com.adventofcode2020.dec20;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.adventofcode2020.common.Point;

public class Main {

    private static final Set<Point> OCCUPIED_SEA_MONSTER_COORDINATES = Set.of(
        new Point( 18, 0 ),
            new Point( 0, 1 ),
            new Point( 5, 1 ),
            new Point( 6, 1 ),
            new Point( 11, 1 ),
            new Point( 12, 1 ),
            new Point( 17, 1 ),
            new Point( 18, 1 ),
            new Point( 19, 1 ),
            new Point( 1, 2 ),
            new Point( 4, 2 ),
            new Point( 7, 2 ),
            new Point( 10, 2 ),
            new Point( 13, 2 ),
            new Point( 16, 2 )
    );

    public static void main( String[] args ) throws IOException {
        List<Tile> tiles = new TileParser().parseTiles( getInput( "src/main/resources/dec20/input.txt" ).iterator() );
        TileGrid grid = new TileGridBuilder( tiles ).build();
        System.out.println( "Part A: " + grid.cornerIds().stream().mapToLong( Integer::longValue ).reduce( 1, (a, b) -> a * b ) );

        Tile gridAsSingleTile = grid.map( Tile::removeBorder ).toSingleTile();
        Tile seaMonsterTile = createSeaMonsterTile();

        System.out.println( gridAsSingleTile );
    }

    private static Tile createSeaMonsterTile() {
        TileSpace[][] tileSpaces = new TileSpace[3][20];
        for ( int y = 0; y < tileSpaces.length; ++y ) {
            TileSpace[] row = tileSpaces[y];
            for ( int x = 0; x < row.length; ++x ) {
                Point point = new Point( x, y );
                row[x] = OCCUPIED_SEA_MONSTER_COORDINATES.contains( point )
                    ? TileSpace.OCCUPIED
                    : TileSpace.MISSING;
            }
        }

        return new Tile( 0, tileSpaces );
    }
}
