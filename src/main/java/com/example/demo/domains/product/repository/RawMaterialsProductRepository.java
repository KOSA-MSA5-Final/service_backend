package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.RawMaterialsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialsProductRepository extends JpaRepository<RawMaterialsProduct, Long> {
}
