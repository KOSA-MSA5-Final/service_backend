package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.RawMaterialsProduct;
import com.example.demo.domains.product.repository.RawMaterialsProductRepository;
import com.example.demo.domains.product.repository.querydsl.customs.RawMaterialsProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RawMaterialsProductRepositoryImpl implements RawMaterialsProductRepositoryCustom {

    private final EntityManager em;
}
