package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.disease.dto.MedicalDiseaseDTO;
import com.example.demo.domains.disease.entity.MedicalDisease;
import com.example.demo.domains.profile_medical.dto.MedicalDTO;
import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.entity.MedicalHospital;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.MedicalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.MedicalService;
import com.example.demo.util.UTCtoKorea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 진료기록 서비스 구현 클래스
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
    private final MedicalRepository medicalRepository;

    @Override
    public List<Medical> getByProfile(Profile profile) {
        return medicalRepository.findByProfile(profile);
    }

    @Override
    public List<Medical> getByVisitDate(Date visitDate) {
        return medicalRepository.findByVisitDate(visitDate);
    }

    @Override
    public List<Medical> getMedicalsByProfileId(Long profileId) {
        return medicalRepository.findMedicalsByProfileId(profileId);
    }

    @Override
    public List<Medical> getMedicalsByProfileID_DESCByVisitDate(Long profileId) {
        return medicalRepository.findMedicalsByProfileID_DESCByVisitDate(profileId);
    }

    @Override
    public List<Medical> getAllMedicalsByVisitDate(Date visitDate) {
        return medicalRepository.findAllMedicalsByVisitDate(visitDate);
    }

    @Override
    public List<Medical> getAllMedicals() {
        return medicalRepository.findAll();
    }

    @Override
    public Medical save(Date utcDate, String receipt_img, String object, Profile profile, Long totalCost) {
        Medical medical = new Medical();
        medical.setProfile(profile);
        medical.setReceipt_img(receipt_img);
        medical.setVisitDate(UTCtoKorea.convertUTCToKoreanTime(utcDate));
        return medicalRepository.save(medical);
    }

    @Override
    public Boolean delete(Medical medical) {
        try {
            medicalRepository.delete(medical);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MedicalDTO> findMedicalRecordsByProfileId(long profileId) {
        // 특정 프로필 ID에 해당하는 모든 진료 기록을 DTO로 변환하여 반환
        List<MedicalDTO> medicalDTOs = medicalRepository.findAllByProfileId(profileId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        // DTO 리스트를 콘솔에 출력
        System.out.println("MedicalDTOs: " + medicalDTOs);
        return medicalDTOs;
    }

    @Override
    public MedicalDTO findMedicalRecordById(long id) {
        // 특정 ID의 진료 기록을 조회하여 DTO로 변환하여 반환
        Optional<Medical> medical = medicalRepository.findById(id);
        return medical.map(this::convertToDTO).orElse(null);
    }

    // 최신 메디컬 기록을 조회하는 메서드 구현
    @Override
    public MedicalDTO findLatestMedicalRecordByProfileId(long profileId) {
        Optional<Medical> latestMedical = medicalRepository.findTopByProfileIdOrderByVisitDateDesc(profileId);
        return latestMedical.map(this::convertToDTO).orElse(null);
    }

    private MedicalDTO convertToDTO(Medical medical) {
        MedicalDTO dto = new MedicalDTO();
        dto.setId(medical.getId());
        dto.setVisitDate(medical.getVisitDate());
        dto.setObject(medical.getObject());
        dto.setTotalCost(medical.getTotalCost());
        dto.setReceiptImg(medical.getReceipt_img());
        dto.setProfileId(medical.getProfile().getId()); // 프로필 ID도 함께 설정

        // 병원 정보를 MedicalHospital을 통해 가져와서 DTO에 설정
        if (medical.getMedicalHospitals() != null && !medical.getMedicalHospitals().isEmpty()) {
            MedicalHospital medicalHospital = medical.getMedicalHospitals().get(0); // 첫 번째 병원 정보만 사용
            dto.setHospitalName(medicalHospital.getHospital().getName());
            dto.setHospitalAddress(medicalHospital.getHospital().getAddress());
        }

        // 진료 기록에 연관된 MedicalDisease를 DTO에 추가
        if (medical.getMedicalDiseases() != null && !medical.getMedicalDiseases().isEmpty()) {
            List<MedicalDiseaseDTO> medicalDiseaseDTOs = medical.getMedicalDiseases()
                    .stream()
                    .map(this::convertToMedicalDiseaseDTO)
                    .collect(Collectors.toList());
            dto.setMedicalDiseases(medicalDiseaseDTOs); // MedicalDiseases 리스트 설정
        }
        return dto;
    }

    // MedicalDisease 엔티티를 MedicalDiseaseDTO로 변환하는 메서드
    private MedicalDiseaseDTO convertToMedicalDiseaseDTO(MedicalDisease medicalDisease) {
        MedicalDiseaseDTO dto = new MedicalDiseaseDTO();
        dto.setId(medicalDisease.getId());
        dto.setDiseaseName(medicalDisease.getDiseaseSub().getDiseaseNames().getName());
        dto.setDiseaseSubCategory(medicalDisease.getDiseaseSub().getName());
        dto.setProgressStatus(medicalDisease.getProgressStatus());
        dto.setDiagnosisDate(medicalDisease.getDiagnosisDate());
        return dto;
    }
}
