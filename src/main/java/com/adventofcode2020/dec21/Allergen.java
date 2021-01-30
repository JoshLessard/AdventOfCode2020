package com.adventofcode2020.dec21;

import java.util.Objects;

class Allergen {

    private final String name;

    Allergen( String name ) {
        this.name = name;
    }

    String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Allergen)) return false;
        Allergen allergen = (Allergen) o;
        return name.equals(allergen.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
