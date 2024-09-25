package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.repository.EfficacyRepository;
import com.example.demo.domains.product.repository.querydsl.customs.EfficacyRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EfficacyRepositoryImpl implements EfficacyRepositoryCustom {
    private final EfficacyRepository efficacyRepository;

    public List<Efficacy> findAllEfficacies() {
        return efficacyRepository.findAll();
    }

    public Efficacy saveEfficacy(Efficacy efficacy) {
        return efficacyRepository.save(efficacy);
    }

    public void deleteEfficacy(Long id) {
        efficacyRepository.deleteById(id);
    }
}
