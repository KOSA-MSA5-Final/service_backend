package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.dto.JoinDTO;
import com.example.demo.domains.member.dto.LoginRequest;
import com.example.demo.domains.member.dto.LoginResponse;
import com.example.demo.domains.member.dto.MemberDTO;
import com.example.demo.domains.member.entity.CustomUserDetails;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.RefreshToken;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import com.example.demo.util.MailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.*;

@RestController
@CrossOrigin(origins = "https://localhost:80") // 해당 컨트롤러에만 CORS 설정
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { //로그인 관련 컨트롤러
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JoinDTO joinDTO) {
        System.out.println("============================회원가입메소드입니다");
        System.out.println(joinDTO.getUsername());

        memberService.joinProcess(joinDTO);

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/register/fetch_ids")
    public List<String> fetchUserIds() {
        List<String> userIds = new ArrayList<>();
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            userIds.add(member.getUsername());
        }
        return userIds;
    }

    @GetMapping("/register/email")
    public String verfiyEmail(@RequestParam("email") String email) {
        String code = createCode();
        mailService.sendEmail(email, "멍지냥지 회원가입 인증코드", "인증 코드는 : " + code + " 입니다.");
        return code;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 사용자 인증
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // JWT 토큰 생성
            String token = jwtUtil.createJwt(loginRequest.getUsername(), "Role_admin", 86400000L); // 사용자 이름을 사용하여 토큰 생성
            System.out.println("jwt토큰은 "+token);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 생성 실패");
            }

            return ResponseEntity.ok(new LoginResponse(token)); // JWT 토큰 반환
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(null); // 인증 실패 시 401 반환
        }
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        // Bearer 앞의 "Bearer " 제거
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);
        String role = jwtUtil.getRole(jwtToken);

        // 사용자 정보를 반환할 DTO 객체 생성
        Member byUsername = memberRepository.findByUsername(username);

        return ResponseEntity.ok(byUsername);
    }


    @PostMapping("/userinfo/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody MemberDTO memberDTO) {
        memberService.updateMember(memberDTO);

        Member updatedUserInfo = memberService.findMemberByEmail(memberDTO.getEmail());

        return ResponseEntity.ok(updatedUserInfo);
    }

    private static String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MemberService.createCode() exception occur");
        }
        return null;
    }

}
