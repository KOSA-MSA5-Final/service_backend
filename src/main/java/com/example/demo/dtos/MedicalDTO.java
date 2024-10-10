package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MedicalDTO {
    String medical_name;
    String medical_price;
    public MedicalDTO(String medical_name, String medical_price) {
        this.medical_name = medical_name;
        this.medical_price = medical_price;
    }
}
