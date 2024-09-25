package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.RawMaterialsProduct;
import com.example.demo.domains.product.repository.RawMaterialsProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RawMaterialsProductRepositoryImpl {

    private final RawMaterialsProductRepository rawMaterialsProductRepository;

    public RawMaterialsProductRepositoryImpl(RawMaterialsProductRepository rawMaterialsProductRepository) {
        this.rawMaterialsProductRepository = rawMaterialsProductRepository;
    }

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
