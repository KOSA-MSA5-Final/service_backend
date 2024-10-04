package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.ProfileRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
            }
        }
        return profile;
    }
}
