package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.repository.ProductDetailImgRepository;
import com.example.demo.domains.product.service.interfaces.ProductDetailImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailImgServiceImps implements ProductDetailImgService {
    private final ProductDetailImgRepository productDetailImgRepository;

    @Override
    public List<ProductDetailImg> getAllProductDetailImgs() {
        return productDetailImgRepository.findAll();
    }

    @Override
    public ProductDetailImg saveProductDetailImg(String imageUrl, Product product) {
        ProductDetailImg productDetailImg = new ProductDetailImg();
        productDetailImg.setImageUrl(imageUrl);
        productDetailImg.setProduct(product);
        return productDetailImgRepository.save(productDetailImg);
    }

    @Override
    public void deleteProductDetailImg(long id) {
        productDetailImgRepository.deleteById(id);
    }
}
