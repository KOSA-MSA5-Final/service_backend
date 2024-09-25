package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.RawMaterialsProduct;

import java.util.List;

public interface RawMaterialsProductService {
    List<RawMaterialsProduct> getAllRawMaterialsProducts();
    RawMaterialsProduct saveRawMaterialsProduct(RawMaterialsProduct rawMaterialsProduct);
    RawMaterialsProduct updateRawMaterialsProduct(RawMaterialsProduct rawMaterialsProduct);
    void deleteRawMaterialsProduct(long id);
}
