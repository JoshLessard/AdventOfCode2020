package com.adventofcode2020.dec06;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class CustomsGroupFactory {

    CustomsGroup build( List<String> rawQuestionsAnsweredPerPerson ) {
        List<Set<Integer>> questionsAnsweredPerPerson = new ArrayList<>();
        String answers;
        while ( ! rawQuestionsAnsweredPerPerson.isEmpty() && ! ( answers = rawQuestionsAnsweredPerPerson.remove( 0 ) ).equals( "" ) ) {
            questionsAnsweredPerPerson.add( toQuestionSet( answers ) );
        }
        return new CustomsGroup( questionsAnsweredPerPerson );
    }

    private Set<Integer> toQuestionSet( String questionsAnswered ) {
        Set<Integer> charSet = new HashSet<>();
        questionsAnswered.chars().forEach( charSet::add );
        return charSet;
    }
}
