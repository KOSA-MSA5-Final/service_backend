package com.example.demo.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayDTO {
//View로 전달할 결제 관련 데이터 입니다.

    private String orderUid;  // 주문 고유 번호
    private String itemName;  // 상품 이름
    private String buyerName;  // 구매자 이름
    private Long paymentPrice;  // 결제 금액
    private String buyerEmail;  // 구매자 이메일
    private String buyerAddress;  // 구매자 주소
      // 빌더 패턴을 사용하여 객체를 생성할 수 있도록 설정
//    public RequestPayDto(String orderUid, String itemName, String buyerName, Long paymentPrice, String buyerEmail, String buyerAddress) {
//        this.orderUid = orderUid;  // 주문 고유 번호 초기화
//        this.itemName = itemName;  // 상품 이름 초기화
//        this.buyerName = buyerName;  // 구매자 이름 초기화
//        this.paymentPrice = paymentPrice;  // 결제 금액 초기화
//        this.buyerEmail = buyerEmail;  // 구매자 이메일 초기화
//        this.buyerAddress = buyerAddress;  // 구매자 주소 초기화
//    }
}
