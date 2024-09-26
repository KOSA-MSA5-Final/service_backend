package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.ProductDetailImg;

import java.util.List;

public interface ProductDetailImgService {
    List<ProductDetailImg> getAllProductDetailImgs();
    ProductDetailImg saveProductDetailImg(String imageUrl, Product product);
    void deleteProductDetailImg(long id);
}
