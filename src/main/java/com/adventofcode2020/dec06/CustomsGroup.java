package com.adventofcode2020.dec06;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class CustomsGroup {

    private final List<Set<Integer>> questionsAnsweredPerPerson;

    CustomsGroup( List<Set<Integer>> questionsAnsweredPerPerson ) {
        this.questionsAnsweredPerPerson = List.copyOf( questionsAnsweredPerPerson );
    }

    public Set<Integer> questionsAnsweredByAnyone() {
        return questionsAnsweredPerPerson.stream()
            .flatMap( Set::stream )
            .collect( toSet() );
    }

    public Set<Integer> questionsAnsweredByEveryone() {
        return questionsAnsweredPerPerson.stream()
            .reduce( questionsAnsweredPerPerson.get( 0 ), (a, b) -> { a.retainAll( b ); return a; } );
    }
}
