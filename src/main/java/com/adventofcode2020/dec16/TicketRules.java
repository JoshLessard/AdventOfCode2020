package com.adventofcode2020.dec16;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

class TicketRules {

    private final Map<TicketField, List<FieldRange>> rangesByField;
    private final List<FieldRange> fieldRanges;

    TicketRules( Map<TicketField, List<FieldRange>> rangesByField ) {
        this.rangesByField = Map.copyOf( rangesByField );
        this.fieldRanges = rangesByField.values().stream()
            .flatMap( List::stream )
            .collect( toList() );
    }

    List<Integer> getInvalidFieldValues( Ticket ticket ) {
        return ticket.getFieldValues().stream()
            .filter( not( this::isWithinAnyRanges ) )
            .collect( toList() );
    }

    private boolean isWithinAnyRanges( int field ) {
        return fieldRanges.stream()
            .anyMatch( r -> r.contains( field ) );
    }

    boolean isValid( Ticket ticket ) {
        return getInvalidFieldValues( ticket ).isEmpty();
    }

    Set<TicketField> getValidFieldsFor( int fieldValue ) {
        return rangesByField.keySet().stream()
            .filter( f -> isWithinAnyRanges( fieldValue, rangesByField.get( f ) ) )
            .collect( toSet() );
    }

    private boolean isWithinAnyRanges( int fieldValue, List<FieldRange> fieldRanges ) {
        return fieldRanges.stream()
            .anyMatch( r -> r.contains( fieldValue ) );
    }
}
