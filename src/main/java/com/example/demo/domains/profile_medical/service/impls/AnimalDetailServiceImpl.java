package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.profile_medical.repository.interfaces.AnimalDetailRepository;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * author : 최혜령
 * date : 2024-09-24
 * description : 동물 소분류 서비스 구현 클래스
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-09-24         최혜령          최초 생성
 */
@Service
@RequiredArgsConstructor
public class AnimalDetailServiceImpl implements AnimalDetailService {
    private final AnimalDetailRepository animalDetailRepository;
}
