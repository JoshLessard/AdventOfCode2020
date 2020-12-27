package com.adventofcode2020.dec16;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TicketValidator {

    private final TicketRules rules;
    private final List<Set<TicketField>> validFieldsPerTicketField;

    TicketValidator( TicketRules rules, int numberOfFieldsPerTicket ) {
        this.rules = rules;
        this.validFieldsPerTicketField = initializeValidFields( numberOfFieldsPerTicket );
    }

    private List<Set<TicketField>> initializeValidFields( int numberOfFieldsPerTicket ) {
        List<Set<TicketField>> validFields = new ArrayList<>();
        for ( int i = 0; i < numberOfFieldsPerTicket; ++i ) {
            validFields.add( new HashSet<>( asList( TicketField.values() ) ) );
        }
        return validFields;
    }

    void addTicket( Ticket ticket ) {
        for ( int i = 0; i < ticket.numberOfFields(); ++i ) {
            int fieldValue = ticket.getFieldValue( i );
            Set<TicketField> validFields = rules.getValidFieldsFor( fieldValue );
            validFieldsPerTicketField.get( i ).retainAll( validFields );
        }
    }

    Map<TicketField, Integer> validate() {
        List<Integer> fieldIndexesToProcess = getFieldIndexesWithSingleValidField();
        while ( ! fieldIndexesToProcess.isEmpty() ) {
            Integer fieldIndexToProcess = fieldIndexesToProcess.remove( 0 );
            TicketField singleValidTicketField = validFieldsPerTicketField.get( fieldIndexToProcess ).iterator().next();
            fieldIndexesToProcess.addAll( removeValidFieldFromIndexesOtherThan( fieldIndexToProcess, singleValidTicketField ) );
        }
        ensureAllFieldIndexesHaveSingleValidField();
        return mapValidFieldsByIndex();
    }

    private List<Integer> getFieldIndexesWithSingleValidField() {
        List<Integer> fieldIndexesWithSingleValidField = new ArrayList<>();
        for ( int i = 0; i < validFieldsPerTicketField.size(); ++i ) {
            Set<TicketField> validFields = validFieldsPerTicketField.get( i );
            if ( validFields.size() == 1 ) {
                fieldIndexesWithSingleValidField.add( i );
            }
        }
        return fieldIndexesWithSingleValidField;
    }

    private Set<Integer> removeValidFieldFromIndexesOtherThan( Integer fieldIndexToIgnore, TicketField singleValidTicketField ) {
        Set<Integer> fieldIndexesToProcess = new HashSet<>();
        for ( int i = 0; i < validFieldsPerTicketField.size(); ++i ) {
            if ( i == fieldIndexToIgnore ) {
                continue;
            }
            Set<TicketField> validFields = validFieldsPerTicketField.get( i );
            if ( validFields.contains( singleValidTicketField ) ) {
                if ( validFields.size() < 2 ) {
                    throw new IllegalStateException( "Removing " + singleValidTicketField + " as valid field for index " + i + " would leave no valid fields." );
                }
                validFields.remove( singleValidTicketField );
                if ( validFields.size() == 1 ) {
                    fieldIndexesToProcess.add( i );
                }
            }
        }
        return fieldIndexesToProcess;
    }

    private void ensureAllFieldIndexesHaveSingleValidField() {
        for ( int i = 0; i < validFieldsPerTicketField.size(); ++i ) {
            if ( validFieldsPerTicketField.get( i ).size() != 1 ) {
                throw new IllegalStateException( "Ticket field with index " + i + " has more than one valid field." );
            }
        }
    }

    private Map<TicketField, Integer> mapValidFieldsByIndex() {
        Map<TicketField, Integer> fieldIndexesByValidField = new HashMap<>();
        for ( int i = 0; i < validFieldsPerTicketField.size(); ++i ) {
            TicketField validField = validFieldsPerTicketField.get( i ).iterator().next();
            fieldIndexesByValidField.put( validField, i );
        }
        return fieldIndexesByValidField;
    }
}
