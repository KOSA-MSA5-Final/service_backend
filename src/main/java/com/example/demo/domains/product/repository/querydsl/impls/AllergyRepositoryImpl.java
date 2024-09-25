package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.repository.AllergyRepository;
import com.example.demo.domains.product.repository.querydsl.customs.AllergyRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AllergyRepositoryImpl implements AllergyRepositoryCustom {
    private final AllergyRepository allergyRepository;

    public List<Allergy> findAllAllergies() {
        return allergyRepository.findAll();
    }

    public Allergy saveAllergy(Allergy allergy) {
        return allergyRepository.save(allergy);
    }

    public void deleteAllergy(Long id) {
        allergyRepository.deleteById(id);
    }
}
