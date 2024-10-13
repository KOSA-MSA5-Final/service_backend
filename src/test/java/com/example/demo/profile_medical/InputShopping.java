package com.example.demo.profile_medical;

import com.example.demo.domains.member.entity.ShoppingOrder;
import com.example.demo.domains.member.entity.ShoppingOrderProduct;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.MyLocation;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.interfaces.MemberServiceImpl;
import com.example.demo.domains.member.service.interfaces.MyLocationServiceImpl;
import com.example.demo.domains.member.service.interfaces.ShoppingOrderProductServiceImpl;
import com.example.demo.domains.member.service.interfaces.ShoppingOrderServiceImpl;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.service.impls.ProductServiceImps;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InputShopping {

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private MyLocationServiceImpl myLocationService;

    @Autowired
    private ProductServiceImps productServiceImps;

    @Autowired
    private ShoppingOrderServiceImpl shoppingOrderService;

    @Autowired
    private ShoppingOrderProductServiceImpl shoppingOrderProductService;

    @Test
    @Transactional
    @Rollback(false)
    void testSaveShoppingOrderWithProducts() {
        // 1. Member, MyLocation, Product 객체 생성
        Member member = memberService.findMemberByEmail("svdragunov@naver.com");
        assertThat(member).isNotNull();

        List<MyLocation> location = myLocationService.findMyLocations(member);
        assertThat(location).isNotEmpty();

        Product product1 = productServiceImps.getAllProducts().get(productServiceImps.getAllProducts().size() - 1);

        // 2. ShoppingOrder 객체 생성 및 설정
        ShoppingOrder order = new ShoppingOrder();
        order.setTotalPrice(3000L);
        order.setIsAllShipping("F");
        order.setMember(member);
        order.setMyLocation(location.get(0));
        order.setReceipient_name("박범배");
        order.setReceipient_telNum("010-1234-5678");
        order.setBuyingDate(new Date());
        order.setMemo("빠른 배송 부탁드립니다");

        order = shoppingOrderService.saveOrder(order);
        assertThat(order).isNotNull();  // 저장된 주문 확인

        // 3. ShoppingOrderProduct 생성 및 저장
        ShoppingOrderProduct orderProduct2 = new ShoppingOrderProduct();
        orderProduct2.setShoppingOrder(order);
        orderProduct2.setProduct(product1);
        orderProduct2.setQuantity(2L);
        orderProduct2.setShippingStatus("대기");
        orderProduct2.setCreatedAt(new Date());

        shoppingOrderProductService.savePrdQuantityToOrder(orderProduct2);

        // 4. 저장된 데이터 검증
        List<ShoppingOrder> savedOrders = shoppingOrderService.findAllOrders();
        assertThat(savedOrders).isNotEmpty();  // 저장된 주문이 있는지 확인
        assertThat(savedOrders.get(0).getOrderProductList()).hasSize(1);  // 상품 1개 포함 확인
    }

}

