package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.repository.IngredientRepository;
import com.example.demo.domains.product.repository.querydsl.customs.IngredientRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class IngredientRepositoryImpl implements IngredientRepositoryCustom {

    private final EntityManager em;
}
