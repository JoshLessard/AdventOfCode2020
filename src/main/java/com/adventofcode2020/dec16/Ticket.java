package com.adventofcode2020.dec16;

import java.util.List;

class Ticket {

    private final List<Integer> fields;

    Ticket( List<Integer> fields ) {
        this.fields = List.copyOf( fields );
    }

    List<Integer> getFieldValues() {
        return List.copyOf( fields );
    }

    int numberOfFields() {
        return fields.size();
    }

    int getFieldValue( int fieldIndex ) {
        return fields.get( fieldIndex );
    }
}
