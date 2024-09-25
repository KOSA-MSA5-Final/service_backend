package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.repository.AllergyRawMaterialRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllergyRawMaterialRepositoryImpl {

    private final AllergyRawMaterialRepository allergyRawMaterialRepository;

    public AllergyRawMaterialRepositoryImpl(AllergyRawMaterialRepository allergyRawMaterialRepository) {
        this.allergyRawMaterialRepository = allergyRawMaterialRepository;
    }

    public List<AllergyRawMaterial> findAllAllergyRawMaterials() {
        return allergyRawMaterialRepository.findAll();
    }

    public AllergyRawMaterial saveAllergyRawMaterial(AllergyRawMaterial allergyRawMaterial) {
        return allergyRawMaterialRepository.save(allergyRawMaterial);
    }

    public void deleteAllergyRawMaterial(Long id) {
        allergyRawMaterialRepository.deleteById(id);
    }
}
