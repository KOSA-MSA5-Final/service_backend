package com.example.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://3.34.164.100:80")
@RequiredArgsConstructor
@ResponseBody
public class TestContoller {
    @GetMapping("/admin222")
    public String adminP() {
        System.out.println("나다");
        return "admin Controller";
    }
}
