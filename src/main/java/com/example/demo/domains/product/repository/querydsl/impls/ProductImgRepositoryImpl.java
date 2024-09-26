package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.ProductImgRepository;
import com.example.demo.domains.product.repository.querydsl.customs.ProductImgRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductImgRepositoryImpl implements ProductImgRepositoryCustom {

    private final EntityManager em;
}
