package com.example.demo.profile_medical;

import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.service.impls.MemberService;
import com.example.demo.domains.profile_medical.entity.Animal;
import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.AnimalDetailRepository;
import com.example.demo.domains.profile_medical.repository.ProfileRepository;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalDetailService;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalService;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class AnimalServiceTests {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private AnimalDetailService animalDetailService;

    @Autowired
    private AnimalDetailRepository animalDetailRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MemberService memberService;

    @Test
    @Transactional
    public void test_findAll() {
        animalService.save("강아지");
        animalService.save("고양이");
        List<Animal> animals = animalService.getAllAnimals();
        assertEquals(animals.size(), 2);
    }

    @Test
    @Transactional
    public void test_save_findAnimalByName_false(){
        animalService.save("강아지");
        animalService.save("고양이");
        Animal result = animalService.save("강아지");
        assertEquals(null, result);
    }

    @Test
    @Transactional
    public void test_findAnimalDetailsByAnimal(){
        Animal animal = animalService.save("강아지");
        animalDetailService.save("포메라니안", "강아지");
        List<AnimalDetail> animalDetails = animalDetailService.findAllDetailsByAnimal(animal);
        List<AnimalDetail> animalDeails1 = animalDetailService.getAnimalDetailsByAnimalName("강아지");

        assertEquals(animalDetails, animalDeails1);
        assertEquals(animalDeails1.size(), 1);
    }

    @Test
    public void insert_test(){
        AnimalDetail animalDetail = animalDetailRepository.findById(2L).get();
        Member member = memberService.findMemberByEmail("tangkim98@naver.com");
        if(member == null){
            System.out.println("No member found");
        } else {
            Profile profile = new Profile();
            profile.setAge(1);
            profile.setName("장구니");
            profile.setMember(member);
            profile.setIsCurrent("T");
            profile.setAnimalDetail(animalDetail);
            profile.setPictureUrl("https://elliebucket1.s3.ap-northeast-2.amazonaws.com/jangoon.gif");
            profileRepository.save(profile);
        }

    }

}
