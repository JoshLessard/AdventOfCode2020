package com.adventofcode2020.dec21;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private static final Pattern FOOD_PATTERN = Pattern.compile( "^([^(]+)\\(contains ([^)]+)\\)$" );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec21/input.txt" );
        AllergenInfo allergenInfo = createAllergenInfo( input );

        System.out.println( "Part A: " + allergenInfo.nonAllergenicIngredientAppearances() );
        System.out.println( "Part B: " + asCommaSeparatedString( allergenInfo.canonicalDangerousIngredientList() ) );
    }

    private static AllergenInfo createAllergenInfo( List<String> input ) {
        AllergenInfoBuilder builder = new AllergenInfoBuilder();
        for ( String line : input ) {
            Matcher matcher = FOOD_PATTERN.matcher( line );
            if ( matcher.matches() ) {
                Set<Ingredient> ingredients = Stream.of( matcher.group( 1 ).split( " " ) )
                    .map( String::trim )
                    .map( Ingredient::new )
                    .collect( toSet() );

                Set<Allergen> allergens = Stream.of( matcher.group( 2 ).split( "," ) )
                    .map( String::trim )
                    .map( Allergen::new )
                    .collect( toSet() );

                builder.addPossibleIngredientsByAllergens( allergens, ingredients );
            }
        }
        return builder.build();
    }

    private static String asCommaSeparatedString( Collection<Ingredient> ingredients ) {
        return ingredients
            .stream()
            .map( Ingredient::name )
            .collect( joining( "," ) );
    }
}
