package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.repository.querydsl.customs.RawMaterialRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> , RawMaterialRepositoryCustom {

}
