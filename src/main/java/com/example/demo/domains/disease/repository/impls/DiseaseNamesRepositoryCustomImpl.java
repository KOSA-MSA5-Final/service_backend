package com.example.demo.domains.disease.repository.impls;

import com.example.demo.domains.disease.entity.DiseaseNames;
import com.example.demo.domains.disease.repository.interfaces.DiseaseNamesRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author : 김진석
 * date : 2024-09-24
 * description : 병명 대분류 관련 커스텀 repository 구현체
 * <p>
 *
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24        김진석          최초 생성
 */

@Repository
public class DiseaseNamesRepositoryCustomImpl implements DiseaseNamesRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DiseaseNames> findCustomDiseaseNames(String condition) {
        String jpql = "SELECT d FROM DiseaseNames d WHERE d.name LIKE :condition";
        TypedQuery<DiseaseNames> query = entityManager.createQuery(jpql, DiseaseNames.class);
        query.setParameter("condition", "%" + condition + "%");

        return query.getResultList();
    }
}
