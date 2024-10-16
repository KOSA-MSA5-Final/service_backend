package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.disease.entity.DiseaseNames;
import com.example.demo.domains.disease.entity.DiseaseSub;
import com.example.demo.domains.disease.entity.MedicalDisease;
import com.example.demo.domains.disease.repository.DiseaseSubRepository;
import com.example.demo.domains.disease.repository.MedicalDiseaseRepository;
import com.example.demo.domains.disease.service.interfaces.DiseaseNamesService;
import com.example.demo.domains.disease.service.interfaces.DiseaseSubService;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.service.impls.MemberService;
import com.example.demo.domains.profile_medical.entity.*;
import com.example.demo.domains.profile_medical.repository.HospitalRepository;
import com.example.demo.domains.profile_medical.repository.MedicalHospitalRepository;
import com.example.demo.domains.profile_medical.repository.MedicalRepository;
import com.example.demo.domains.profile_medical.repository.TreatmentRepository;
import com.example.demo.domains.profile_medical.service.interfaces.ProfileService;
import com.example.demo.dtos.*;
import com.example.demo.util.AwsS3Service;
import com.example.demo.util.GoogleVisionOCR;
import com.example.demo.util.GptService;
import com.example.demo.util.ReceiptParserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "https://3.34.164.100:80")
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReceiptController {
    private final AwsS3Service awsS3Service;
    private final ReceiptParserService receiptParserService;
    private final GptService gptService;
    private final DiseaseNamesService diseaseNamesService;
    private final DiseaseSubService diseaseSubService;
    private final DiseaseSubRepository diseaseSubRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ProfileService profileService;
    private final HospitalRepository hospitalRepository;
    private final MedicalRepository medicalRepository;
    private final MedicalDiseaseRepository medicalDiseaseRepository;
    private final MedicalHospitalRepository medicalHospitalRepository;
    private final TreatmentRepository treatmentRepository;

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
        String beforeAsk = "아래는 영수증(청구서) 내용을 OCR로 읽어낸거야. 내가 원하는 값들은 진료 내역들 (이름-가격)과 병원명, 대표자이름, 사업자 등록번호와 사업장 소재지, 전화번호, 총 가격 및 방문 날짜시간이야. 각 json 형태로 key 이름을 '진료내역리스트'(각 항목의 키는 진료이름(정형화된 실제 데이터로 작성해줘. 진료내역1 이런식으론 절대로 하지마.), 값은 가격), '병원명', '대표자이름', '사업자등록번호', '사업장주소', '전화번호', '총가격', '방문날짜시간' 이라고 명시하여 아래내용들을 정형화 해줘. key 이름에 빈 문자열 생기지 않도록 주의하고, 만약 각 key의 값을 찾을 수 없다면, 키는 그대로 넣고 빈 문자열을 넣어서 보내줘. 가격은 숫자만 넣어.\n";
        ResponseEntity<?> gptResponse = gptService.getAssistantMsg(beforeAsk + parsed);
        System.out.println(gptResponse.getBody());
        ReceiptDTO receiptDTO = parseReceipt(gptResponse, resultURL);

        return receiptDTO;
    }


    @DeleteMapping("/s3/file")
    public void deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteFile(fileName);
    }//end deleteFile

    private static ReceiptDTO parseReceipt(ResponseEntity<?> receipt, String receipt_url) {
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


            receiptDTO.setReceipt_url(receipt_url);
            return receiptDTO;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @PostMapping("/disease/analysis")
    public ResponseEntity<?> analyzeDisease(@RequestBody List<MedicalDTO> medicalDTOs) {
        System.out.println("Received data: " + medicalDTOs);
        String medicalList = "";
        if(medicalDTOs != null && medicalDTOs.size() > 0) {
            for (MedicalDTO medicalDTO : medicalDTOs) {
                medicalList += "'";
                medicalList += medicalDTO.getMedical_name();
                medicalList += "', ";
            }
        }
        try {
            String beforeAsk = "근거 목록에서 추론할 수 있는 질병을 대분류 기준으로 key로 작성하고, 근거들을 해당 키의 value에 작성하여 json으로 나에게 보내줘. 근거목록은 [" + medicalList + "] 이고, 병명 대분류는 '피부계통(귀)', '호흡기계통', '소화기계통', '근골격계통', '대사계통', '신경계통', '내분비(호르몬)계통', '순환계통(심장)', '비뇨생식계통, '눈','구강', '종양성' 이야. 대분류 이름을 너 마음대로 수정하지말고 내가 제공해준 문자열그대로 사용하여 추론할수 있는 병의 대분류를 키로 사용해줘. 추론할 수 없는 대분류들은 포함하지 마.\n";

            ResponseEntity<?> gptResponse = gptService.getAssistantMsg(beforeAsk);

            String jsonString = gptResponse.getBody().toString();
            System.out.println("GPT Response: " + jsonString);
            List<DiseaseAnalysisDTO> formattedResponse = parseJsonString(jsonString);

            System.out.println(">>>>여기는 disease/analysis");
            System.out.println(formattedResponse);

            return ResponseEntity.ok(formattedResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    private List<DiseaseAnalysisDTO> parseJsonString(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> map = mapper.readValue(jsonString, new TypeReference<Map<String, List<String>>>(){});

        List<DiseaseAnalysisDTO> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if(entry.getValue().size() > 0) {
                result.add(new DiseaseAnalysisDTO(entry.getKey(), entry.getValue()));
            }
        }
        return result;
    }

    @PostMapping("/fetch_subdiseases")
    public ResponseEntity<?> fetchSubdiseases(@RequestBody List<String> diseaseNames) {
        try {
            List<AnalysedDiseaseDTO> analysedDiseaseDTOS = new ArrayList<>();
            for (String name : diseaseNames) {
                DiseaseNames disease = diseaseNamesService.findDiseaseByName(name);
                if (disease != null){

                    List<String> subDiseases = diseaseSubRepository.findSubDiseasesByDiseaseName(name);
                    AnalysedDiseaseDTO analysedDiseaseDTO = new AnalysedDiseaseDTO();
                    analysedDiseaseDTO.setDiseaseName(name);
                    analysedDiseaseDTO.setSubDiseases(subDiseases);
                    analysedDiseaseDTOS.add(analysedDiseaseDTO);
                }

            }

            return ResponseEntity.ok(analysedDiseaseDTOS);
        } catch (Exception e) {
            return ResponseEntity.ok("healthy");
        }
    }

    @PostMapping("/unfound_diseases")
    public ResponseEntity<?> fetchUnfoundDiseases(@RequestBody List<String> diseaseNames) {
        try {
            List<DiseaseNames> allDiseases = diseaseNamesService.findAllDiseases();
            List<UnfoundDiseaseDTO> unfoundDiseases = new ArrayList<>();
            for (DiseaseNames found : allDiseases) {
                String foundName = found.getName();
                Boolean f = false;
                for (String name : diseaseNames) {
                    if (foundName.equals(name)) {
                        f = true;
                    }
                }
                if (!f) {

                    UnfoundDiseaseDTO unfoundDiseaseDTO = new UnfoundDiseaseDTO();
                    unfoundDiseaseDTO.setDiseaseName(foundName);
                    unfoundDiseaseDTO.setSelected(false);
                    unfoundDiseases.add(unfoundDiseaseDTO);
                }
            }
            if (unfoundDiseases.size() > 0) {
                return ResponseEntity.ok(unfoundDiseases);
            }
            return ResponseEntity.ok("none");
        } catch (Exception e) {
            return ResponseEntity.ok("none");
        }
    }

    @PostMapping("/uploadProfileImage")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("qimage") List<MultipartFile> multipartFile) throws IOException {
        List<String> result = awsS3Service.uploadFile(multipartFile);
        String resultURL = result.get(0);
        System.out.println(resultURL);
        return ResponseEntity.ok(resultURL);
    }

    @PostMapping("/save_receipt")
    public ResponseEntity<?> saveReceipt(@RequestBody SaveReceiptRequestDTO requestDTO,
                                         @RequestHeader("Authorization") String token) {
        System.out.println("I'm here!!!!!!!!! " + requestDTO.toString());
        try {
            // Bearer 토큰에서 "Bearer " 제거
            String jwtToken = token.replace("Bearer ", "");

            // 토큰에서 사용자 정보 추출
            String username = jwtUtil.getUsername(jwtToken);
            Member member = memberRepository.findByUsername(username);

            if (member == null) {
                System.out.println("User not found for username: " + username);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            Profile currentProfile = profileService.getCurrentProfileByMember(member);

            List<UnfoundDiseaseDTO> extraMedicals = requestDTO.getExtraMedical();
            ReceiptDTO receiptInfo = requestDTO.getReceiptInfo();
            List<AnalysedDiseaseDTO> selectedDiseases = requestDTO.getSelectedDiseases();

            // 진료 기록 정보 세팅
            Medical newMedical = new Medical();
            newMedical.setReceipt_img(receiptInfo.getReceipt_url());
            newMedical.setTotalCost(receiptInfo.getTotalCost());
            newMedical.setProfile(currentProfile);
            newMedical.setVisitDate(convertToDate(receiptInfo.getVisitDate()));

            Medical myMedical = medicalRepository.save(newMedical);
            // 병원 정보 세팅 (및 저장)
            Hospital myHospital;
            Long hospitalId = extractNumbers(receiptInfo.getReg_num());
            Optional<Hospital> hospitalOptional = hospitalRepository.findById(hospitalId);

            if (hospitalOptional.isPresent()) {
                myHospital = hospitalOptional.get();
            } else {
                Hospital newHospital = new Hospital();
                newHospital.setAddress(receiptInfo.getHospital_address());
                newHospital.setName(receiptInfo.getHospital_name());
                newHospital.setIs_ours("F");
                newHospital.setDoctor("잘 모름");
                newHospital.setPhone_number(receiptInfo.getHospital_phoneNum());
                newHospital.setId(hospitalId);
                myHospital = hospitalRepository.save(newHospital);
            }

            // ExtraMedical은 저장을 할 수 없음 ... 연결이 안됨... 대분류라서...

            // 진단명 String으로 뽑아내기 시작.
            String objectNames = "";
            for(AnalysedDiseaseDTO analysedDiseaseDTO : selectedDiseases) {
                String DiseaseName = analysedDiseaseDTO.getDiseaseName();
                List<String> subDiseases = analysedDiseaseDTO.getSubDiseases();
                if(subDiseases.size() > 0){
                    for(String sub : subDiseases){
                        objectNames = objectNames + "#" + sub;

                        List<DiseaseSub> diseaseSubs = diseaseSubRepository.findAllByName(sub);
                        DiseaseSub currentSub = null;
                        if (diseaseSubs.size() > 0) {
                            for (DiseaseSub ds : diseaseSubs){
                                if (ds.getDiseaseNames().getName().equals(DiseaseName)){
                                    currentSub = ds;
                                }
                            }
                        }

                        // medicalSub - Medical 관계 엔티티 저장
                        if(currentSub != null){
                            MedicalDisease medicalDisease = new MedicalDisease();
                            medicalDisease.setMedical(myMedical);
                            medicalDisease.setDiseaseSub(currentSub);
                            medicalDisease.setProgressStatus("T");
                            medicalDisease.setDiagnosisDate(myMedical.getVisitDate());
                            medicalDiseaseRepository.save(medicalDisease);
                        }

                    }
                }
            }

            // 진료 기록 저장
            myMedical.setObject(objectNames);
            myMedical = medicalRepository.save(myMedical);

            // medical-hospital 관계식 저장
            MedicalHospital medicalHospital = new MedicalHospital();
            medicalHospital.setHospital(myHospital);
            medicalHospital.setMedical(myMedical);
            medicalHospitalRepository.save(medicalHospital);

            // 영수증 내역들 세팅
            List<MedicalDTO> treatments = receiptInfo.getMedicalDTOs();
            for (MedicalDTO medicalDTO : treatments) {
                String treatment_name = medicalDTO.getMedical_name();
                Long treatment_price = Long.parseLong(medicalDTO.getMedical_price());

                Treatment newTreatment = new Treatment();
                newTreatment.setHospital(myHospital);
                newTreatment.setMedical(myMedical);
                newTreatment.setName(treatment_name);
                newTreatment.setPrice(treatment_price);
                treatmentRepository.save(newTreatment);
            }

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            System.out.println("Error saving receipt" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }

    }

    public static Date convertToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);
    }
    public static Long extractNumbers(String input) {
        String numericString = input.replaceAll("[^0-9]", "");
        if (numericString.isEmpty()) {
            return null; // or you could return 0L, depending on your requirements
        }
        return Long.parseLong(numericString);
    }

    // profile 사진 저장 로직인데, 다른 컨트롤러에 넣으려니 에러가 나서 여기에 넣음.
    @PostMapping("/s3/profile")
    public String uploadProfileImg(
            @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("나 여기!!!!!!!!!!!!!!!!!");
        List<String> result = awsS3Service.uploadFile(Collections.singletonList(file));
        String resultURL = result.get(0);
        return resultURL;
    }
}

