package com.example.lab4.Repository;


import Domain.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static final List<String> ingredienteTypes = List.of("ulei_de_masline", "sare", "piper", "lapte", "oua", "porumb");
    private static final Random random = new Random();

    public static String generateRandomIngredientType() {
        return ingredienteTypes.get(random.nextInt(ingredienteTypes.size()));
    }

    public static List<Ingredient> generateRandomIngrediente(List<Ingredient> allIngredients, int numberOfIngrediente) {
        List<Ingredient> selectedIngredients = new ArrayList<>();
        int totalIngrediente = allIngredients.size();

        if (totalIngrediente == 0) {
            throw new IllegalArgumentException("List of ingredients is empty.");
        }

        for (int i = 0; i < numberOfIngrediente; i++) {
            Ingredient randomIngredient = allIngredients.get(random.nextInt(totalIngrediente));
            selectedIngredients.add(randomIngredient);
        }

        return selectedIngredients;
    }


}


