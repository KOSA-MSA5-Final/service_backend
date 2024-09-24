package com.example.demo.domains.profile_medical.repository.interfaces;

import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import java.util.Map;
/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 동물 소분류 커스텀 레포지토리
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
public interface AnimalDetailRepositoryCustom {
    Map<AnimalDetail, Integer> countProfilesByAnimalDetail();
}
