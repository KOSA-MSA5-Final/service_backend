package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.ProductDetailImg;

import java.util.List;

public interface ProductDetailImgService {
    List<ProductDetailImg> getAllProductDetailImgs();
    ProductDetailImg saveProductDetailImg(ProductDetailImg productDetailImg);
    ProductDetailImg updateProductDetailImg(ProductDetailImg productDetailImg);
    void deleteProductDetailImg(long id);
}
