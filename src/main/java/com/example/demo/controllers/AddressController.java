package com.example.demo.controllers;

import com.example.demo.config.JWTUtil;
import com.example.demo.domains.member.entity.Member;
import com.example.demo.domains.member.entity.MyLocation;
import com.example.demo.domains.member.repository.MemberRepository;
import com.example.demo.domains.member.repository.MyLocationRepository;
import com.example.demo.domains.member.service.impls.MyLocationService;
import com.example.demo.dtos.AddressDTO;
import jakarta.mail.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://3.34.164.100:80") // 해당 컨트롤러에만 CORS 설정
@RequestMapping("/auth/address")
@RequiredArgsConstructor
public class AddressController {
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final MyLocationService myLocationService;
    private final MyLocationRepository myLocationRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        // Bearer 앞의 "Bearer " 제거
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);

        // 사용자 정보를 반환할 DTO 객체 생성
        Member member = memberRepository.findByUsername(username);

        List<MyLocation> locations = myLocationService.findMyLocations(member);
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (MyLocation myLocation : locations) {
            addressDTOS.add(new AddressDTO(myLocation));
        }
        return ResponseEntity.ok(addressDTOS);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestHeader("Authorization") String token, @RequestBody AddressDTO addressDTO) {
        // Bearer 앞의 "Bearer " 제거
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);

        // 사용자 정보를 반환할 DTO 객체 생성
        Member member = memberRepository.findByUsername(username);

        MyLocation myLocation = new MyLocation();
        myLocation.setAddress(addressDTO.getAddress());
        myLocation.setMember(member);
        myLocation.setReceipientName(addressDTO.getReceipientName());
        myLocation.setReceipientTellNum(addressDTO.getReceipientTellNum());

        List<MyLocation> locations = myLocationService.findMyLocations(member);

        if (locations.size() > 0) {
            myLocation.setIsPrimary("F");
        } else {
            myLocation.setIsPrimary("T");
        }
        myLocationRepository.save(myLocation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change_primary")
    public ResponseEntity<?> addAddress(@RequestHeader("Authorization") String token, @RequestBody Map<String, Long> requestBody) {
        Long addressId = requestBody.get("addressId");
//        System.out.println("하이룽");
        // Bearer 앞의 "Bearer " 제거
        String jwtToken = token.substring(7);

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(jwtToken);

        // 사용자 정보를 반환할 DTO 객체 생성
        Member member = memberRepository.findByUsername(username);

        List<MyLocation> locations = myLocationService.findMyLocations(member);

        MyLocation changeLocation = myLocationRepository.findById(addressId).get();
        MyLocation usedPrimary = null;
        for (MyLocation myLocation : locations) {
            if (myLocation.getIsPrimary().equals("T")) {
                usedPrimary = myLocation;
            }
        }
        usedPrimary.setIsPrimary("F");
        myLocationRepository.save(usedPrimary);
        changeLocation.setIsPrimary("T");
        myLocationRepository.save(changeLocation);
        return ResponseEntity.ok().build();
    }
}
