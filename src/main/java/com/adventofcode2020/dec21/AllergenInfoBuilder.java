package com.adventofcode2020.dec21;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class AllergenInfoBuilder {

    private final SortedMap<Allergen, Ingredient> ingredientByAllergen = new TreeMap<>( comparing( Allergen::name ) );
    private final Map<Set<Allergen>, List<Set<Ingredient>>> possibleIngredientsByAllergens = new HashMap<>();

    void addPossibleIngredientsByAllergens( Set<Allergen> allergens, Set<Ingredient> possibleIngredients ) {
        addPossibleIngredientsByAllergens( allergens, List.of( possibleIngredients ) );
    }

    void addPossibleIngredientsByAllergens( Set<Allergen> allergens, Collection<Set<Ingredient>> possibleIngredientLists ) {
        possibleIngredientsByAllergens.computeIfAbsent( allergens, k -> new ArrayList<>() ).addAll( possibleIngredientLists );
    }

    AllergenInfo build() {
        AllergenQueue singleAllergensToConsider = new AllergenQueue( singleAllergens() );
        while ( ! singleAllergensToConsider.isEmpty() ) {
            Allergen allergen = singleAllergensToConsider.removeFirst();
            Set<Ingredient> ingredientsInAllAllergenLists = ingredientsInAllAllergenListsContaining( allergen );
            if ( ingredientsInAllAllergenLists.size() == 1 ) {
                Ingredient ingredientContainingAllergen = ingredientsInAllAllergenLists.stream().findAny().get();
                ingredientByAllergen.put( allergen, ingredientContainingAllergen );
                removeFromPossibleIngredients( ingredientContainingAllergen );
                removeFromPossibleAllergenLists( allergen );
                singleAllergensToConsider.offerAll( singleAllergens() );
            } else {
                singleAllergensToConsider.offer( allergen );
            }
        }

        return new AllergenInfo( ingredientByAllergen, remainingIngredientCounts() );
    }

    private Set<Allergen> singleAllergens() {
        return possibleIngredientsByAllergens
            .keySet()
            .stream()
            .filter( a -> a.size() == 1 )
            .map( a -> a.stream().findAny().get() )
            .collect( toSet() );
    }

    private Set<Ingredient> ingredientsInAllAllergenListsContaining( Allergen allergen ) {
        List<Set<Ingredient>> ingredientListsContaining = ingredientListsContaining( allergen );
        Set<Ingredient> ingredientsInAllAllergenLists = new HashSet<>( ingredientListsContaining.get( 0 ) );
        ingredientListsContaining.forEach( ingredientsInAllAllergenLists::retainAll );
        return ingredientsInAllAllergenLists;
    }

    private void removeFromPossibleIngredients( Ingredient ingredient ) {
        possibleIngredientsByAllergens.values().stream()
            .flatMap( List::stream )
            .forEach( s -> s.remove( ingredient ) );
    }

    private void removeFromPossibleAllergenLists( Allergen allergen ) {
        List<Set<Allergen>> allergenListsContainingAllergen = allergenListsContaining( allergen );
        for ( Set<Allergen> allergenList : allergenListsContainingAllergen ) {
            List<Set<Ingredient>> ingredientLists = possibleIngredientsByAllergens.remove( allergenList );
            allergenList.remove( allergen );
            addPossibleIngredientsByAllergens( allergenList, ingredientLists );
        }
    }

    private Map<Ingredient, Long> remainingIngredientCounts() {
        return possibleIngredientsByAllergens.values().stream()
            .flatMap( List::stream )
            .flatMap( Set::stream )
            .collect( groupingBy( identity(), counting() ) );
    }

    private List<Set<Ingredient>> ingredientListsContaining( Allergen allergen ) {
        return possibleIngredientsByAllergens.entrySet().stream()
            .filter( e -> e.getKey().contains( allergen ) )
            .map( Map.Entry::getValue )
            .flatMap( List::stream )
            .collect( toList() );
    }

    private List<Set<Allergen>> allergenListsContaining( Allergen allergen ) {
        return possibleIngredientsByAllergens.keySet().stream()
            .filter( al -> al.contains(allergen) )
            .collect( toList() );
    }
}
