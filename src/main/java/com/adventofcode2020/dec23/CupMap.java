package com.adventofcode2020.dec23;

class CupMap {

    private final CupNode[] nodesByCup;

    private int lowestCup = Integer.MAX_VALUE;
    private int highestCup = Integer.MIN_VALUE;

    CupMap( int capacity ) {
        this.nodesByCup = new CupNode[capacity];
    }

    void put( int cup, CupNode node ) {
        nodesByCup[cup - 1] = node;
        lowestCup = Math.min( lowestCup, cup );
        highestCup = Math.max( highestCup, cup );
    }

    CupNode get( int cup ) {
        return nodesByCup[cup - 1];
    }

    void remove( int cup ) {
        nodesByCup[cup - 1] = null;
        if ( cup == lowestCup ) {
            lowestCup = lowestCupHigherThan( cup );
        }
        if ( cup == highestCup ) {
            highestCup = highestCupLowerThan( cup );
        }
    }

    private int lowestCupHigherThan( int cup ) {
        for ( int i = cup + 1; ; ++i ) {
            if ( containsCup( i ) ) {
                return i;
            }
        }
    }

    private int highestCupLowerThan( int cup ) {
        for ( int i = cup - 1; ; --i ) {
            if ( containsCup( i ) ) {
                return i;
            }
        }
    }

    boolean containsCup( int cup ) {
        return nodesByCup[cup - 1] != null;
    }

    CupNode cupNodeBefore( int cup ) {
        while ( true ) {
            --cup;
            if ( cup < lowestCup ) {
                cup = highestCup;
            }
            if ( containsCup( cup ) ) {
                return get( cup );
            }
        }
    }
}
