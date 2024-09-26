package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.entity.RawMaterialsProduct;

import java.util.List;

public interface RawMaterialsProductService {
    List<RawMaterialsProduct> getAllRawMaterialsProducts();
    RawMaterialsProduct saveRawMaterialsProduct(boolean is_primary, Product product, RawMaterial rawMaterial);
    void deleteRawMaterialsProduct(long id);
}
