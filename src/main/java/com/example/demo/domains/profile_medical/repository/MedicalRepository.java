package com.example.demo.domains.profile_medical.repository;

import com.example.demo.domains.profile_medical.entity.Medical;
import com.example.demo.domains.profile_medical.entity.Profile;
import com.example.demo.domains.profile_medical.repository.querydsl.customs.MedicalRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 진료 기록 JPA 레포지토리
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */

public interface MedicalRepository extends JpaRepository<Medical, Long>, MedicalRepositoryCustom {
    List<Medical> findByProfile(Profile profile);
    List<Medical> findByVisitDate(Date visitDate);

    List<Medical> findAllByProfileId(Long profileId);

    // 특정 프로필 ID에 해당하는 최신 방문 기록을 조회하는 메서드
    Optional<Medical> findTopByProfileIdOrderByVisitDateDesc(long profileId);
}
