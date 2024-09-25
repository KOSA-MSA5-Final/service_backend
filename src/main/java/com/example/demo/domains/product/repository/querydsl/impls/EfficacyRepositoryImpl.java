package com.example.demo.domains.product.repository.querydsl.impls;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.repository.EfficacyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EfficacyRepositoryImpl {
    private final EfficacyRepository efficacyRepository;

    public EfficacyRepositoryImpl(EfficacyRepository efficacyRepository) {
        this.efficacyRepository = efficacyRepository;
    }

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
