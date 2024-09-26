package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.entity.Efficacy;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.repository.EfficacyRepository;
import com.example.demo.domains.product.repository.querydsl.impls.EfficacyRepositoryImpl;
import com.example.demo.domains.product.service.interfaces.EfficacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EfficacyServiceImps implements EfficacyService {
    private final EfficacyRepository efficacyRepository;

    @Override
    public List<Efficacy> getAllEfficacy() {
        return efficacyRepository.findAll();
    }

    @Override
    public Efficacy saveEfficacy(String details, Product product) {
        Efficacy efficacy = new Efficacy();
        efficacy.setDetails(details);
        efficacy.setProduct(product);
        return efficacyRepository.save(efficacy);
    }

    @Override
    public void deleteEfficacy(long id) {
        efficacyRepository.deleteById(id);
    }
}
