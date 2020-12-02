package com.adventofcode2020.dec02;

class Part1PasswordValidator implements PasswordValidator {

    @Override
    public boolean isValid( int min, int max, char letter, String password ) {
        int numMatches = (int) password.chars()
            .filter( c -> c == letter )
            .count();

        return min <= numMatches && numMatches <= max;
    }
}
