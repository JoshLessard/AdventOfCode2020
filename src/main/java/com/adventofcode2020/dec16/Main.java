package com.adventofcode2020.dec16;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private static final Pattern RULES_PATTERN = Pattern.compile( "([a-z ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)" );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec16/input.txt" );

        TicketRules rules = parseRules( input );
        Ticket myTicket = parseMyTicket( input );
        List<Ticket> nearbyTickets = parseNearbyTickets( input );
        System.out.println( "Part A: " + getErrorRate( nearbyTickets, rules ) );

        TicketValidator validator = new TicketValidator( rules, myTicket.numberOfFields() );
        validator.addTicket( myTicket );
        filterValidTickets( nearbyTickets, rules )
            .forEach( validator::addTicket );

        System.out.println( "Part B: " + getPartBValue( validator, myTicket ) );
    }

    private static TicketRules parseRules( List<String> input ) {
        Map<TicketField, List<FieldRange>> rules = input.stream()
            .map( RULES_PATTERN::matcher )
            .filter( Matcher::matches )
            .collect( toMap( m -> TicketField.parse( m.group( 1 ) ), m -> toRanges( Integer.parseInt( m.group( 2 ) ), Integer.parseInt( m.group( 3 ) ), Integer.parseInt( m.group( 4 ) ), Integer.parseInt( m.group( 5 ) ) ) ) );

        return new TicketRules( rules );
    }

    private static List<FieldRange> toRanges( int min1, int max1, int min2, int max2 ) {
        return List.of(
            new FieldRange( min1, max1 ),
            new FieldRange( min2, max2 )
        );
    }

    private static Ticket parseMyTicket( List<String> input ) {
        int indexOfMyTicketHeader = findIndexOfHeader( "your ticket:", input );
        return new Ticket( parseTicketFields( input.get( indexOfMyTicketHeader + 1 ) ) );
    }

    private static int findIndexOfHeader( String header, List<String> input ) {
        for ( int i = 0; i < input.size(); ++i ) {
            if ( input.get( i ).equals( header ) ) {
                return i;
            }
        }

        throw new IllegalArgumentException( "Unable to find header for my ticket." );
    }

    private static List<Integer> parseTicketFields( String input ) {
        return Stream.of( input.split( "," ) )
            .map( Integer::valueOf )
            .collect( toList() );
    }

    private static List<Ticket> parseNearbyTickets( List<String> input ) {
        int indexOfNearbyTicketsHeader = findIndexOfHeader( "nearby tickets:", input );
        List<Ticket> nearbyTickets = new ArrayList<>();
        for ( int i = indexOfNearbyTicketsHeader + 1; i < input.size(); ++i ) {
            nearbyTickets.add( new Ticket( parseTicketFields( input.get( i ) ) ) );
        }
        return nearbyTickets;
    }

    private static int getErrorRate( List<Ticket> tickets, TicketRules rules ) {
        return tickets.stream()
            .map( rules::getInvalidFieldValues )
            .flatMap( List::stream )
            .mapToInt( Integer::intValue )
            .sum();
    }

    private static List<Ticket> filterValidTickets( List<Ticket> tickets, TicketRules rules ) {
        return tickets.stream()
            .filter( rules::isValid )
            .collect( toList() );
    }

    private static long getPartBValue( TicketValidator validator, Ticket ticket ) {
        Map<TicketField, Integer> indexesByField = validator.validate();
        return indexesByField.keySet().stream()
            .filter( f -> f.getName().startsWith( "departure" ) )
            .map( indexesByField::get )
            .mapToLong( ticket::getFieldValue )
            .reduce( 1L, (a, b) -> a * b );
    }
}
