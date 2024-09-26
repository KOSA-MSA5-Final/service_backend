package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AllergyRepository  extends JpaRepository<Allergy, Long> , AllergyRepositoryCustom {

}
