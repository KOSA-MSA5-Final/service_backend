package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.RawMaterialsProduct;
import com.example.demo.domains.product.repository.querydsl.impls.RawMaterialsProductRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.RawMaterialsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialsProductServiceImps implements RawMaterialsProductService {
    private final RawMaterialsProductRepositoryImpl rawMaterialsProductRepositoryImpl;

    @Override
    public List<RawMaterialsProduct> getAllRawMaterialsProducts() {
        return rawMaterialsProductRepositoryImpl.findAllRawMaterialsProducts();
    }

    @Override
    public RawMaterialsProduct saveRawMaterialsProduct(RawMaterialsProduct rawMaterialsProduct) {
        return rawMaterialsProductRepositoryImpl.saveRawMaterialsProduct(rawMaterialsProduct);
    }

    @Override
    public RawMaterialsProduct updateRawMaterialsProduct(RawMaterialsProduct rawMaterialsProduct) {
        return null;
    }

    @Override
    public void deleteRawMaterialsProduct(long id) {
        rawMaterialsProductRepositoryImpl.deleteRawMaterialsProduct(id);
    }
}
