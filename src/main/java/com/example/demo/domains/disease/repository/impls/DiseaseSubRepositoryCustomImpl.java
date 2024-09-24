package com.example.demo.domains.disease.repository.impls;

import com.example.demo.domains.disease.entity.DiseaseSub;
import com.example.demo.domains.disease.repository.interfaces.DiseaseSubRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author : 김진석
 * date : 2024-09-24
 * description : 병명 소분류 관련 커스텀 repository 구현체
 * <p>
 *
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24        김진석          최초 생성
 */

@Repository
public class DiseaseSubRepositoryCustomImpl implements DiseaseSubRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DiseaseSub> findCustomDiseaseSubs(String condition) {
        String jpql = "SELECT ds FROM DiseaseSub ds WHERE ds.name LIKE :condition";
        TypedQuery<DiseaseSub> query = entityManager.createQuery(jpql, DiseaseSub.class);
        query.setParameter("condition", "%" + condition + "%");

        return query.getResultList();
    }
}
