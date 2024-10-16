package com.example.demo.controllers;

import com.example.demo.domains.profile_medical.dto.HospitalDTO;
import com.example.demo.domains.profile_medical.service.interfaces.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://d3b5vhsqsj0w82.cloudfront.net", "https://mgng.site","http://localhost:80", "https://localhost:80", "https://3.34.164.100:80", "http://mgng.site"}, allowCredentials = "true")
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/affiliated-facilities")
    public ResponseEntity<List<HospitalDTO>> getAffiliatedFacilities() {
        List<HospitalDTO> affiliatedFacilities = hospitalService.getAffiliatedFacilities();
        return new ResponseEntity<>(affiliatedFacilities, HttpStatus.OK);
    }
}
