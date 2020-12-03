package com.adventofcode2020.dec02;

class Part2PasswordValidator implements PasswordValidator {

    @Override
    public boolean isValid( int position1, int position2, char letter, String password ) {
        return password.charAt( position1 - 1 ) == letter ^ password.charAt( position2 - 1 ) == letter;
    }
}
