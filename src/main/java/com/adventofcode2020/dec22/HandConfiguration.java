package com.adventofcode2020.dec22;

import java.util.List;
import java.util.Objects;

class HandConfiguration {

    private final List<Integer> hand1;
    private final List<Integer> hand2;

    HandConfiguration( List<Integer> hand1, List<Integer> hand2 ) {
        this.hand1 = hand1;
        this.hand2 = hand2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HandConfiguration)) return false;
        HandConfiguration that = (HandConfiguration) o;
        return hand1.equals(that.hand1) && hand2.equals(that.hand2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hand1, hand2);
    }
}
