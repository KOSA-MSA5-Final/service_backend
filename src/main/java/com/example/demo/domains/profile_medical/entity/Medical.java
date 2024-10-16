package com.example.demo.domains.profile_medical.entity;

import com.example.demo.domains.disease.entity.MedicalDisease;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 진료 기록 Entity
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@Entity
@Getter
@Setter
public class Medical {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medical_id")
    private long id;

    @Column(name = "visit_date")
    private Date visitDate;

    @Column(name="receipt_img_url")
    private String receipt_img;

    @Column(name = "object")
    private String object;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "total_cost")
    private long totalCost;

    @OneToMany(mappedBy = "medical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalHospital> medicalHospitals; // MedicalHospital과의 관계 설정

    // Medical과 MedicalDisease의 연관관계 설정 (One-to-Many)
    @OneToMany(mappedBy = "medical", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalDisease> medicalDiseases;
}
