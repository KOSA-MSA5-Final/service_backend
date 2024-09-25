package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.repository.querydsl.impls.IngredientRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImps implements IngredientService {

    private final IngredientRepositoryImpl ingredientRepositoryImpl;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepositoryImpl.findAllIngredients();
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepositoryImpl.saveIngredient(ingredient);
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    public Ingredient updateIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    public void deleteIngredient(long id) {
        ingredientRepositoryImpl.deleteIngredient(id);
    }
}
