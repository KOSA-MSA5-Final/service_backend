package com.example.demo.domains.disease.repository.interfaces;

import com.example.demo.domains.disease.entity.DiseaseNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author : 김진석
 * date : 2024-09-24
 * description : 병명 대분류 기본 repository
 * <p>
 *
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24        김진석          최초 생성
 */

@Repository
public interface DiseaseNamesRepository extends JpaRepository<DiseaseNames, Long>, DiseaseNamesRepositoryCustom {

    // 기본적으로 대분류 병명을 이름으로 검색
    DiseaseNames findByName(String diseaseName);
}
