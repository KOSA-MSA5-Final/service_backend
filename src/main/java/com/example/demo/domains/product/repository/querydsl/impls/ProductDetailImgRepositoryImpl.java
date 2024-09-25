package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.repository.ProductDetailImgRepository;
import com.example.demo.domains.product.repository.querydsl.customs.ProductDetailImgRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductDetailImgRepositoryImpl implements ProductDetailImgRepositoryCustom {

    private final ProductDetailImgRepository productDetailImgRepository;

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
