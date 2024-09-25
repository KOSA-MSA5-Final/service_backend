package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.repository.AllergyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllergyRepositoryImpl {
    private final AllergyRepository allergyRepository;

    public AllergyRepositoryImpl(AllergyRepository allergyRepository) {
        this.allergyRepository = allergyRepository;
    }

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
