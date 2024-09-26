package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRawMaterialRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AllergyRawMaterialRepository extends JpaRepository<AllergyRawMaterial, Long>, AllergyRawMaterialRepositoryCustom {

}
