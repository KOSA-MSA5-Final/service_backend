package com.example.demo.profile_medical;

import com.example.demo.domains.profile_medical.service.interfaces.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AnimalServiceTests {
    @Autowired
    private AnimalService animalService;

    @Test
    public void findAll() {
        if(animalService.getAllAnimals().isEmpty()){
            System.out.println("EXAMPLE!!!!!");
        }
    }
}
