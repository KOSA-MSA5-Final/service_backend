package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getAllIngredients();
    Ingredient saveIngredient(Ingredient ingredient);
    Ingredient createIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Ingredient ingredient);
    void deleteIngredient(long id);
}
