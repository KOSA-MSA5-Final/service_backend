package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.querydsl.customs.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
