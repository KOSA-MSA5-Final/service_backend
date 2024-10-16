package com.example.demo.controllers;


import com.example.demo.domains.profile_medical.dto.MedicalDTO;
import com.example.demo.domains.profile_medical.service.interfaces.MedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://d3b5vhsqsj0w82.cloudfront.net", "https://mgng.site","http://localhost:80", "https://localhost:80", "https://3.34.164.100:80", "http://mgng.site"})
@RequestMapping("/api/medical")
@RequiredArgsConstructor
public class MedicalController {

    private final MedicalService medicalService;

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<MedicalDTO>> getMedicalRecordsByProfileId(@PathVariable long profileId) {
        List<MedicalDTO> medicalRecords = medicalService.findMedicalRecordsByProfileId(profileId);
        return ResponseEntity.ok(medicalRecords);
    }

    @GetMapping("/{medicalId}")
    public ResponseEntity<MedicalDTO> getMedicalRecordById(@PathVariable long medicalId) {
        MedicalDTO medicalRecord = medicalService.findMedicalRecordById(medicalId);
        return ResponseEntity.ok(medicalRecord);
    }

    // 최신 메디컬 기록을 가져오는 엔드포인트 추가
    @GetMapping("/profile/{profileId}/latest")
    public ResponseEntity<MedicalDTO> getLatestMedicalRecordByProfileId(@PathVariable long profileId) {
        MedicalDTO latestMedicalRecord = medicalService.findLatestMedicalRecordByProfileId(profileId);
        return ResponseEntity.ok(latestMedicalRecord);
    }
}