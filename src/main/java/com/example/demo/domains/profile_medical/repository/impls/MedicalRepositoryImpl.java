package com.example.demo.domains.profile_medical.repository.impls;

import com.example.demo.domains.profile_medical.repository.interfaces.MedicalRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 진료 기록 커스텀 레포지토리 구현 클래스
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@RequiredArgsConstructor
public class MedicalRepositoryImpl implements MedicalRepositoryCustom {
    private final EntityManager em;

}
