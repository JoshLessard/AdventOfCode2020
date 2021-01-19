package com.adventofcode2020.dec24;

@FunctionalInterface
interface TileFlipPredicate {

    boolean shouldFlip( Colour currentColour, int blackNeighbours );
}
