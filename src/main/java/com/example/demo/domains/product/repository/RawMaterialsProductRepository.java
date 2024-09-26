package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.RawMaterialsProduct;
import com.example.demo.domains.product.repository.querydsl.customs.RawMaterialsProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface RawMaterialsProductRepository extends JpaRepository<RawMaterialsProduct, Long>, RawMaterialsProductRepositoryCustom {

}
