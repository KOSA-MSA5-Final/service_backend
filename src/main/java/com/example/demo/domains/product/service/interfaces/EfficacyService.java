package com.example.demo.domains.product.service.interfaces;

import com.example.demo.domains.product.entity.Efficacy;

import java.util.List;

public interface EfficacyService {
    List<Efficacy> getAllEfficacy();
    Efficacy saveEfficacy(Efficacy efficacy);
    Efficacy updateEfficacy(Efficacy efficacy);
    void deleteEfficacy(long id);
}
