package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.interfaces.MedicalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.MedicalService;
import com.example.demo.util.UTCtoKorea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        return List.of();
    }

    @Override
    public List<Medical> getByVisitDate(Date visitDate) {
        return List.of();
    }

    @Override
    public List<Medical> getMedicalsByProfileId(Long profileId) {
        return List.of();
    }

    @Override
    public List<Medical> getMedicalsByProfileID_DESCByVisitDate(Long profileId) {
        return List.of();
    }

    @Override
    public List<Medical> getAllMedicalsByVisitDate(Date visitDate) {
        return List.of();
    }

    @Override
    public List<Medical> getAllMedicals() {
        return List.of();
    }

    @Override
    public Medical save(Date utcDate, String receipt_img, Profile profile) {
        Medical medical = new Medical();
        medical.setProfile(profile);
        medical.setReceipt_img(receipt_img);
        medical.setVisit_date(UTCtoKorea.convertUTCToKoreanTime(utcDate));
        return null;
    }
}
