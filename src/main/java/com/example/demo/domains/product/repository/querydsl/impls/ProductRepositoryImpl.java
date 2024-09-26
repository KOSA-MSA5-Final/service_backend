package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.ProductRepository;
import com.example.demo.domains.product.repository.querydsl.customs.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final EntityManager em;
}
