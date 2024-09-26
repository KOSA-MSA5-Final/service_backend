package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.repository.ProductDetailImgRepository;
import com.example.demo.domains.product.repository.querydsl.customs.ProductDetailImgRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductDetailImgRepositoryImpl implements ProductDetailImgRepositoryCustom {

    private final EntityManager em;
}
