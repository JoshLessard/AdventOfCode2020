package com.adventofcode2020.dec22;

import java.util.List;

class HandResult {

    private final int winningPlayer;
    private final List<Integer> winningHand;

    HandResult( int winningPlayer, List<Integer> winningHand ) {
        this.winningPlayer = winningPlayer;
        this.winningHand = winningHand;
    }

    int winningPlayer() {
        return winningPlayer;
    }

    List<Integer> winningHand() {
        return winningHand;
    }
}
