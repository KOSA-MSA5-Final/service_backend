package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.profile_medical.dto.ProfileDTO;
import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.repository.ProfileRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import com.example.demo.dtos.ProfileDataDTO;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.ProfileRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 동물 프로필 서비스 구현 클래스
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public List<Profile> getProfilesByMember(Member member) {
        return profileRepository.findByMember(member);
    }

    @Override
    public List<Medical> getMedicalsByMember(Member member) {
        return profileRepository.findMedicalByMember(member);
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile save(String name, Integer age, Member member, AnimalDetail animalDetail, String pictureUrl) {
        Profile profile = new Profile();
        profile.setName(name);
        profile.setAge(age);
        profile.setMember(member);
        profile.setAnimalDetail(animalDetail);
        profile.setPictureUrl(pictureUrl);
        return profileRepository.save(profile);
    }

    @Override
    public Boolean delete(Profile profile) {
        try{
            profileRepository.delete(profile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Profile getCurrentProfileByMember(Member member) {
        List<Profile> profiles = profileRepository.findByMember(member);
        Profile profile = null;
        for (Profile p : profiles) {
            if(p.getIsCurrent().equals("T")){
                profile = p;
                break; // T인 프로필을 찾으면 더 이상 반복할 필요가 없으므로 종료 //나선주 추가
            }
        }
        return profile;
    }

    @Override
    public Profile changeProfile(Profile before, Long afterId) {
        before.setIsCurrent("F");
        Profile after = profileRepository.findById(afterId).get();
        after.setIsCurrent("T");
        profileRepository.save(before);

        return profileRepository.save(after);
    }

    @Override
    public Profile saveSpecificProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    // Profile 엔티티를 ProfileDTO로 변환하는 메서드
    public ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(profile.getId());
        dto.setName(profile.getName());
        dto.setGender(profile.getGender());
        dto.setAge(profile.getAge());
        dto.setBirthday(profile.getBirthday());
        dto.setIsNeutered(profile.getIsneutered());
        dto.setWillNeutered(profile.getWillneutered());
        dto.setPictureUrl(profile.getPictureUrl());
        dto.setIsCurrent(profile.getIsCurrent());

        // AnimalDetail의 이름을 PetType으로 설정
        if (profile.getAnimalDetail() != null) {
            dto.setAnimalType(profile.getAnimalDetail().getName());
        } else {
            dto.setAnimalType("Unknown");
        }

        return dto;
    }

    // 특정 회원의 모든 프로필을 ProfileDTO 리스트로 반환하는 메서드
    public List<ProfileDTO> getAllProfilesByMember(Member member) {
        List<Profile> profiles = profileRepository.findByMember(member);
        return profiles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
