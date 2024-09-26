package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.ProductImgRepository;
import com.example.demo.domains.product.service.interfaces.ProductImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImgServiceImps implements ProductImgService {

    private final ProductImgRepository productImgRepository;

    @Override
    public List<ProductImg> getAllProductImgs() {
        return productImgRepository.findAll();
    }

    @Override
    public ProductImg saveProductImg(String imageUrl, Product product) {
        ProductImg productImg = new ProductImg();
        productImg.setImageUrl(imageUrl);
        productImg.setProduct(product);
        return productImgRepository.save(productImg);
    }

    @Override
    public void deleteProductImg(long id) {
        productImgRepository.deleteById(id);
    }
}
