package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.profile_medical.repository.interfaces.MedicalRepository;
import com.example.demo.domains.profile_medical.service.interfaces.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
