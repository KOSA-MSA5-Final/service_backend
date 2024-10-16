package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.MyLocation;
import com.example.demo.domains.member.entity.ShoppingOrder;
import com.example.demo.domains.member.entity.ShoppingOrderProduct;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.repository.MyLocationRepository;
import com.example.demo.domains.member.repository.ShoppingOrderProductRepository;
import com.example.demo.domains.member.repository.ShoppingOrderRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import com.example.demo.domains.member.service.impls.MyLocationService;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.ProductRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://mgng.site:80")
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PayController {
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final MyLocationService myLocationService;
    private final MyLocationRepository myLocationRepository;
    @Autowired
    private IamportClient iamportClient;
    private final MemberService memberService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingOrderRepository shoppingOrderRepository;
    @Autowired
    private ShoppingOrderProductRepository shoppingOrderProductRepository;


    @PostMapping("/{imp_uid}")
    public ResponseEntity<?> verifyPayment(@RequestHeader("Authorization") String token, @PathVariable String imp_uid) {
        try {
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(imp_uid);
            Payment payment = paymentResponse.getResponse();
            if (paymentResponse.getResponse().getStatus().equals("paid")) {
                String jwtToken = token.substring(7);
                String username = jwtUtil.getUsername(jwtToken);
                BigDecimal amount = payment.getAmount();

                String merchantUid = payment.getMerchantUid();
                String[] uidParts = merchantUid.split("_");
                Long productUid = Long.parseLong(uidParts[0]);
                Member buyer = memberRepository.findByUsername(username);

                List<MyLocation> deliverAddresses = myLocationService.findMyLocations(buyer);
                MyLocation deliveryAddress = null;
                if(deliverAddresses.size()>0) {
                    for (MyLocation deliverAddress : deliverAddresses) {
                        if(deliverAddress.getIsPrimary().equals("T")){
                            deliveryAddress = deliverAddress;
                        }
                    }
                }

                ShoppingOrder shoppingOrder = new ShoppingOrder();
                shoppingOrder.setIsAllShipping("F");
                Long totalPrice = Long.parseLong(String.valueOf(amount));
                shoppingOrder.setTotalPrice(totalPrice);
                shoppingOrder.setMemo("도착 전 연락 부탁드립니다.");
                shoppingOrder.setMember(buyer);
                if (deliveryAddress != null) {
                    shoppingOrder.setMyLocation(deliveryAddress);
                    shoppingOrder.setReceipient_name(deliveryAddress.getReceipientName());
                    shoppingOrder.setReceipient_telNum(deliveryAddress.getReceipientTellNum());
                }

                // 한국 시간대 설정
                ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");

                // 현재 한국 시간 가져오기
                ZonedDateTime koreaTime = ZonedDateTime.now(koreaZoneId);

                // ZonedDateTime을 Date 객체로 변환
                Date koreanDate = Date.from(koreaTime.toInstant());
                shoppingOrder.setBuyingDate(koreanDate);

                ShoppingOrder completedOrder = shoppingOrderRepository.save(shoppingOrder);

                Product product = productRepository.findById(productUid).get();
                ShoppingOrderProduct shoppingOrderProduct = new ShoppingOrderProduct();
                shoppingOrderProduct.setProduct(product);
                shoppingOrderProduct.setShoppingOrder(completedOrder);
                Integer quantity = Integer.parseInt(String.valueOf(product.getPrice()/totalPrice));
                shoppingOrderProduct.setQuantity(quantity);
                shoppingOrderProduct.setShippingStatus("대기중");
                shoppingOrderProduct.setCreatedAt(koreanDate);

                shoppingOrderProductRepository.save(shoppingOrderProduct);

                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.badRequest().body("Payment not completed");
            }
        } catch (IamportResponseException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }
}
