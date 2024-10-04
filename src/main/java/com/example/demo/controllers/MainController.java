package com.example.demo.controllers;

import com.example.demo.domains.disease.service.interfaces.DiseaseSubService;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.entity.ProfileAllergy;
import com.example.demo.domains.product.repository.AllergyRepository;
import com.example.demo.domains.product.repository.ProfileAllergyRepository;
import com.example.demo.domains.product.service.interfaces.AllergyService;
import com.example.demo.domains.profile_medical.entity.Animal;
import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.AnimalDetailRepository;
import com.example.demo.domains.profile_medical.repository.AnimalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalDetailService;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalService;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import com.example.demo.dtos.ProfileDataDTO;
import com.google.auto.value.AutoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;


/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 병명 대분류 엔티티
 * <p>
 *
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24        최혜령          최초 생성
 * 2024-10-03       나선주         /profile/subdiseases 생성
 */

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequestMapping("/api") // 수정: 전체 경로가 "/api"로 시작하도록 설정
public class MainController {
    @Autowired
    private DiseaseSubService diseaseSubService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private AnimalDetailService animalDetailService;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AllergyService allergyService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AnimalDetailRepository animalDetailRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileAllergyRepository profileAllergyRepository;
    @Autowired
    private AllergyRepository allergyRepository;

//    @GetMapping("/message") // 수정: /api/message 경로로 매핑
//    public String testController(){
//        return "Backend is HERE!!!!";
//    }

    @PostMapping("/profile/subdiseases")
    public ResponseEntity<Map<String, List<String>>> getSubdiseases(@RequestBody List<String> isDisease){
        Map<String, List<String>>  subDiseasesMap = new HashMap<>();

        for (String diseaseName : isDisease) {
            List<String> subDiseases = diseaseSubService.getProfileSubDiseaseNames(diseaseName);
            subDiseasesMap.put(diseaseName, subDiseases);
        }

        return ResponseEntity.ok(subDiseasesMap);
    }

    @GetMapping("/profile/animal")
    public List<Animal> getAllAnimals(){
        return animalService.getAllAnimals();
    }

    @GetMapping("/profile/animalDetails")
    public Map<String, List<String>> getAllAnimalDetails(){
        System.out.println("품종 api가 왔어요");

        //개
        Animal dog = animalRepository.findById(1L).orElse(null);
        List<AnimalDetail> dogDetails= animalDetailService.findAllDetailsByAnimal(dog);
        List<String> dogsNames = new ArrayList<>();
        for(AnimalDetail animalDetail : dogDetails){
            String name = animalDetail.getName();
            dogsNames.add(name);
        }
        //고양이
        Animal cat = animalRepository.findById(2L).orElse(null);
        List<AnimalDetail> catDetails = animalDetailService.findAllDetailsByAnimal(cat);
        List<String> catNames = new ArrayList<>();
        for(AnimalDetail animalDetail : catDetails){
            String name = animalDetail.getName();
            catNames.add(name);
        }
        //결과를 map으로 반환
        Map<String, List<String>> result = new HashMap<>();
        result.put("dogs", dogsNames);
        result.put("cats", catNames);

        return result;
    }

    @GetMapping("/profile/allergies")
    public List<Allergy> getAllAllergies(){
        return allergyService.getAllAllergies();
    }

    @PostMapping("/profile/saveProfile")
    public ResponseEntity<String> saveProfile(@RequestBody ProfileDataDTO profileData){
        System.out.println("pet name: "+profileData.getPetName());
        System.out.println("petType: "+profileData.getPetType());//품종 이름
        System.out.println("petGender: "+profileData.getPetGender());
        System.out.println("petBirthDate: "+profileData.getPetBirthDate());
        System.out.println("petIsNeutered: "+profileData.getPetIsNeutered());
        System.out.println("petwillneutered: "+profileData.getPetWillNeutered());
        System.out.println("selectedAllergies: "+profileData.getSelectedAllergies());
        System.out.println("isDisease: "+profileData.getIsDisease());
        System.out.println("checkedDiseases: "+ profileData.getCheckedDiseases());
        System.out.println("Username: "+profileData.getUsername());

        Profile newbie = new Profile();
        newbie.setName(profileData.getPetName());
        newbie.setGender(profileData.getPetGender());
        newbie.setBirthday(profileData.getPetBirthDate());
        //age계산
        Date petBirthDate = profileData.getPetBirthDate();
        int age = calculateAge(petBirthDate);
        newbie.setAge(age);
        newbie.setIsneutered(profileData.getPetIsNeutered());
        newbie.setWillneutered(profileData.getPetWillNeutered());
        Member byUsername = memberRepository.findByUsername(profileData.getUsername());
        newbie.setMember(byUsername);
        AnimalDetail animalDetail = animalDetailRepository.findByName(profileData.getPetType()).get();
        newbie.setAnimalDetail(animalDetail);

        //check한 disease 뽑기
        Map<String, Object> checkedDiseases = profileData.getCheckedDiseases();
        Map<String, List<String>> trueAllergies = new HashMap<>();

        for (Map.Entry<String, Object> entry : checkedDiseases.entrySet()) {
            String systemName = entry.getKey();  // 시스템 이름 (ex. 소화기계통, 눈)
            Map<String, Boolean> diseases = (Map<String, Boolean>) entry.getValue();  // 질병 목록을 Map으로 캐스팅

            List<String> trueDiseases = new ArrayList<>();
            for (Map.Entry<String, Boolean> diseaseEntry : diseases.entrySet()) {
                if (diseaseEntry.getValue()) {
                    trueDiseases.add(diseaseEntry.getKey());  // true인 질병만 추가
                }
            }

            if (!trueDiseases.isEmpty()) {
                trueAllergies.put(systemName, trueDiseases);  // true인 값이 있으면 시스템에 추가
            }
        }

        //프로필저장
        Profile profile = profileService.saveSpecificProfile(newbie);

        //Profile별 allergy저장
//        for (Map.Entry<String, List<String>> entry : trueAllergies.entrySet()) {
//            List<String> diseases = entry.getValue();
//
//            for (String diseaseName : diseases) {
//                // Allergy 테이블에서 해당 allergy를 찾음
//                Allergy allergy = allergyRepository.findByName(diseaseName);
//                if (allergy != null) {
//                    // ProfileAllergy 엔티티에 저장
//                    ProfileAllergy profileAllergy = new ProfileAllergy();
//                    profileAllergy.setProfile(profile);
//                    profileAllergy.setAllergy(allergy);
//                    profileAllergyRepository.save(profileAllergy);
//                }
//            }
//        }

        return ResponseEntity.ok("일단 저장이 됐어");
    }

    public  int calculateAge(Date birthDate) {
        // Date 타입을 LocalDate로 변환
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 현재 날짜
        LocalDate currentDate = LocalDate.now();

        // 나이 계산
        if ((birthLocalDate != null) && (currentDate != null)) {
            return Period.between(birthLocalDate, currentDate).getYears();
        } else {
            return 0; // 생일 또는 현재 날짜가 null인 경우 0 리턴
        }
    }
}
