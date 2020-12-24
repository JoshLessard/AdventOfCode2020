package com.adventofcode2020.dec15;

import java.util.ArrayList;
import java.util.List;

class NumberStatistics {

    private final List<Integer> turnsSpoken;

    NumberStatistics() {
        this.turnsSpoken = new ArrayList<>();
    }

    public void addTurn( int turn ) {
        turnsSpoken.add( turn );
        while ( turnsSpoken.size() > 2 ) {
            turnsSpoken.remove( 0 );
        }
    }

    boolean isFirstTimeSpoken() {
        return turnsSpoken.size() < 2;
    }

    int differenceBetweenLastTwoTurns() {
        return turnsSpoken.get( 1 ) - turnsSpoken.get( 0 );
    }
}
