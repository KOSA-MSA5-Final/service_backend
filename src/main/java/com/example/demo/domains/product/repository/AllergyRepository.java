package com.example.demo.domains.product.repository;

import com.example.demo.domains.product.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository  extends JpaRepository<Allergy, Long> {
}
