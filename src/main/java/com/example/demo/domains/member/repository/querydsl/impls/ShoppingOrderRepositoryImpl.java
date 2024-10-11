package com.example.demo.domains.member.repository.querydsl.impls;

import com.example.demo.domains.disease.entity.DiseaseNames;
import com.example.demo.domains.member.entity.ShoppingOrder;
import com.example.demo.domains.member.repository.querydsl.customs.ShoppingOrderRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShoppingOrderRepositoryImpl implements ShoppingOrderRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<ShoppingOrder> findAllByMember(Long memberId) {
        String jpql = "SELECT so FROM ShoppingOrder so WHERE so.member.id = :memberId";  // JPQL 쿼리 작성
        TypedQuery<ShoppingOrder> query = em.createQuery(jpql, ShoppingOrder.class);
        query.setParameter("memberId", memberId);  // 파라미터 바인딩

        return query.getResultList();  // 결과 반환
    }
}
