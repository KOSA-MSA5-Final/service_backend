package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.RawMaterial;

import java.util.List;

public interface RawMaterialService {
    List<RawMaterial> getAllRawMaterials();
    RawMaterial saveRawMaterial(String name, String type);
    void deleteRawMaterial(Long id);
}
