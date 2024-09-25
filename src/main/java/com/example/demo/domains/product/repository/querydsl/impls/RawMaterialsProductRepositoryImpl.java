package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.RawMaterialsProduct;
import com.example.demo.domains.product.repository.RawMaterialsProductRepository;
import com.example.demo.domains.product.repository.querydsl.customs.RawMaterialsProductRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RawMaterialsProductRepositoryImpl implements RawMaterialsProductRepositoryCustom {

    private final RawMaterialsProductRepository rawMaterialsProductRepository;

    public List<RawMaterialsProduct> findAllRawMaterialsProducts() {
        return rawMaterialsProductRepository.findAll();
    }

    public RawMaterialsProduct saveRawMaterialsProduct(RawMaterialsProduct rawMaterialsProduct) {
        return rawMaterialsProductRepository.save(rawMaterialsProduct);
    }

    public void deleteRawMaterialsProduct(Long id) {
        rawMaterialsProductRepository.deleteById(id);
    }
}
