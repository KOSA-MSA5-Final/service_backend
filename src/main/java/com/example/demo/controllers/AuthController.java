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
@CrossOrigin(origins = {"https://d3b5vhsqsj0w82.cloudfront.net", "https://mgng.site","http://localhost:80", "https://localhost:80", "https://3.34.164.100:80", "http://mgng.site"} ,allowCredentials = "true")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController { //로그인 관련 컨트롤러
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MailService mailService;
    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody JoinDTO joinDTO) {
//        System.out.println("============================회원가입메소드입니다");
//        System.out.println(joinDTO.getUsername());

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
//            System.out.println("jwt토큰은 "+token);

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

    // 작성자 : 최혜령    작성일 : 2024년 10월 03일
    // 계정 관련 모든 정보 로딩 컨트롤러 함수
    @GetMapping("/userinfo-all")
    public ResponseEntity<?> getUserInfoAll(@RequestHeader("Authorization") String token) {
        // Bearer 앞의 "Bearer " 제거
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);
        String role = jwtUtil.getRole(jwtToken);

        // 사용자 정보를 반환할 DTO 객체 생성
        Member member = memberRepository.findByUsername(username);

        List<Profile> profiles = profileService.getProfilesByMember(member);

        UserAllInfoDTO userAllInfoDTO = new UserAllInfoDTO(member, profiles);

        return ResponseEntity.ok(userAllInfoDTO);
    }

    // 작성자 : 최혜령    작성일 : 2024년 10월 03일
    // 현재 로그인 프로필 정보 로딩 컨트롤러 함수
    @GetMapping("/currentProfileInfo")
    public ResponseEntity<?> getCurrentProfileInfo(@RequestHeader("Authorization") String token) {
        // Bearer 앞의 "Bearer " 제거
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);
        String role = jwtUtil.getRole(jwtToken);
//        System.out.println("token: "+ jwtToken);

        // 사용자 정보를 반환할 DTO 객체 생성
        Member member = memberRepository.findByUsername(username);
        Profile profile = profileService.getCurrentProfileByMember(member);
//        System.out.println("여기까지는 괜찮아!");
//        System.out.println("nsj: profile값은 "+profile);
        
        // profile이 null이면 CurrentProfileDTO도 null로 반환 - 나선주 추가
        CurrentProfileDTO currentProfileDTO = (profile != null) ? new CurrentProfileDTO(profile) : null;
//        CurrentProfileDTO currentProfileDTO = new CurrentProfileDTO(profile); //나선주 주석처리
        
        return ResponseEntity.ok(currentProfileDTO);
    }

    @PostMapping("/changeCurrentProfile")
    public ResponseEntity<?> changeCurrentProfile(
            @RequestHeader("Authorization") String token,
            @RequestParam Long profileId) {

        System.out.println("Received request to change profile. ProfileId: " +  profileId);

        try {
            // Bearer 토큰에서 "Bearer " 제거
            String jwtToken = token.replace("Bearer ", "");

            // 토큰에서 사용자 정보 추출
            String username = jwtUtil.getUsername(jwtToken);
            Member member = memberRepository.findByUsername(username);

            if (member == null) {
//                System.out.println("User not found for username: " + username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            Profile currentProfile = profileService.getCurrentProfileByMember(member);
            if (!currentProfile.getId().equals(profileId)){
                profileService.changeProfile(currentProfile, profileId);

//                System.out.println("Profile changed successfully for user: " + username);
            }

            return ResponseEntity.ok("success");
        } catch (Exception e) {
//            System.out.println("Error changing profile" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
