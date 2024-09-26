package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.repository.AllergyRepository;
import com.example.demo.domains.product.service.interfaces.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyServiceImps implements AllergyService {
    private final AllergyRepository allergyRepository;

    @Override
    public List<Allergy> getAllAllergies() {
        return allergyRepository.findAll();
    }

    @Override
    public Allergy saveAllergy(String name, String symptoms) {
        Allergy allergy = new Allergy();
        allergy.setName(name);
        allergy.setSymptoms(symptoms);
        return allergyRepository.save(allergy);
    }

    @Override
    public void deleteAllergy(long id) {
        allergyRepository.deleteById(id);
    }

}
