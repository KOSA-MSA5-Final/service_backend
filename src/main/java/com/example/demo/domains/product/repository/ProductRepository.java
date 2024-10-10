package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.querydsl.customs.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * author : 윤다희
 * date : 2024-09-24
 * description : 상품 레포지토리
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         윤다희          최초 생성
 */

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    // 모든 상품 조회
    List<Product> findAll();

    // 특정 타입의 상품 조회
    List<Product> findByType(String type);
}
