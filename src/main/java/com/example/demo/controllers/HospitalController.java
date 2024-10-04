package com.example.demo.controllers;

import com.example.demo.domains.profile_medical.dto.HospitalDTO;
import com.example.demo.domains.profile_medical.service.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequestMapping("/api/maps")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/affiliated-facilities")
    public ResponseEntity<List<HospitalDTO>> getAffiliatedFacilities() {
        List<HospitalDTO> affiliatedFacilities = hospitalService.getAffiliatedFacilities();
        return new ResponseEntity<>(affiliatedFacilities, HttpStatus.OK);
    }
}
