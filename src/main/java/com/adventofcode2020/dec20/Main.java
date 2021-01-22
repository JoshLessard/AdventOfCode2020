package com.adventofcode2020.dec20;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Main {

    public static void main( String[] args ) throws IOException {
        Map<Integer, List<Tile>> tilesBySideValue = new HashMap<>();
        List<Tile> tiles = new TileParser().parseTiles( getInput( "src/main/resources/dec20/input.txt" ).iterator() );
        tiles.forEach( tile -> {
            tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.TOP ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.flippedSideValue( TileSide.TOP ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.RIGHT ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.flippedSideValue( TileSide.RIGHT ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.BOTTOM ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.flippedSideValue( TileSide.BOTTOM ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.sideValue( TileSide.LEFT ), t -> new ArrayList<>() ).add( tile );
            tilesBySideValue.computeIfAbsent( tile.flippedSideValue( TileSide.LEFT ), t -> new ArrayList<>() ).add( tile );
        } );
        Set<Integer> cornerIds = tiles.stream()
            .filter( tile -> isCorner( tile, tilesBySideValue ) )
            .map( Tile::id )
            .collect( toSet() );

        System.out.println( "Part A: " + cornerIds.stream().mapToLong( Integer::longValue ).reduce( 1, (a, b) -> a * b ) );
    }

    private static boolean isCorner( Tile tile, Map<Integer, List<Tile>> tilesBySideValue ) {
        int count = (int) Stream.of( TileSide.values() )
            .filter( s -> tilesBySideValue.get( tile.sideValue( s ) ).size() == 1 && tilesBySideValue.get( tile.flippedSideValue( s ) ).size() == 1 )
            .filter( s -> tilesBySideValue.get( tile.sideValue( s ) ).get( 0 ) == tile && tilesBySideValue.get( tile.flippedSideValue( s ) ).get( 0 ) == tile )
            .count();

        return count == 2;
    }
}
