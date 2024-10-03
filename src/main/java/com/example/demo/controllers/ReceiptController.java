package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.dto.JoinDTO;
import com.example.demo.domains.member.dto.LoginRequest;
import com.example.demo.domains.member.dto.LoginResponse;
import com.example.demo.domains.member.dto.MemberDTO;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import com.example.demo.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dtos.UserAllInfoDTO;
import com.example.demo.dtos.CurrentProfileDTO;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin(origins = "https://localhost:80") // 해당 컨트롤러에만 CORS 설정
@RequestMapping("/receipt")
@RequiredArgsConstructor
public class ReceiptController {
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestHeader("Authorization") String token, ReceiptDTO receiptDTO) {
//        // Bearer 앞의 "Bearer " 제거
//        String jwtToken = token.substring(7);
//
//        // 토큰에서 사용자 정보 추출
//        String username = jwtUtil.getUsername(jwtToken);
//        String role = jwtUtil.getRole(jwtToken);
//
//        // 사용자 정보를 반환할 DTO 객체 생성
//        Member byUsername = memberRepository.findByUsername(username);
//
//    }
}
