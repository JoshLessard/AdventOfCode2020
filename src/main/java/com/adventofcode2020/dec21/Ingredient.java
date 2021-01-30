package com.adventofcode2020.dec21;

import java.util.Objects;

class Ingredient {

    private final String name;

    Ingredient( String name ) {
        this.name = name;
    }

    String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
