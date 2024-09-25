package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.ProductImg;

import java.util.List;

public interface ProductImgService {
    List<ProductImg> getAllProductImgs();
    ProductImg saveProductImg(ProductImg productImg);
    ProductImg updateProductImg(ProductImg productImg);
    void deleteProductImg(long id);
}
