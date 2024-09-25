package com.example.demo.domains.member.service.impls;

import com.example.demo.domains.member.entity.Product;
import com.example.demo.domains.member.entity.ShoppingCart;
import com.example.demo.domains.member.entity.ShoppingCartProduct;
import com.example.demo.domains.member.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ShoppingCartProductServiceTest {
    @Autowired
    ShoppingCartProductService shoppingCartProductService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void savePrdQuantityToCart() {
        ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct();
        shoppingCartProduct.setId(1);
        shoppingCartProduct.setProduct(productRepository.findById(1L).get());
        shoppingCartProduct.setQuantity(1);



    }

    @Test
    void findPrdQuantityFromCart() {
    }

    @Test
    void findProductsFromCart() {
    }

    @Test
    void updatePrdQuantityFromCart() {
    }

    @Test
    void deleteProductFromCart() {
    }
}