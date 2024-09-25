package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.repository.RawMaterialRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RawMaterialRepositoryImpl {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialRepositoryImpl(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    // 모든 RawMaterials 반환
    public List<RawMaterial> findAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }

    // RawMaterial 저장
    public RawMaterial saveRawMaterial(RawMaterial rawMaterial) {
        return rawMaterialRepository.save(rawMaterial);
    }

    // RawMaterial 삭제
    public void deleteRawMaterial(Long id) {
        rawMaterialRepository.deleteById(id);
    }

    // ID로 RawMaterial 찾기
    public Optional<RawMaterial> findRawMaterialById(Long id) {
        return rawMaterialRepository.findById(id);
    }
}
