/**
 * author : 윤다희
 * date : 2024-09-24
 * description : 상품 내용 엔티티
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         윤다희          최초 생성
 */

package com.example.demo.domains.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product;

    private String product_name;
    private String product_maker;
    private String product_type;

    //@ManyToOne
    //@JoinColumn(name = "animal_id")
    //private Animal animal;

    // RawMaterial(원료) 다대다 관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<RawMaterialsProduct> rawMaterials;  // ProductRawMaterial과 1대다 관계

    // ProductDetailImage 1대다 관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductDetailImg> detailImgs;

    // ProductImage 1대다 관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImg> images;

    // Ingredient 1대다 관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Ingredient> ingerdients;

    // Efficacy 1대다 관계 설정
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Efficacy> efficacies;
}
