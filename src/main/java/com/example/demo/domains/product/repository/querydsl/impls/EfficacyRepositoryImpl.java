package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.repository.EfficacyRepository;
import com.example.demo.domains.product.repository.querydsl.customs.EfficacyRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EfficacyRepositoryImpl implements EfficacyRepositoryCustom {
    private final EntityManager em;
}
