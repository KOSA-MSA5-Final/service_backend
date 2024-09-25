package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.ProductRepository;
import com.example.demo.domains.product.repository.querydsl.customs.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
