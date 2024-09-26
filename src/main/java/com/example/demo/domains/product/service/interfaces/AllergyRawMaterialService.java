package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.entity.RawMaterial;

import java.util.List;

public interface AllergyRawMaterialService {
    List<AllergyRawMaterial> getAllAllergyRawMaterials();
    AllergyRawMaterial saveAllergyRawMaterial(Allergy allergy, RawMaterial rawMaterial);
    void deleteAllergyRawMaterial(long id);
}
