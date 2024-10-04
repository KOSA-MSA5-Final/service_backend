package com.example.demo.controllers;

import com.example.demo.dtos.MedicalDTO;
import com.example.demo.dtos.ReceiptDTO;
import com.example.demo.util.AwsS3Service;
import com.example.demo.util.GoogleVisionOCR;
import com.example.demo.util.GptService;
import com.example.demo.util.ReceiptParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;
    private final ReceiptParserService receiptParserService;
    private final GptService gptService;
    //Amazon S3에 파일 업로드
    //return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
    @PostMapping("/file")
    public ReceiptDTO uploadFile(
            @RequestParam("qimage") List<MultipartFile> multipartFile) throws IOException {
        List<String> result = awsS3Service.uploadFile(multipartFile);
        String resultURL = result.get(0);
        String parsed = GoogleVisionOCR.execute(resultURL);

        System.out.println(parsed);

        System.out.println("\n>>>>여기부터 gpt의 대답");
        String beforeAsk = "아래는 영수증(청구서) 내용을 OCR로 읽어낸거야. 내가 원하는 값들은 진료 내역들 (이름-가격)과 병원명, 대표자이름, 사업자 등록번호와 사업장 소재지, 전화번호, 총 가격 및 방문 날짜시간이야. 각 json 형태로 key 이름을 진료내역 (안에 값은 또다른 json으로 키는 진료이름, 값은 가격), 병원명, 대표자이름, 사업자등록번호, 사업장주소, 전화번호, 총가격, 방문날짜시각 이라고 명시하여 아래내용들을 정형화 해줘. 혹시나, 동물명이 들어가는 결과값이 안생기도록 조심해줘.\n";
        ResponseEntity<?> gptResponse = gptService.getAssistantMsg(beforeAsk + parsed);
        ReceiptDTO receiptDTO = parseReceipt(gptResponse);

        return receiptDTO;
    }


    @DeleteMapping("/file")
    public void deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
    }//end deleteFile

    private static ReceiptDTO parseReceipt(ResponseEntity<?> receipt) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> resultMap;

            // ResponseEntity의 body가 이미 Map인지 확인
            if (receipt.getBody() instanceof Map) {
                resultMap = (Map<String, Object>) receipt.getBody();
            } else {
                // body가 String인 경우, JSON 파싱 진행
                String responseBody = (String) receipt.getBody();
                resultMap = objectMapper.readValue(responseBody, Map.class);
            }

            // ReceiptDTO 객체 생성 및 데이터 설정
            ReceiptDTO receiptDTO = new ReceiptDTO();
            receiptDTO.setReg_num(String.valueOf(resultMap.get("사업자등록번호")));
            receiptDTO.setHospital_name(String.valueOf(resultMap.get("병원명")));
            receiptDTO.setHospital_address(String.valueOf(resultMap.get("사업장주소")));
            receiptDTO.setHospital_phoneNum(String.valueOf(resultMap.get("전화번호")));
            receiptDTO.setVisitDate(String.valueOf(resultMap.get("방문날짜시각")));

            String totalCost = String.valueOf(resultMap.get("총가격"));
            receiptDTO.setTotalCost(Integer.parseInt(totalCost.replace(",", "")));

            // 진료내역을 MedicalDTO 리스트로 변환
            List<MedicalDTO> medicalDTOs = new ArrayList<>();
            Map<String, Object> medicalDetails = (Map<String, Object>) resultMap.get("진료내역");
            for (Map.Entry<String, Object> entry : medicalDetails.entrySet()) {
                String price = String.valueOf(entry.getValue());
                medicalDTOs.add(new MedicalDTO(entry.getKey(), price.replace(",", "")));
            }
            receiptDTO.setMedicalDTOs(medicalDTOs);



            return receiptDTO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

