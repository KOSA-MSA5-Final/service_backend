package com.example.demo.domains.member.service.interfaces;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.Product;
import com.example.demo.domains.member.entity.ShoppingCart;
import com.example.demo.domains.member.entity.ShoppingCartProduct;
import com.example.demo.domains.member.repository.ShoppingCartProductRepository;
import com.example.demo.domains.member.service.impls.ShoppingCartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * author : 나선주
 * date : 2024-09-24
 * description : MemberService
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24       나선주          최초 생성
 * 2024-09-24       나선주          메소드(조회, 삭제, 생성) 생성
 */
@Service
public class ShoppingCartProductServiceImpl implements ShoppingCartProductService {
    @Autowired
    private ShoppingCartProductRepository shoppingCartProductRepository;

    @Override
    public Long saveProductQuantity(ShoppingCartProduct shoppingCartProduct) {
        ShoppingCartProduct save = shoppingCartProductRepository.save(shoppingCartProduct);
        return save.getQuantity();
    }

    @Override
    public Long findProductQuantity(ShoppingCartProduct shoppingCartProduct) {
        long id = shoppingCartProduct.getId();
        Optional<ShoppingCartProduct> byId = shoppingCartProductRepository.findById(id);
        return byId.get().getQuantity();
    }

    @Override
    public void updateProductQuantity(Long shoppingCartProductId, long newQuantity) {
        ShoppingCartProduct cartProduct = shoppingCartProductRepository.findById(shoppingCartProductId)
                .orElseThrow(() -> new RuntimeException("nsj: ShoppingCartProduct not found"));

        cartProduct.updateQuantity(newQuantity); // quantity 필드를 업데이트
        // 트랜잭션이 끝나면 자동으로 DB에 반영됨
    }

    @Override
    public Boolean deleteProductFromCart(ShoppingCartProduct shoppingCartProduct) {
        try {
            shoppingCartProductRepository.delete(shoppingCartProduct);
            return true;
        }catch(Exception e) {
            return false;
        }
    }
}
