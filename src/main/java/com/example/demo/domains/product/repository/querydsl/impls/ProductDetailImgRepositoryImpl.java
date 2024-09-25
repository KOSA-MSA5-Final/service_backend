package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.repository.ProductDetailImgRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDetailImgRepositoryImpl {

    private final ProductDetailImgRepository productDetailImgRepository;

    public ProductDetailImgRepositoryImpl(ProductDetailImgRepository productDetailImgRepository) {
        this.productDetailImgRepository = productDetailImgRepository;
    }

    public List<ProductDetailImg> findAllProductDetailImgs() {
        return productDetailImgRepository.findAll();
    }

    public ProductDetailImg saveProductDetailImg(ProductDetailImg productDetailImg) {
        return productDetailImgRepository.save(productDetailImg);
    }

    public void deleteProductDetailImg(Long id) {
        productDetailImgRepository.deleteById(id);
    }
}
