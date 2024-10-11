package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.dto.ShoppingOrderDTO;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.interfaces.ShoppingOrderServiceImpl;
import com.example.demo.domains.product.dto.ProductDTO;
import com.example.demo.domains.product.service.impls.ProductServiceImps;
import com.example.demo.domains.product.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductServiceImps productServiceImps;
    private final ShoppingOrderServiceImpl shoppingOrderService;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    // 모든 제품을 조회하는 엔드포인트
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProductDTOs();
    }

    // 특정 타입의 제품을 조회하는 엔드포인트
    @GetMapping("/type/{type}")
    public List<ProductDTO> getProductsByType(@PathVariable String type) {
        return productService.getProductsByType(type);
    }

    @GetMapping("get/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productServiceImps.getProductsById(id);
    }

//    @GetMapping("order/{memberId}")
//    public List<ShoppingOrderDTO> getOrdersByMember(@PathVariable Long memberId) {
//        return shoppingOrderService.getShoppingOrderByMemberId(memberId);
//    }

    @GetMapping("/order")
    public List<ShoppingOrderDTO> getOrdersByMember(@RequestHeader("Authorization") String token) {
        // 토큰에서 사용자 정보를 추출
        String jwtToken = token.substring(7);  // "Bearer " 제거
        String username = jwtUtil.getUsername(jwtToken);

        // 사용자 정보 가져오기
        Member member = memberRepository.findByUsername(username);

        // 추출한 사용자 ID를 이용하여 주문 조회
        return shoppingOrderService.getShoppingOrderByMemberId(member.getMember_id());
    }
}