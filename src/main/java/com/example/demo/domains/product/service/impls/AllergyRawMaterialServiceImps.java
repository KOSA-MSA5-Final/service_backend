package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.AllergyRawMaterial;
import com.example.demo.domains.product.repository.querydsl.impls.AllergyRawMaterialRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.AllergyRawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyRawMaterialServiceImps implements AllergyRawMaterialService {
    private final AllergyRawMaterialRepositoryImpl allergyRawMaterialRepositoryImpl;

    @Override
    public List<AllergyRawMaterial> getAllAllergyRawMaterials() {
        return allergyRawMaterialRepositoryImpl.findAllAllergyRawMaterials();
    }

    @Override
    public AllergyRawMaterial saveAllergyRawMaterial(AllergyRawMaterial allergyRawMaterial) {
        return allergyRawMaterialRepositoryImpl.saveAllergyRawMaterial(allergyRawMaterial);
    }

    @Override
    public AllergyRawMaterial updateAllergyRawMaterial(AllergyRawMaterial allergyRawMaterial) {
        return null;
    }

    @Override
    public void deleteAllergyRawMaterial(long id) {
        allergyRawMaterialRepositoryImpl.deleteAllergyRawMaterial(id);
    }
}
