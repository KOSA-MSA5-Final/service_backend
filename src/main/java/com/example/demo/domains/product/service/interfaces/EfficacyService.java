package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.entity.Product;

import java.util.List;

public interface EfficacyService {
    List<Efficacy> getAllEfficacy();
    Efficacy saveEfficacy(String details, Product product);
    void deleteEfficacy(long id);
}
