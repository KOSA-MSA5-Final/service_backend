package com.example.demo.domains.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImgDTO {
    private long id;
    private String imageUrl;
    private long productId; // Product 외래 키를 담기 위한 필드
}
