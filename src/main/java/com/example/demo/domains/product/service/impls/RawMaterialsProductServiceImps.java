package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.entity.RawMaterialsProduct;
import com.example.demo.domains.product.repository.RawMaterialsProductRepository;
import com.example.demo.domains.product.service.interfaces.RawMaterialsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialsProductServiceImps implements RawMaterialsProductService {
    private final RawMaterialsProductRepository rawMaterialsProductRepository;

    @Override
    public List<RawMaterialsProduct> getAllRawMaterialsProducts() {
        return rawMaterialsProductRepository.findAll();
    }

    @Override
    public RawMaterialsProduct saveRawMaterialsProduct(boolean is_primary, Product product, RawMaterial rawMaterial) {
        RawMaterialsProduct rawMaterialsProduct = new RawMaterialsProduct();
        rawMaterialsProduct.setRawMaterial(rawMaterial);
        rawMaterialsProduct.setProduct(product);
        rawMaterialsProduct.set_primary(is_primary);
        return rawMaterialsProductRepository.save(rawMaterialsProduct);
    }

    @Override
    public void deleteRawMaterialsProduct(long id) {
        rawMaterialsProductRepository.deleteById(id);
    }
}
