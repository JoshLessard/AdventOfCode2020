package com.adventofcode2020.dec06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.adventofcode2020.common.InputUtilities.getInput;

public class Main {

    private static final CustomsGroupFactory GROUP_FACTORY = new CustomsGroupFactory();

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec06/input.txt" );
        List<CustomsGroup> customsGroups = new ArrayList<>();
        while ( ! input.isEmpty() ) {
            customsGroups.add( GROUP_FACTORY.build( input ) );
        }

        int numberOfAnsweredQuestions = customsGroups.stream()
            .map( CustomsGroup::questionsAnsweredByAnyone )
            .mapToInt( Set::size )
            .sum();

        int numberOfQuestionsAnsweredByEveryone = customsGroups.stream()
            .map( CustomsGroup::questionsAnsweredByEveryone )
            .mapToInt( Set::size )
            .sum();

        System.out.println( "Number of questions answered by anyone: " + numberOfAnsweredQuestions );
        System.out.println( "Number of questions answered by everyone: " + numberOfQuestionsAnsweredByEveryone );
    }
}
