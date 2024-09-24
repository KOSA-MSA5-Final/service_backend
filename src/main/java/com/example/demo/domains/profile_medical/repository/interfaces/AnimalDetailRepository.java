package com.example.demo.domains.profile_medical.repository.interfaces;

import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 동물 소분류 JPA 레포지토리

 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
public interface AnimalDetailRepository extends JpaRepository<AnimalDetail, Long>, AnimalDetailRepositoryCustom {
}
