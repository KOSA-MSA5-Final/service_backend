package com.example.demo.controllers;

import com.example.demo.domains.product.dto.ProductDTO;
import com.example.demo.domains.product.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 모든 제품을 조회하는 엔드포인트
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProductDTOs();
    }

    // 특정 타입의 제품을 조회하는 엔드포인트
    @GetMapping("/type/{type}")
    public List<ProductDTO> getProductsByType(@PathVariable String type) {
        return productService.getProductsByType(type);
    }
}