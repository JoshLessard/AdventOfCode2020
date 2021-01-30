package com.adventofcode2020.dec25;

import static com.adventofcode2020.common.InputUtilities.getInput;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final long SUBJECT_NUMBER = 7L;

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec25/input.txt" );
        long cardPublicKey = Long.parseLong( input.get( 0 ) );
        long doorPublicKey = Long.parseLong( input.get( 1 ) );

        long cardLoopSize = getLoopSize( SUBJECT_NUMBER, cardPublicKey );
        long doorLoopSize = getLoopSize( SUBJECT_NUMBER, doorPublicKey );

        long encryptionKey1 = transform( 1L, cardPublicKey, doorLoopSize );
        long encryptionKey2 = transform( 1L, doorPublicKey, cardLoopSize );

        if ( encryptionKey1 != encryptionKey2 ) {
            throw new IllegalStateException( "Encryption keys do not match." );
        }

        System.out.println( "Encryption key: " + encryptionKey1 );
    }

    private static long getLoopSize( long subjectNumber, long publicKey ) {
        long value = 1L;
        long loopSize = 0L;
        while ( value != publicKey ) {
            ++loopSize;
            value = transform( value, subjectNumber );
        }
        return loopSize;
    }

    private static long transform( long value, long subjectNumber, long loopSize ) {
        for ( long i = 0L; i < loopSize; ++i ) {
            value = transform( value, subjectNumber );
        }
        return value;
    }

    private static long transform( long value, long subjectNumber ) {
        value *= subjectNumber;
        return value % 20201227;
    }
}
