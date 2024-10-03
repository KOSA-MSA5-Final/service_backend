package com.example.demo.controllers;

import com.example.demo.domains.disease.service.interfaces.DiseaseSubService;
import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.service.interfaces.AllergyService;
import com.example.demo.domains.profile_medical.entity.Animal;
import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.repository.AnimalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalDetailService;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalService;
import com.google.auto.value.AutoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @GetMapping("/message") // 수정: /api/message 경로로 매핑
    public String testController(){
        return "Backend is HERE!!!!";
    }

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
}
