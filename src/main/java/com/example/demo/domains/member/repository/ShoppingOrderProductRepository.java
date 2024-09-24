package com.example.demo.domains.member.repository;

import com.example.demo.domains.member.entity.Product;
import com.example.demo.domains.member.entity.ShoppingOrder;
import com.example.demo.domains.member.entity.ShoppingOrderProduct;
import com.example.demo.domains.member.repository.querydsl.customs.ShoppingOrderProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingOrderProductRepository extends JpaRepository<ShoppingOrderProduct, Long>, ShoppingOrderProductRepositoryCustom {

}
