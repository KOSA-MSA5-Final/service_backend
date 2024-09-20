//package com.example.demo.oAuth;
//
//
//import com.nimbusds.jose.jwk.source.ImmutableSecret;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
//import org.springframework.security.oauth2.jwt.JwsHeader;
//import org.springframework.security.oauth2.jwt.JwtClaimsSet;
//import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.util.Map;
//
////@Component
//@Log4j2
//public class CustomOAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private String jwtSecretKey = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007a" ;
//    private String jwtIssuer ="TM-application" ;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        log.info("CustomOAuth2LoginSuccessHandler   successssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
//        log.info("JWT Secret Key: " + jwtSecretKey);
//        log.info("JWT Issuer: " + jwtIssuer);
//
//        // OAuth2User 에서 사용자 정보를 가져옴
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();   // OAuth2 제공자 확인
//        System.out.println("registrationId: " + registrationId);
//        String username = null;
//
//        if (registrationId.contains("kakao")) {
//            Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttribute("properties");
//            if(properties != null) {
//                username = properties != null ? (String) properties.get("nickname") : null;    // Kakao Oauth2 에서는 properties 에 'nickname' 속성 사용
//            }
//        }
//        else if (registrationId.contains("google")) {
//            username = oAuth2User.getAttribute("name"); // Google OAuth2에서 'name' 속성 사용
//        } else if(registrationId.contains("naver")){
//            var res = (Map<String, Object>)oAuth2User.getAttribute("response");
//            username = (String)res.get("name");
//        }
//
//
//        // JWT 생성
//        String jwtToken = createJwtToken(username);
//
//        // JWT 토큰 로그로 출력
//        log.info("Generated JWT Token: " + jwtToken);
//
//        setJwtTokenAsCookie(jwtToken, response);
//
//        response.sendRedirect("http://localhost:3000/");  // mypage로 리다이렉트
//
//    }
//    private String createJwtToken(String username) {
//        Instant now = Instant.now();
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer(jwtIssuer)
//                .issuedAt(now)
//                .expiresAt(now.plusSeconds(24 * 3600)) // 24시간 유효
//                .subject(username)
//                .claim("role", "INVALID") // 역할 추가 (필요시 변경)
//                .build();
//
//        var encoder = new NimbusJwtEncoder(
//                new ImmutableSecret<>(jwtSecretKey.getBytes()));
//        var params = JwtEncoderParameters.from(
//                JwsHeader.with(MacAlgorithm.HS256).build(), claims);
//
//        return encoder.encode(params).getTokenValue();
//    }
//
//    private void setJwtTokenAsCookie(String jwtToken, HttpServletResponse response) {
//        Cookie cookie = new Cookie("jwt_token", jwtToken);
//        cookie.setHttpOnly(false);  // HTTP Only 설정
//        cookie.setSecure(true);  // HTTPS에서만 전송되도록 설정 (개발 시엔 false로 설정할 수 있음)
//        cookie.setPath("/");  // 쿠키가 전송될 경로
//        cookie.setMaxAge(24 * 60 * 60);  // 쿠키 유효 기간 (24시간)
//        response.addCookie(cookie);
//    }
//
//}