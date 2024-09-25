package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.querydsl.impls.ProductImgRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.ProductImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImgServiceImps implements ProductImgService {

    private final ProductImgRepositoryImpl productImgRepositoryImpl;

    @Override
    public List<ProductImg> getAllProductImgs() {
        return productImgRepositoryImpl.findAllProductImgs();
    }

    @Override
    public ProductImg saveProductImg(ProductImg productImg) {
        return productImgRepositoryImpl.saveProductImg(productImg);
    }

    @Override
    public ProductImg updateProductImg(ProductImg productImg) {
        return null;
    }

    @Override
    public void deleteProductImg(long id) {
        productImgRepositoryImpl.deleteProductImg(id);
    }
}
