package com.example.demo.domains.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private long id;
    private String name;
    private String maker;
    private String type;
    private String subtype;
    private long price;
    private String animalName; // Animal 이름을 담기 위한 필드
    private String origin;
    private String allRawMaterial;
    private String ingredient;
    private String calories;
    private String weight;
    private String ageGroup;
    private String function;
    private String mainimageurl;

    // 이미지 URL 리스트 필드 추가
    private List<String> imageUrls; // 상품 이미지 URL 리스트
    private List<String> detailImageUrls; // 상품 상세 이미지 URL 리스트

    // DiseaseProduct 관련 정보 추가
    private List<DiseaseProductDTO> diseaseProducts;  // DiseaseProductDTO 리스트
}
