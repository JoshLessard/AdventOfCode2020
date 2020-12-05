package com.adventofcode2020.dec04;

import java.util.List;

import static com.adventofcode2020.common.InputUtilities.getInput;

public class Main {

    public static void main( String[] args ) throws Exception {
        List<String> input = getInput( "src/main/resources/dec04/input.txt" );
        PassportFactory factory = new PassportFactory();
        int passportsWithRequiredFields = 0;
        int passportsWithValidFields = 0;
        while ( ! input.isEmpty() ) {
            Passport passport = factory.build( input );
            if ( passport.hasRequiredFields() ) {
                ++passportsWithRequiredFields;
                if ( passport.allFieldsValid() ) {
                    ++passportsWithValidFields;
                }
            }
        }

        System.out.println( "Number of valid passports: " + passportsWithRequiredFields );
        System.out.println( "Number of passports with valid fields: " + passportsWithValidFields );
    }
}
