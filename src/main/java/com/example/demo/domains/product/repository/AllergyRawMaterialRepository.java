package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRawMaterialRepositoryCustom;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AllergyRawMaterialRepository extends JpaRepository<AllergyRawMaterial, Long>, AllergyRawMaterialRepositoryCustom {
}
