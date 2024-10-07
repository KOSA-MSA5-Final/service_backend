package com.example.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.dtos.DiseaseAnalysisDTO;
import com.example.demo.dtos.MedicalDTO;
import com.example.demo.dtos.ReceiptDTO;
import com.example.demo.util.AwsS3Service;
import com.example.demo.util.GoogleVisionOCR;
import com.example.demo.util.GptService;
import com.example.demo.util.ReceiptParserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "https://localhost:80")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReceiptController {
    private final AwsS3Service awsS3Service;
    private final ReceiptParserService receiptParserService;
    private final GptService gptService;
    //Amazon S3에 파일 업로드
    //return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
    @PostMapping("/s3/file")
    public ReceiptDTO uploadFile(
            @RequestParam("qimage") List<MultipartFile> multipartFile) throws IOException {
        List<String> result = awsS3Service.uploadFile(multipartFile);
        String resultURL = result.get(0);
        String parsed = GoogleVisionOCR.execute(resultURL);

        System.out.println(parsed);

        System.out.println("\n>>>>여기부터 gpt의 대답");
        String beforeAsk = "아래는 영수증(청구서) 내용을 OCR로 읽어낸거야. 내가 원하는 값들은 진료 내역들 (이름-가격)과 병원명, 대표자이름, 사업자 등록번호와 사업장 소재지, 전화번호, 총 가격 및 방문 날짜시간이야. 각 json 형태로 key 이름을 '진료내역리스트'(각 항목의 키는 진료이름, 값은 가격), '병원명', '대표자이름', '사업자등록번호', '사업장주소', '전화번호', '총가격', '방문날짜시간' 이라고 명시하여 아래내용들을 정형화 해줘. key 이름에 빈 문자열 생기지 않도록 주의하고, 만약 각 key의 값을 찾을 수 없다면, 키는 그대로 넣고 빈 문자열을 넣어서 보내줘\n";
        ResponseEntity<?> gptResponse = gptService.getAssistantMsg(beforeAsk + parsed);
        System.out.println(gptResponse.getBody());
        ReceiptDTO receiptDTO = parseReceipt(gptResponse);

        return receiptDTO;
    }


    @DeleteMapping("/s3/file")
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
            receiptDTO.setVisitDate(String.valueOf(resultMap.get("방문날짜시간")));

            String totalCost = String.valueOf(resultMap.get("총가격"));
            receiptDTO.setTotalCost(Integer.parseInt(totalCost.replace(",", "")));

            // 진료내역을 MedicalDTO 리스트로 변환
            List<MedicalDTO> medicalDTOs = new ArrayList<>();
            Map<String, Object> medicalDetails = (Map<String, Object>) resultMap.get("진료내역리스트");
            if (medicalDetails != null) {
                for (Map.Entry<String, Object> entry : medicalDetails.entrySet()) {
                    String price = String.valueOf(entry.getValue());
                    medicalDTOs.add(new MedicalDTO(entry.getKey(), price.replace(",", "")));
                }
            }

            receiptDTO.setMedicalDTOs(medicalDTOs);



            return receiptDTO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/disease/analysis")
    public ResponseEntity<?> analyzeDisease(@RequestBody ReceiptDTO receiptData) {
        try {
            String beforeAsk = "아래 목록에 대해서 추론할 수 있는 질병을 대분류 기준으로 key로 작성하고, 근거를 해당 키의 value에 작성하여 json으로 나에게 보내줘.\n" +
                    "근거는 아래 목록의 키값으로 이루어진 목록이야." + receiptData.getMedicalDTOs() + "대분류는 아래와 같아. 띄어쓰기 하지 말고, 추론할수 있는 병의 대분류를 키로 사용해줘.\n" +
                    "피부계통(귀)\t호흡기계통\t소화기계통\t근골격계통\t대사계통\t신경계통\t내분비(호르몬)계통\t순환계통(심장)\t비뇨생식계통\t눈\t구강\t종양성";

            ResponseEntity<?> gptResponse = gptService.getAssistantMsg(beforeAsk);

            String jsonString = gptResponse.getBody().toString();
            List<DiseaseAnalysisDTO> formattedResponse = parseJsonString(jsonString);

            System.out.println(formattedResponse);

            return ResponseEntity.ok(formattedResponse);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body("healthy!");
        }

    }

    private List<DiseaseAnalysisDTO> parseJsonString(String jsonString) {
        List<DiseaseAnalysisDTO> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(.*?)\":\\s*\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(jsonString);

        while (matcher.find()) {
            String diseaseTitle = matcher.group(1);
            String[] reasons = matcher.group(2).split(",");
            List<String> reasonList = new ArrayList<>();
            for (String reason : reasons) {
                reasonList.add(reason.trim().replaceAll("\"", ""));
            }
            result.add(new DiseaseAnalysisDTO(diseaseTitle, reasonList));
        }

        return result;
    }
}
}

