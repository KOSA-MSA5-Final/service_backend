package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.ProductImgRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductImgRepositoryImpl {

    private final ProductImgRepository productImgRepository;

    public ProductImgRepositoryImpl(ProductImgRepository productImgRepository) {
        this.productImgRepository = productImgRepository;
    }

    public List<ProductImg> findAllProductImgs() {
        return productImgRepository.findAll();
    }

    public ProductImg saveProductImg(ProductImg productImg) {
        return productImgRepository.save(productImg);
    }

    public void deleteProductImg(Long id) {
        productImgRepository.deleteById(id);
    }
}
