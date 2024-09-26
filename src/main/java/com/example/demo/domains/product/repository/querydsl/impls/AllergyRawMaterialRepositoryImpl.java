package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.repository.AllergyRawMaterialRepository;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRawMaterialRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AllergyRawMaterialRepositoryImpl implements AllergyRawMaterialRepositoryCustom {

    private final EntityManager em;
}
