package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.repository.querydsl.customs.ProductDetailImgRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductDetailImgRepository extends JpaRepository<ProductDetailImg, Long>, ProductDetailImgRepositoryCustom {

}
