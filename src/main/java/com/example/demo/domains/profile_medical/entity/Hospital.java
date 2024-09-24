package com.example.demo.domains.profile_medical.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hospital {
    @Id @Column(unique = true, nullable = false, name = "business_registration_number")
    Long id;

    @Column(name = "hospital_name")
    String name;

    @Column(name = "hospital_address")
    String address;

    @Column(name = "doctor_name")
    String doctor;

    @Column(name = "is_ours")
    String is_ours = "F";
}
