package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.ProductImgRepository;
import com.example.demo.domains.product.repository.querydsl.customs.ProductImgRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductImgRepositoryImpl implements ProductImgRepositoryCustom {

    private final ProductImgRepository productImgRepository;

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
