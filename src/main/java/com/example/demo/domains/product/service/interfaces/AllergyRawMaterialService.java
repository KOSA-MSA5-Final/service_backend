package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.AllergyRawMaterial;

import java.util.List;

public interface AllergyRawMaterialService {
    List<AllergyRawMaterial> getAllAllergyRawMaterials();
    AllergyRawMaterial saveAllergyRawMaterial(AllergyRawMaterial allergyRawMaterial);
    AllergyRawMaterial updateAllergyRawMaterial(AllergyRawMaterial allergyRawMaterial);
    void deleteAllergyRawMaterial(long id);
}
