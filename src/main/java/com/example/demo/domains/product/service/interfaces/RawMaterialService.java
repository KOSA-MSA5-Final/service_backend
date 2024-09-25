package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.RawMaterial;

import java.util.List;

public interface RawMaterialService {
    List<RawMaterial> getAllRawMaterials();
    RawMaterial getRawMaterialById(Long id);
    RawMaterial createRawMaterial(RawMaterial rawMaterial);
    RawMaterial updateRawMaterial(Long id, RawMaterial rawMaterial);
    void deleteRawMaterial(Long id);
}
