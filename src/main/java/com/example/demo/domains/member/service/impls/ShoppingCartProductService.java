package com.example.demo.domains.member.service.impls;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.Product;
import com.example.demo.domains.member.entity.ShoppingCart;
import com.example.demo.domains.member.entity.ShoppingCartProduct;

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
 * 2024-09-24       나선주          method생성(CRD)
 */
public interface ShoppingCartProductService {
    //한 물품의 구매 수량 등록
    Long saveProductQuantity(ShoppingCartProduct shoppingCartProduct);

    //쇼핑카트에서 한 물품의 수량 가져오기
    Long findProductQuantity(ShoppingCartProduct shoppingCartProduct);

    //쇼핑카트에서 한 물품의 수량 조정하기
    void updateProductQuantity(Long shoppingCartProductId, long newQuantity);

    //쇼핑카트에서 한 물품 제거하기
    Boolean deleteProductFromCart(ShoppingCartProduct shoppingCartProduct);

}
