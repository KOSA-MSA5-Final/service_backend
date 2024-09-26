package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.ProductImg;

import java.util.List;

public interface ProductImgService {
    List<ProductImg> getAllProductImgs();
    ProductImg saveProductImg(String imageUrl, Product product);
    void deleteProductImg(long id);
}
