package com.example.demo.domains.disease.repository;

import com.example.demo.domains.disease.entity.DiseaseSubProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseSubProfileRepository extends JpaRepository<DiseaseSubProfile, Long> {
    DiseaseSubProfile save(DiseaseSubProfile diseaseSubProfile);
}
