package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.repository.IngredientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IngredientRepositoryImpl {

    private final IngredientRepository ingredientRepository;

    public IngredientRepositoryImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

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
