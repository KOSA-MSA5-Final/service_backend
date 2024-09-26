package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.entity.Product;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getAllIngredients();
    Ingredient saveIngredient(String name, String percentage, Product product);
    void deleteIngredient(long id);
}
