package com.adventofcode2020.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InputUtilities {

    public static List<String> getInput(String fileName ) throws IOException {
        try ( BufferedReader reader = new BufferedReader( new FileReader( fileName ) ) ) {
            return reader.lines().collect( toList() );
        }
    }
}
