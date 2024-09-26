package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.querydsl.customs.ProductImgRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductImgRepository extends JpaRepository<ProductImg, Long>, ProductImgRepositoryCustom {

}
