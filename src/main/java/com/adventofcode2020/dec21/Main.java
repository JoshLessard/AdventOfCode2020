package com.adventofcode2020.dec21;

import static com.adventofcode2020.common.InputUtilities.getInput;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private static final Pattern FOOD_PATTERN = Pattern.compile( "^([^(]+)\\(contains ([^)]+)\\)$" );

    public static void main( String[] args ) throws IOException {
        List<String> input = getInput( "src/main/resources/dec21/input.txt" );
        Map<Set<String>, List<Set<String>>> possibleIngredientsByAllergens = mapPossibleIngredientListsByAllergens( input );
        SortedMap<String, String> ingredientByAllergen = new TreeMap<>();
        Set<String> singleAllergensToConsider = possibleIngredientsByAllergens.keySet().stream().filter( a -> a.size() == 1 ).map( a -> a.stream().findAny().get() ).collect( toCollection( LinkedHashSet::new ) );
        while ( ! singleAllergensToConsider.isEmpty() ) {
            String allergen = singleAllergensToConsider.stream().findAny().get();
            singleAllergensToConsider.remove( allergen );
            List<Set<String>> ingredientListsContaining = possibleIngredientsByAllergens.entrySet().stream()
                .filter( e -> e.getKey().contains( allergen ) )
                .map( Map.Entry::getValue )
                .flatMap( List::stream )
                .collect( toList() );
            Set<String> ingredientsInAllAllergenLists = new HashSet<>( ingredientListsContaining.get( 0 ) );
            ingredientListsContaining.forEach( ingredientsInAllAllergenLists::retainAll );
            if ( ingredientsInAllAllergenLists.size() == 1 ) {
                String ingredientWithAllergen = ingredientsInAllAllergenLists.stream().findAny().get();

                // We've found the ingredient containing the allergen
                ingredientByAllergen.put( allergen, ingredientWithAllergen );

                // Remove the ingredient from possible ingredients containing other allergens
                possibleIngredientsByAllergens.values().stream()
                    .flatMap( List::stream )
                    .forEach( s -> s.remove( ingredientWithAllergen ) );

                // Remove the allergen from possible allergen lists and merge ingredient lists that now share the same possible allergens
                List<Set<String>> allergenListsContainingAllergen = possibleIngredientsByAllergens.keySet().stream()
                    .filter( al -> al.contains( allergen ) )
                    .collect( toList() );
                for ( Set<String> allergenList : allergenListsContainingAllergen ) {
                    List<Set<String>> ingredientLists = possibleIngredientsByAllergens.remove( allergenList );
                    allergenList.remove( allergen );
                    possibleIngredientsByAllergens.computeIfAbsent( allergenList, k -> new ArrayList<>() ).addAll( ingredientLists );
                    if ( allergenList.size() == 1 ) {
                        singleAllergensToConsider.add( allergenList.stream().findAny().get() );
                    }
                }
            } else {
                singleAllergensToConsider.add( allergen );
            }
        }

        Map<String, Long> countsByRemainingIngredients = possibleIngredientsByAllergens.values().stream()
            .flatMap( List::stream )
            .flatMap( Set::stream )
            .collect( groupingBy( identity(), counting() ) );

        long totalCount = countsByRemainingIngredients.values().stream().mapToLong( Long::longValue ).sum();
        System.out.println( "Part A: " + totalCount );

        String canonicalDangerousIngredientList = String.join( ",", ingredientByAllergen.values() );
        System.out.println( "Part B: " + canonicalDangerousIngredientList );
    }

    private static Map<Set<String>, List<Set<String>>> mapPossibleIngredientListsByAllergens( List<String> input ) {
        Map<Set<String>, List<Set<String>>> possibleIngredientsByAllergens = new HashMap<>();
        for ( String line : input) {
            Matcher matcher = FOOD_PATTERN.matcher( line );
            if ( matcher.matches() ) {
                Set<String> ingredients = Stream.of( matcher.group( 1 ).split( " " ) )
                    .map( String::trim )
                    .collect( toSet() );

                Set<String> allergens = Stream.of( matcher.group( 2 ).split( "," ) )
                    .map( String::trim )
                    .collect( toSet() );

                possibleIngredientsByAllergens.computeIfAbsent( allergens, k -> new ArrayList<>() ).add( ingredients );
            }
        }
        return possibleIngredientsByAllergens;
    }
}
