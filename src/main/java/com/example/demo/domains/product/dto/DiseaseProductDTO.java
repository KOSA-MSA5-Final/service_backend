package com.example.demo.domains.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiseaseProductDTO {
    private long id;
    private String diseaseName;  // DiseaseNames 엔티티의 name 필드
    private long productId;      // Product 엔티티의 id
}