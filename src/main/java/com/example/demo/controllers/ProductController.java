package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.disease.entity.DiseaseSubProfile;
import com.example.demo.domains.disease.entity.MedicalDisease;
import com.example.demo.domains.disease.repository.DiseaseSubRepository;
import com.example.demo.domains.disease.repository.MedicalDiseaseRepository;
import com.example.demo.domains.member.dto.ShoppingOrderDTO;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.interfaces.ShoppingOrderServiceImpl;
import com.example.demo.domains.product.dto.ProductDTO;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.service.impls.ProductServiceImps;
import com.example.demo.domains.product.service.interfaces.ProductService;
import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.MedicalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductServiceImps productServiceImps;
    private final ShoppingOrderServiceImpl shoppingOrderService;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final ProfileService profileService;
    private final MedicalRepository medicalRepository;
    private final MedicalDiseaseRepository medicalDiseaseRepository;
    private final DiseaseSubRepository diseaseSubRepository;

    // 맞춤 알고리즘 조회 함수
    private List<ProductDTO> getPersonalizedProducts(Member member) {
        List<ProductDTO> personalizedProducts = new ArrayList<>();
        Profile currentProfile = profileService.getCurrentProfileByMember(member);
        if (currentProfile != null) {
            List<ProductDTO> allProductsByType = productService.getProductsByType(currentProfile.getAnimalDetail().getAnimal().getName());
//            for (ProductDTO productDTO : allProductsByType) {
////                if ()
//            }
        } else {
            List<ProductDTO> allProducts = productService.getAllProductDTOs();
            return allProducts;
        }

        return personalizedProducts;
    }

    private Set<String> getDiseaseNamesByProfile(Profile profile){
        Set<String> result = new HashSet<>();

        List<Medical> profileMedicals = medicalRepository.findAllByProfileId(profile.getId());

        if(profileMedicals.size() > 0){
            for (Medical medical : profileMedicals) {
                List<MedicalDisease> medicalDiseases = medicalDiseaseRepository.findByMedical_Id(medical.getId());
                if (medicalDiseases.size() > 0) {
                    for (MedicalDisease medicalDisease : medicalDiseases){
                        result.add(medicalDisease.getDiseaseSub().getDiseaseNames().getName());
                    }
                }
            }
        }

//        List<DiseaseSubProfile> diseaseSubProfiles = diseaseSubRepository.findByProfile
        return result;
    }
    // 모든 제품을 조회하는 엔드포인트
    @GetMapping
    public List<ProductDTO> getAllProducts(@RequestHeader("Authorization") String token) {
        List<ProductDTO> allProducts = productService.getAllProductDTOs();

        return productService.getAllProductDTOs();
    }

    @GetMapping("get/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productServiceImps.getProductsById(id);
    }

    @GetMapping("/order")
    public List<ShoppingOrderDTO> getOrdersByMember(@RequestHeader("Authorization") String token) {
        // 토큰에서 사용자 정보를 추출
        String jwtToken = token.substring(7);  // "Bearer " 제거
        String username = jwtUtil.getUsername(jwtToken);

        // 사용자 정보 가져오기
        Member member = memberRepository.findByUsername(username);

        // 추출한 사용자 ID를 이용하여 주문 조회
        return shoppingOrderService.getShoppingOrderByMemberId(member.getMember_id());
    }
}