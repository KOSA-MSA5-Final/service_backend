package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.repository.IngredientRepository;
import com.example.demo.domains.product.repository.querydsl.customs.IngredientRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IngredientRepositoryImpl implements IngredientRepositoryCustom {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
