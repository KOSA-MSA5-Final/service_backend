package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.profile_medical.entity.Animal;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(String name, String maker, String type, long price, Animal animal);
    void deleteProduct(long id);
}
