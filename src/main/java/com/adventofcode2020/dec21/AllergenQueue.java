package com.adventofcode2020.dec21;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

class AllergenQueue {

    private final Set<Allergen> containedAllergens;
    private final Deque<Allergen> queue = new LinkedList<>();

    AllergenQueue( Set<Allergen> allergens ) {
        containedAllergens = new HashSet<>( allergens );
        allergens.forEach( queue::offer );
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }

    Allergen removeFirst() {
        Allergen allergen = queue.removeFirst();
        containedAllergens.remove( allergen );
        return allergen;
    }

    void offer( Allergen allergen ) {
        if ( ! containedAllergens.contains( allergen ) ) {
            containedAllergens.add( allergen );
            queue.offer( allergen );
        }
    }

    public void offerAll( Set<Allergen> allergens ) {
        allergens.forEach( this::offer );
    }
}
