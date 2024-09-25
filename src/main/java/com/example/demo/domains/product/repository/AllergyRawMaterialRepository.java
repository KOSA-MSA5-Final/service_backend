package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRawMaterialRepository extends JpaRepository<AllergyRawMaterial, Long> {
}
