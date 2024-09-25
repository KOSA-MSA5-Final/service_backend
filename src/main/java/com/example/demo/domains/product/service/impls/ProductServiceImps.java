package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.querydsl.impls.ProductRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImps implements ProductService {

    private final ProductRepositoryImpl productRepositoryImpl;

    @Override
    public List<Product> getAllProducts() {
        return productRepositoryImpl.findAllProducts();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepositoryImpl.saveProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        productRepositoryImpl.deleteProduct(id);
    }
}
