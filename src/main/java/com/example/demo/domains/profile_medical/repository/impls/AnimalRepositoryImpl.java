package com.example.demo.domains.profile_medical.repository.impls;

import com.example.demo.domains.profile_medical.entity.Animal;
import com.example.demo.domains.profile_medical.repository.interfaces.AnimalRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 동물 대분류 커스텀 레포지토리 구현 클래스
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@RequiredArgsConstructor
public class AnimalRepositoryImpl implements AnimalRepositoryCustom {
    private final EntityManager em;

    @Override
    public Map<Animal, Integer> countProfilesByAnimal() {
        String jpql = "SELECT a, COUNT(p) " +
                "FROM Profile p " +
                "JOIN p.animalDetail ad " +
                "JOIN ad.animal a " +
                "GROUP BY a";

        List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();

        // 결과를 Map<Animal, Integer> 형태로 변환
        Map<Animal, Integer> profileCountsByAnimal = new HashMap<>();
        for (Object[] result : results) {
            Animal animal = (Animal) result[0];
            Long count = (Long) result[1];
            profileCountsByAnimal.put(animal, count.intValue());
        }
        return profileCountsByAnimal;
    }
}
