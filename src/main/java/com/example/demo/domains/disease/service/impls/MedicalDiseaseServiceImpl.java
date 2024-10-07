package com.example.demo.domains.disease.service.impls;

import com.example.demo.domains.disease.entity.MedicalDisease;
import com.example.demo.domains.disease.repository.MedicalDiseaseRepository;
import com.example.demo.domains.disease.service.interfaces.MedicalDiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalDiseaseServiceImpl implements MedicalDiseaseService {

    private final MedicalDiseaseRepository medicalDiseaseRepository;

    @Override
    @Transactional
    public MedicalDisease createMedicalDisease(MedicalDisease medicalDisease) {
        return medicalDiseaseRepository.save(medicalDisease);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalDisease> findByDiseaseSubAndProgressStatus(String diseaseName, String progressStatus) {
        return medicalDiseaseRepository.findByDiseaseSub_NameAndProgressStatus(diseaseName, progressStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalDisease> findByMedicalId(Long medicalId) {
        return medicalDiseaseRepository.findByMedical_Id(medicalId);
    }

    @Override
    @Transactional
    public Boolean deleteMedicalDiseaseById(Long id) {
        try {
            medicalDiseaseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}