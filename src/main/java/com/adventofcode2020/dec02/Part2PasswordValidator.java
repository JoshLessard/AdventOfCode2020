package com.adventofcode2020.dec02;

class Part2PasswordValidator implements PasswordValidator {

    @Override
    public boolean isValid( int position1, int position2, char letter, String password ) {
        int numMatches = 0;
        if ( password.charAt( position1 - 1 ) == letter ) {
            ++numMatches;
        }
        if ( password.charAt( position2 - 1 ) == letter ) {
            ++numMatches;
        }

        return numMatches == 1;
    }
}
