package com.example.demo.domains.profile_medical.dto;

import com.example.demo.domains.disease.dto.MedicalDiseaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class MedicalDTO {

    private long id;
    private Date visitDate;
    private String receiptImg;
    private String object;
    private long totalCost;
    private long profileId; // Profile의 ID를 추가하여 참조 가능하도록 설정

    // 병원 정보를 위한 필드 추가
    private String hospitalName;   // 병원 이름
    private String hospitalAddress; // 병원 위치
    private List<MedicalDiseaseDTO> medicalDiseases; // 진료 기록과 연관된 질병 정보 리스트 추가
}