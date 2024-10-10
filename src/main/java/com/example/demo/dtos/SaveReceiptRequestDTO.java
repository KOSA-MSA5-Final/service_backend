package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReceiptRequestDTO {
    List<UnfoundDiseaseDTO> extraMedical;
    ReceiptDTO receiptInfo;
    List<AnalysedDiseaseDTO> selectedDiseases;
}
