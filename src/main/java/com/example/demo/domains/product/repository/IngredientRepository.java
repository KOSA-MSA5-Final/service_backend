package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.repository.querydsl.customs.IngredientRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IngredientRepository  extends JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {

}
