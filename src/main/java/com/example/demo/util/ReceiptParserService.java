package com.example.demo.util;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReceiptParserService {

    public Map<String, Object> parseReceipt(String ocrText) {
        Map<String, Object> result = new HashMap<>();

        // 기존의 다른 필드 추출 로직은 유지
        Pattern businessNumberPattern = Pattern.compile("사업자\\s*등록\\s*번호\\s*:?\\s*(\\d{3}-\\d{2}-\\d{5})");
        Pattern hospitalNamePattern = Pattern.compile("병\\s*원\\s*명\\s*:?\\s*(.+?)(?=\\s*대\\s*표|$)", Pattern.DOTALL);
        Pattern representativePattern = Pattern.compile("대\\s*표\\s*자\\s*:?\\s*(.+?)(?=\\s*사업장|$)", Pattern.DOTALL);
        Pattern addressPattern = Pattern.compile("사업장\\s*소재지\\s*:?\\s*(.+?)(?=\\s*전화|$)", Pattern.DOTALL);
        Pattern phonePattern = Pattern.compile("전화\\s*번호\\s*:?\\s*(\\d{2,3}-\\d{3,4}-\\d{4})");
        Pattern datePattern = Pattern.compile("날\\s*짜\\s*:?\\s*(\\d{4}-\\d{2}-\\d{2})");
        Pattern animalNamePattern = Pattern.compile("\\[\\s*동물\\s*명\\s*:?\\s*(.+?)\\s*\\]");
        Pattern customerNamePattern = Pattern.compile("고객\\s*이름\\s*:?\\s*(.+?)(?=\\s*날|$)", Pattern.DOTALL);

        result.put("사업자등록번호", extractInfo(ocrText, businessNumberPattern));
        result.put("병원명", cleanExtractedInfo(extractInfo(ocrText, hospitalNamePattern)));
        result.put("대표자이름", cleanExtractedInfo(extractInfo(ocrText, representativePattern)));
        result.put("주소", cleanExtractedInfo(extractInfo(ocrText, addressPattern)));
        result.put("전화번호", extractInfo(ocrText, phonePattern));
        result.put("날짜", extractInfo(ocrText, datePattern));
        result.put("동물명", extractInfo(ocrText, animalNamePattern));
        result.put("고객이름", cleanExtractedInfo(extractInfo(ocrText, customerNamePattern)));

        // 진료 항목 추출
        result.put("진료항목", extractTreatmentItems(ocrText));

        return result;
    }

    private String extractInfo(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    private String cleanExtractedInfo(String info) {
        return info.replaceAll("\\s+", " ").trim();
    }

    private List<String> extractTreatmentItems(String text) {
        List<String> items = new ArrayList<>();
        Pattern startPattern = Pattern.compile("\\[\\s*동물\\s*명\\s*:.*?\\]");
        Pattern endPattern = Pattern.compile("\\*표시.*비과세.*입니다");

        Matcher startMatcher = startPattern.matcher(text);
        Matcher endMatcher = endPattern.matcher(text);

        if (startMatcher.find() && endMatcher.find()) {
            String treatmentSection = text.substring(startMatcher.end(), endMatcher.start());
            String[] lines = treatmentSection.split("\n");

            StringBuilder currentItem = new StringBuilder();
            String quantity = "";
            String price = "";

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.matches("^\\d+(?:\\.\\d+)?\\s+\\d{1,3}(?:,\\d{3})*$")) {
                    String[] parts = line.split("\\s+");
                    quantity = parts[0];
                    price = parts[1];
                    if (!currentItem.toString().isEmpty()) {
                        items.add(String.format("%s : %s : %s",
                                currentItem.toString().trim(), quantity, price));
                        currentItem.setLength(0);
                    }
                } else {
                    if (currentItem.length() > 0 && !quantity.isEmpty() && !price.isEmpty()) {
                        items.add(String.format("%s : %s : %s",
                                currentItem.toString().trim(), quantity, price));
                        currentItem.setLength(0);
                        quantity = "";
                        price = "";
                    }
                    currentItem.append(line).append(" ");
                }
            }

            // 마지막 항목 처리
            if (!currentItem.toString().isEmpty() && !quantity.isEmpty() && !price.isEmpty()) {
                items.add(String.format("%s : %s : %s",
                        currentItem.toString().trim(), quantity, price));
            }
        }

        return items;
    }
}