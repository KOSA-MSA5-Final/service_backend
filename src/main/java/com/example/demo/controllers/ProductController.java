package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.disease.entity.DiseaseSubProfile;
import com.example.demo.domains.disease.entity.MedicalDisease;
import com.example.demo.domains.disease.repository.DiseaseSubProfileRepository;
import com.example.demo.domains.disease.repository.DiseaseSubRepository;
import com.example.demo.domains.disease.repository.MedicalDiseaseRepository;
import com.example.demo.domains.member.dto.ShoppingOrderDTO;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.interfaces.ShoppingOrderServiceImpl;
import com.example.demo.domains.product.dto.ProductDTO;
import com.example.demo.domains.product.entity.DiseaseProduct;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.ProfileAllergy;
import com.example.demo.domains.product.entity.RawMaterial;
import com.example.demo.domains.product.repository.DiseaseProductRepository;
import com.example.demo.domains.product.repository.ProductRepository;
import com.example.demo.domains.product.repository.ProfileAllergyRepository;
import com.example.demo.domains.product.repository.RawMaterialRepository;
import com.example.demo.domains.product.service.impls.ProductServiceImps;
import com.example.demo.domains.product.service.interfaces.ProductService;
import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.MedicalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "https://3.34.164.100:80")
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
    private final DiseaseSubProfileRepository diseaseSubProfileRepository;
    private final ProfileAllergyRepository profileAllergyRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final DiseaseProductRepository diseaseProductRepository;

    // 맞춤 알고리즘 조회 함수
    private List<ProductDTO> getPersonalizedProducts(Member member) {

        Profile currentProfile = profileService.getCurrentProfileByMember(member);
        List<ProductDTO> personalizedProducts = new ArrayList<>();
        List<ProductDTO> allergyFiltered = new ArrayList<>();

        if (currentProfile != null) {
            List<ProductDTO> allProductsByType = productService.getAllProductDTOs();
            System.out.println("allProductsByType 갯수: " + allProductsByType.size());
            Set<String> profileAllergies = getAllergiesByProfile(currentProfile);
            System.out.println("알러지 갯수: " + profileAllergies.size());
            for (ProductDTO productDTO : allProductsByType) {

                List<RawMaterial> rawMaterials = rawMaterialRepository.findByProductId(productDTO.getId());
//                System.out.println("rawMaterials 갯수: " + rawMaterials.size());
                Set<String> materials = new HashSet<>();
                if(rawMaterials.size() > 0) {
                    for (RawMaterial rawMaterial : rawMaterials) {
                        materials.add(rawMaterial.getAllergy().getName());
                    }
                }
                boolean allergyContained = false;
                if(materials.size() > 0) {
                    allergyContained = Arrays.stream(materials.toArray()).anyMatch(item -> Arrays.asList(profileAllergies.toArray()).contains(item));
                }

                if(!allergyContained) {
                    allergyFiltered.add(productDTO);
                }
                System.out.println("allergyFiltered 여부: " + allergyContained);
            }

            if(allergyFiltered.size() > 0) {

                Set<String> profileDiseases = getDiseaseNamesByProfile(currentProfile);
                for (ProductDTO productDTO : allergyFiltered) {
                    List<DiseaseProduct> diseaseProducts = diseaseProductRepository.findByProductId(productDTO.getId());
                    Set<String> diseaseNames = new HashSet<>();
                    if (diseaseProducts.size() > 0) {
                        for(DiseaseProduct dp : diseaseProducts) {
                            diseaseNames.add(dp.getDiseaseNames().getName());
                        }
                    }
                    boolean diseaseContained = false;
                    if(diseaseNames.size() > 0) {
                        diseaseContained = Arrays.stream(diseaseNames.toArray()).anyMatch(item -> Arrays.asList(profileDiseases.toArray()).contains(item));
                    }

                    if(diseaseContained){
                        personalizedProducts.add(productDTO);
                    }
                }
            }

        }

        if(personalizedProducts.size() > 0) {
            return personalizedProducts;
        }
        return allergyFiltered;
    }

    // 프로필의 질병정보 대분류 이름으로 모두 호출
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

        List<DiseaseSubProfile> diseaseSubProfiles = diseaseSubProfileRepository.findByProfile(profile);
        if(diseaseSubProfiles.size() > 0){
            for (DiseaseSubProfile dsp : diseaseSubProfiles) {
                result.add(dsp.getDiseaseSub().getDiseaseNames().getName());
            }
        }

        return result;
    }

    // 프로필의 모든 알러지정보 호출
    private Set<String> getAllergiesByProfile(Profile profile){
        Set<String> result = new HashSet<>();
        List<ProfileAllergy> profileAllergies = profileAllergyRepository.findByProfile(profile);
        if(profileAllergies.size() > 0){
            for (ProfileAllergy profileAllergy : profileAllergies) {
                result.add(profileAllergy.getAllergy().getName());
            }
        }
        return result;
    }
    // 맞춤화된 제품을 조회하는 엔드포인트
    @GetMapping("/personalized/{type}")
    public List<ProductDTO> getPersonalizedProductsByType(
            @RequestHeader("Authorization") String token,
            @PathVariable String type) {
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);
        Member member = memberRepository.findByUsername(username);
        List<ProductDTO> personalizedProducts = getPersonalizedProducts(member);
        // 특정 타입으로 필터링
        List<ProductDTO> filteredByType = personalizedProducts.stream()
                .filter(product -> product.getType().equalsIgnoreCase(type))
                .toList();

        if (!filteredByType.isEmpty()) {
            return filteredByType;
        }

        System.out.println("맞춤형 제품 갯수: " + filteredByType.size());

        // 필터된 맞춤형 제품이 없을 경우 전체 상품 반환
        return productService.getAllProductDTOs().stream()
                .filter(product -> product.getType().equalsIgnoreCase(type))
                .toList();
    }
    // 모든 제품을 조회하는 엔드포인트
    @GetMapping
    public List<ProductDTO> getallProducts(@RequestHeader("Authorization") String token) {

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