package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.IngredientRepository;
import com.example.demo.domains.product.service.interfaces.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImps implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient saveIngredient(String name, String percentage, Product product) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setPercentage(percentage);
        ingredient.setProduct(product);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteIngredient(long id) {
        ingredientRepository.deleteById(id);
    }
}
