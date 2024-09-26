package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.repository.RawMaterialRepository;
import com.example.demo.domains.product.repository.querydsl.customs.RawMaterialRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RawMaterialRepositoryImpl implements RawMaterialRepositoryCustom {

    private final EntityManager em;
}
