package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.repository.querydsl.impls.ProductDetailImgRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.ProductDetailImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailImgServiceImps implements ProductDetailImgService {
    private final ProductDetailImgRepositoryImpl productDetailImgRepositoryImpl;

    @Override
    public List<ProductDetailImg> getAllProductDetailImgs() {
        return productDetailImgRepositoryImpl.findAllProductDetailImgs();
    }

    @Override
    public ProductDetailImg saveProductDetailImg(ProductDetailImg productDetailImg) {
        return productDetailImgRepositoryImpl.saveProductDetailImg(productDetailImg);
    }

    @Override
    public ProductDetailImg updateProductDetailImg(ProductDetailImg productDetailImg) {
        return null;
    }

    @Override
    public void deleteProductDetailImg(long id) {
        productDetailImgRepositoryImpl.deleteProductDetailImg(id);
    }
}
