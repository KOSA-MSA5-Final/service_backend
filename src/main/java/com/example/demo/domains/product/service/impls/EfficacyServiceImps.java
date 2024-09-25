package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.repository.querydsl.impls.EfficacyRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.EfficacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EfficacyServiceImps implements EfficacyService {
    private final EfficacyRepositoryImpl efficacyRepositoryImpl;

    @Override
    public List<Efficacy> getAllEfficacy() {
        return efficacyRepositoryImpl.findAllEfficacies();
    }

    @Override
    public Efficacy saveEfficacy(Efficacy efficacy) {
        return efficacyRepositoryImpl.saveEfficacy(efficacy);
    }

    @Override
    public Efficacy updateEfficacy(Efficacy efficacy) {
        return null;
    }

    @Override
    public void deleteEfficacy(long id) {
        efficacyRepositoryImpl.deleteEfficacy(id);
    }
}
