package com.example.demo.domains.profile_medical.entity;

import jakarta.persistence.*;

@Entity
public class MedicalHospital {
    @Id @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="business_registration_number")
    Hospital hospital;

    @ManyToOne
    @JoinColumn(name="medical_id")
    Medical medical;
}
