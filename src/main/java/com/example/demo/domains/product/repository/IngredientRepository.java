package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Ingredient;
import com.example.demo.domains.product.repository.querydsl.customs.IngredientRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author : 윤다희
 * date : 2024-09-24
 * description : 영양성분 레포지토리
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         윤다희          최초 생성
 */

public interface IngredientRepository  extends JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {

}
