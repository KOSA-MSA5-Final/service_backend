package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.repository.AllergyRepository;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepositoryCustom {
    private final EntityManager em;
}
