package com.example.demo.domains.profile_medical.service.impls;

import com.example.demo.domains.profile_medical.entity.Animal;
import com.example.demo.domains.profile_medical.entity.AnimalDetail;
import com.example.demo.domains.profile_medical.repository.interfaces.AnimalDetailRepository;
import com.example.demo.domains.profile_medical.service.interfaces.AnimalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<AnimalDetail> findAllDetailsByAnimal(Animal animal) {
        return animalDetailRepository.findByAnimal(animal);
    }

    @Override
    public AnimalDetail save(String name, Animal animal) {
        AnimalDetail animalDetail = new AnimalDetail();
        animalDetail.setName(name);
        animalDetail.setAnimal(animal);
        return animalDetailRepository.save(animalDetail);
    }

    @Override
    public Boolean delete(AnimalDetail animalDetail) {
        try{
            animalDetailRepository.delete(animalDetail);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Map<AnimalDetail, Integer> countProfilesByAnimalDetail() {
        return animalDetailRepository.countProfilesByAnimalDetail();
    }
}
