package com.example.demo.domains.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShoppingOrderProductDTO {
    private long id; // 주문 상품 ID
    private long shoppingOrderId; // 주문 ID
    private long productId; // 상품 ID
    private Date createdAt; // 각 상품별 구매 날짜
    private long quantity; // 수량
    private String shippingStatus; // 배송 상태
    private String productMainImageUrl;
    private String brandName;
    private String productName;
}
