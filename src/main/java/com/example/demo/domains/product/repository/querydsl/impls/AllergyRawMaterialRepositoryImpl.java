package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.repository.AllergyRawMaterialRepository;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRawMaterialRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AllergyRawMaterialRepositoryImpl implements AllergyRawMaterialRepositoryCustom {

    private final AllergyRawMaterialRepository allergyRawMaterialRepository;

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
