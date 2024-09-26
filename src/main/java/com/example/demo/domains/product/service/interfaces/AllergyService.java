package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Allergy;

import java.util.List;

public interface AllergyService {
    List<Allergy> getAllAllergies();
    Allergy saveAllergy(String name, String symptoms);
    void deleteAllergy(long id);
}
