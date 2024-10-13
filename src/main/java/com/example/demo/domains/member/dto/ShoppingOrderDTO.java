package com.example.demo.domains.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ShoppingOrderDTO {

    private long id;
    private long totalPrice;
    private String isAllShipping; // 모든 품목 배송 여부
    private String receipientName; // 수취인 이름
    private String receipientTelNum; // 수취인 전화번호
    private Date buyingDate; // 구매 날짜
    private String memo; // 메모
    private String location;
    private List<ShoppingOrderProductDTO> orderProductList; // 주문 상품 목록
}
