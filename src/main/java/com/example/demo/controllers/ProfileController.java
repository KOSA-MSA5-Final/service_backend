package com.example.demo.controllers;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.profile_medical.dto.ProfileDTO;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.ProfileRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequestMapping("/auth/profile") // 모든 요청에 /auth로 시작
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final MemberRepository memberRepository;

    // 특정 회원의 프로필 정보를 가져오는 메서드
    @GetMapping("/userinfo-all")
    public ResponseEntity<List<ProfileDTO>> getAllProfilesByMember(@RequestParam("memberId") Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        if (member.isPresent()) {
            // Profile 엔티티 리스트를 ProfileDTO 리스트로 변환하여 반환
            List<ProfileDTO> profileDTOs = profileService.getAllProfilesByMember(member.get());
            return ResponseEntity.ok(profileDTOs);
        } else {
            return ResponseEntity.notFound().build();
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
