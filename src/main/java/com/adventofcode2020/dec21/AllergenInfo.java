package com.adventofcode2020.dec21;

import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

class AllergenInfo {

    private final SortedMap<Allergen, Ingredient> ingredientByAllergen;
    private final Map<Ingredient, Long> nonAllergenicIngredientCounts;

    AllergenInfo( SortedMap<Allergen, Ingredient> ingredientByAllergen, Map<Ingredient, Long> nonAllergenicIngredientCounts ) {
        this.ingredientByAllergen = new TreeMap<>( ingredientByAllergen );
        this.nonAllergenicIngredientCounts = Map.copyOf( nonAllergenicIngredientCounts );
    }

    long nonAllergenicIngredientAppearances() {
        return nonAllergenicIngredientCounts
            .values()
            .stream()
            .mapToLong( Long::longValue )
            .sum();
    }

    Collection<Ingredient> canonicalDangerousIngredientList() {
        return ingredientByAllergen.values();
    }
}
