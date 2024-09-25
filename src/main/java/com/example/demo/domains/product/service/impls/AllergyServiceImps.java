package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Allergy;
import com.example.demo.domains.product.repository.querydsl.impls.AllergyRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllergyServiceImps implements AllergyService {
    private final AllergyRepositoryImpl allergyRepositoryImpl;

    @Override
    public List<Allergy> getAllAllergies() {
        return allergyRepositoryImpl.findAllAllergies();
    }

    @Override
    public Allergy saveAllergy(Allergy allergy) {
        return allergyRepositoryImpl.saveAllergy(allergy);
    }

    @Override
    public Allergy updateAllergy(Allergy allergy) {
        return null;
    }

    @Override
    public void deleteAllergy(long id) {
        allergyRepositoryImpl.deleteAllergy(id);
    }

}
