package com.adventofcode2020.dec02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Main {

    private static final Pattern PATTERN = Pattern.compile( "(\\d+)-(\\d+) ([a-z]): ([a-z]+)" );
    private static final PasswordValidator PART_1_VALIDATOR = new Part1PasswordValidator();
    private static final PasswordValidator PART_2_VALIDATOR = new Part2PasswordValidator();

    public static void main( String[] args ) throws Exception {
        List<String> input = getInput();
        System.out.println( "Part 1: " + numberOfValidPasswords( input, PART_1_VALIDATOR ) );
        System.out.println( "Part 2: " + numberOfValidPasswords( input, PART_2_VALIDATOR ) );
    }

    private static List<String> getInput() throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( "src/main/resources/dec02/input.txt" ) ) ) {
            return reader.lines().collect( toList() );
        }
    }

    private static int numberOfValidPasswords( List<String> input, PasswordValidator validator ) {
        return (int) input.stream()
            .map( PATTERN::matcher )
            .filter( Matcher::matches )
            .filter( m -> validator.isValid(
                Integer.parseInt( m.group( 1 ) ),
                Integer.parseInt( m.group( 2 ) ),
                m.group( 3 ).charAt( 0 ),
                m.group( 4 ) )
            )
            .count();
    }
}