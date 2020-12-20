package com.adventofcode2020.dec12;

interface FerrySystemCommandFactory {

    FerrySystemCommand build( String commandName, int commandArgument );
}
