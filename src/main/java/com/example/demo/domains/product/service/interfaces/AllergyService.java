package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Allergy;

import java.util.List;

public interface AllergyService {
    List<Allergy> getAllAllergies();
    Allergy saveAllergy(Allergy allergy);
    Allergy updateAllergy(Allergy allergy);
    void deleteAllergy(long id);
}
