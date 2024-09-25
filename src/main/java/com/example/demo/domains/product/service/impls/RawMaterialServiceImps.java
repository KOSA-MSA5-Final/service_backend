package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.repository.querydsl.impls.RawMaterialRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImps implements RawMaterialService {

    private final RawMaterialRepositoryImpl rawMaterialRepositoryImpl;

    @Override
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepositoryImpl.findAllRawMaterials();
    }

    @Override
    public RawMaterial getRawMaterialById(Long id) {
        Optional<RawMaterial> rawMaterial = rawMaterialRepositoryImpl.findRawMaterialById(id);
        return rawMaterial.orElse(null);  // 찾지 못할 경우 null 반환
    }

    @Override
    public RawMaterial createRawMaterial(RawMaterial rawMaterial) {
        return rawMaterialRepositoryImpl.saveRawMaterial(rawMaterial);
    }

    @Override
    public RawMaterial updateRawMaterial(Long id, RawMaterial rawMaterial) {
        Optional<RawMaterial> existingRawMaterial = rawMaterialRepositoryImpl.findRawMaterialById(id);
        if (existingRawMaterial.isPresent()) {
            RawMaterial materialToUpdate = existingRawMaterial.get();
            materialToUpdate.setName(rawMaterial.getName());
            return rawMaterialRepositoryImpl.saveRawMaterial(materialToUpdate);
        }
        return null; // 업데이트할 데이터가 없을 경우 null 반환
    }

    @Override
    public void deleteRawMaterial(Long id) {
        rawMaterialRepositoryImpl.deleteRawMaterial(id);
    }
}
