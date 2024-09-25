package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.repository.querydsl.customs.EfficacyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EfficacyRepository  extends JpaRepository<Efficacy, Long>, EfficacyRepositoryCustom {
}
