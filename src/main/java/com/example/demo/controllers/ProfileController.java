package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.profile_medical.dto.ProfileDTO;
import com.example.demo.domains.profile_medical.repository.ProfileRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://3.34.164.100:80")
@RequestMapping("/auth/profile") // 모든 요청에 /auth로 시작
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final JWTUtil jwtUtil;
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    // 현재 로그인한 사용자의 모든 프로필 조회
    @GetMapping("/userinfo-all")
    public ResponseEntity<List<ProfileDTO>> getAllProfilesByMember(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7); // "Bearer " 제거
            String username = jwtUtil.getUsername(jwtToken);

            // 사용자 정보 가져오기
            Member member = memberRepository.findByUsername(username);

            // 사용자 정보로 프로필 조회
            List<ProfileDTO> profileDTOs = profileService.getAllProfilesByMember(member);
            return ResponseEntity.ok(profileDTOs);

        } catch (Exception e) {
            return ResponseEntity.status(401).build(); // JWT 파싱 실패 시 401 Unauthorized 반환
        }
    }


    // 특정 프로필을 가져오는 메서드
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long profileId) {
        Optional<ProfileDTO> profileDTO = profileService.getAllProfiles().stream()
                .filter(p -> p.getId().equals(profileId))
                .map(profileService::convertToDTO)
                .findFirst();

        return profileDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
