package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.dto.JoinDTO;
import com.example.demo.domains.member.dto.LoginRequest;
import com.example.demo.domains.member.dto.LoginResponse;
import com.example.demo.domains.member.entity.CustomUserDetails;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.RefreshToken;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://localhost:80") // 해당 컨트롤러에만 CORS 설정
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { //로그인 관련 컨트롤러
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JoinDTO joinDTO) {
        System.out.println("============================회원가입메소드입니다");
        System.out.println(joinDTO.getUsername());

        memberService.joinProcess(joinDTO);

        return ResponseEntity.ok("User registered successfully");
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

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//
//        try{
//            // UsernamePasswordAuthenticationToken 생성
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(username, password);
//
//            // 인증 시도
//            Authentication authentication = authenticationManager.authenticate(authToken);
//
//            // 인증 성공 시 JWT 생성
//            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//            String role = authentication.getAuthorities().iterator().next().getAuthority();
//            String token = jwtUtil.createJwt(customUserDetails.getUsername(), role, 60 * 60 * 10L);
//
//            // JWT를 응답 헤더에 추가
//            return ResponseEntity.ok()
//                    .header("Authorization", "Bearer " + token)
//                    .body(new LoginResponse(token));
//
//            } catch (AuthenticationException e) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//            }
//    }



//        @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//        System.out.println("=====================로그인요청");
//        System.out.println(loginRequest.getEmail());
//
//        try {
//            // 이메일과 비밀번호로 인증 시도
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()
//                    )
//            );
//
//            // 인증이 성공했을 때 사용자 정보 가져오기
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            Optional<Member> byEmail = memberRepository.findByEmail(loginRequest.getEmail());
//            Member user = byEmail.get();
//
//            // 기존의 Refresh Token 확인
//            System.out.println("1번위치예요");
//            RefreshToken existingRefreshToken = refreshTokenService.findByUserId(user.getMember_id());
//            System.out.println("2번위치입니다");
//
//            if(existingRefreshToken != null) {
//                //이미 생성된 refresh token이 있는 경우, 기존 토큰 사용
//                String accessToken = tokenProvider.generateToken(user, Duration.ofDays(1)); // 새로운 액세스 토큰 생성
//                // 응답으로 토큰 전송
//                return ResponseEntity.ok(new LoginResponse(accessToken, existingRefreshToken));
//            }else{
//                //새로운 Access Token과 Refresh Token 생성
//                String accessToken = tokenProvider.generateToken(user, Duration.ofDays(1)); // 하루 유효한 액세스 토큰
//                RefreshToken refreshToken = tokenProvider.generateRefreshToken(user.getMember_id()); // 리프레시 토큰
//
//                // Refresh Token 저장
//                refreshTokenService.saveRefreshToken(refreshToken);
//
//                // 응답으로 토큰 전송
//                return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
//            }
//
//
//
//        } catch (AuthenticationException e) {
//            System.err.println("인증 실패: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 올바르지 않습니다.");
//        }
//    }

}
