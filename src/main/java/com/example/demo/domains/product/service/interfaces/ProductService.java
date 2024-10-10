package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.dto.ProductDTO;
import com.example.demo.domains.product.entity.Product;

import java.util.List;

/**
 * author : 윤다희
 * date : 2024-09-24
 * description : 상품 서비스 인터페이스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         윤다희          최초 생성
 */

public interface ProductService {
    List<Product> getAllProducts();
    Product saveProduct(Product product);
    void deleteProduct(long id);

    List<ProductDTO> getAllProductDTOs();

    List<ProductDTO> getProductsByType(String type);

    ProductDTO getProductsById(Long id);
}
