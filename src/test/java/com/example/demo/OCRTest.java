//package com.example.demo;
//
//import com.example.demo.util.ReceiptParserService;
//import org.checkerframework.checker.units.qual.A;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Map;
//
//public class OCRTest {
//    @Autowired
//    private ReceiptParserService receiptParserService;
//    public static void main(String[] args) {
//        String ocrText = "청 구 서\n" +
//                "사업자 등록번호 : 111-38-66998\n" +
//                "병\n" +
//                "대\n" +
//                "원표\n" +
//                "명: 장재영외과동물병원\n" +
//                "자: 장재영\n" +
//                "사업장\n" +
//                "소재지: 경기도 성남시 분당구\n" +
//                "운중로 142 2층\n" +
//                "전화 번호: 031-8017-7511\n" +
//                "담\n" +
//                "당 자: 장재영\n" +
//                "고객 번호: 4378\n" +
//                "고객 이름: 한민규\n" +
//                "날 짜: 2020-06-19\n" +
//                "진료 (222,000)\n" +
//                "[동물명: 모나]\n" +
//                "'재진료 (Re-Examination)\n" +
//                "1 9,500\n" +
//                "Propofol (프로바이브 주 200\n" +
//                "mg/20ml); 주사마취제\n" +
//                "1\n" +
//                "22,000\n" +
//                "'향정신성의약품 처방\n" +
//                "1\n" +
//                "11,000\n" +
//                "카테터 (18-24G IV Catheter)\n" +
//                "장착\n" +
//                "1\n" +
//                "12,000\n" +
//                "주사마취4 (Injectable Anest\n" +
//                "hesia): > 20kg 이상\n" +
//                "1 77,000\n" +
//                "방사선 검사 (Radiograph),\n" +
//                "컷당 20-30kg\n" +
//                "2\n" +
//                "77,000\n" +
//                "V프레비콕스(firocoxib)\n" +
//                "7.5 13,500\n" +
//                "*표시가 되어 있는 항목은 비과세 입니다.\n" +
//                "총금액:\n" +
//                "222,000\n" +
//                "과세품목합계\n" +
//                "222,000\n" +
//                "과세품목에 포함된 부가세\n" +
//                "20, 180\n" +
//                "결제금액합계\n" +
//                "222,000\n" +
//                "청구 금액:\n" +
//                "222,000\n" +
//                "발행일: 2020-06-19 12:44:48 청 구 서 사업자 등록 번호 : 111-38-66998 병 대 원표 명 : 장재영 외과 동물 병원 자 : 장재영 사업장 소재지 : 경기도 성남시 분당구 운중 로 142 2 층 전화 번호 : 031-8017-7511 담 당 자 : 장재영 고객 번호 : 4378 고객 이름 : 한민규 날 짜 : 2020-06-19 진료 ( 222,000 ) [ 동물 명 : 모나 ] ' 재 진료 ( Re - Examination ) 1 9,500 Propofol ( 프로 바이브 주 200 mg / 20ml ) ; 주사 마취제 1 22,000 ' 향정신성 의약품 처방 1 11,000 카테터 ( 18-24G IV Catheter ) 장착 1 12,000 주사 마취 4 ( Injectable Anest hesia ) : > 20kg 이상 1 77,000 방사선 검사 ( Radiograph ) , 컷당 20-30kg 2 77,000 V 프레 비 콕스 ( firocoxib ) 7.5 13,500 * 표시 가 되어 있는 항목 은 비과세 입니다 . 총금액 : 222,000 과세 품목 합계 222,000 과세 품목 에 포함 된 부가세 20 , 180 결제 금액 합계 222,000 청구 금액 : 222,000 발행일 : 2020-06-19 12:44:48 \n";
//
//    }
//}
