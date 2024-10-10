package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDTO {
    private List<MedicalDTO> medicalDTOs;
    private String reg_num;
    private String hospital_name;
    private String hospital_address;
    private String hospital_phoneNum;
    private String visitDate;
    private Integer totalCost;
    private String receipt_url;
}
