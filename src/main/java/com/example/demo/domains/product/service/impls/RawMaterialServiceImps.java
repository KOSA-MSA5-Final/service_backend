package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.repository.RawMaterialRepository;
import com.example.demo.domains.product.service.interfaces.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImps implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    @Override
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }

    @Override
    public RawMaterial saveRawMaterial(String name, String type) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(name);
        rawMaterial.setType(type);
        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public void deleteRawMaterial(Long id) {
        rawMaterialRepository.deleteById(id);
    }
}
