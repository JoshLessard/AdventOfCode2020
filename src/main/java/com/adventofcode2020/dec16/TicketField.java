package com.adventofcode2020.dec16;

enum TicketField {

    DEPARTURE_LOCATION( "departure location" ),
    DEPARTURE_STATION( "departure station" ),
    DEPARTURE_PLATFORM( "departure platform" ),
    DEPARTURE_TRACK( "departure track" ),
    DEPARTURE_DATE( "departure date" ),
    DEPARTURE_TIME( "departure time" ),
    ARRIVAL_LOCATION( "arrival location" ),
    ARRIVAL_STATION( "arrival station" ),
    ARRIVAL_PLATFORM( "arrival platform" ),
    ARRIVAL_TRACK( "arrival track" ),
    CLASS( "class" ),
    DURATION( "duration" ),
    PRICE( "price" ),
    ROUTE( "route" ),
    ROW( "row" ),
    SEAT( "seat" ),
    TRAIN( "train" ),
    TYPE( "type" ),
    WAGON( "wagon" ),
    ZONE( "zone" );

    private final String name;

    TicketField( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static TicketField parse(String name ) {
        for ( TicketField ticketField : TicketField.values() ) {
            if ( ticketField.name.equals( name ) ) {
                return ticketField;
            }
        }

        throw new IllegalArgumentException( "Unrecognized ticket field name: " + name );
    }
}
