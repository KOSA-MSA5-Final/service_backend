package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.ProductRepository;
import com.example.demo.domains.product.repository.querydsl.impls.ProductRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.ProductService;
import com.example.demo.domains.profile_medical.entity.Animal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImps implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(String name, String maker, String type, long price, Animal animal) {
        Product product = new Product();
        product.setName(name);
        product.setMaker(maker);
        product.setType(type);
        product.setPrice(price);
        product.setAnimal(animal);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
