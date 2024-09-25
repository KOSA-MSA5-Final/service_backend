package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(long id);
}
