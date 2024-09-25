package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
}
