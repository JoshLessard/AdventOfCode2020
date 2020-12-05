package com.adventofcode2020.dec04;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PassportFactory {

    public Passport build( List<String> inputs ) {
        Map<String, String> fields = new HashMap<>();
        while ( ! inputs.isEmpty() ) {
            String line = inputs.remove( 0 );
            if ( line.equals( "" ) ) {
                break;
            }
            Stream.of( line.split( " " ) )
                .map( s -> s.split( ":" ) )
                .forEach( s -> fields.put( s[0], s[1] ) );
        }
        return new Passport( fields );
    }
}
