package com.example.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"https://d3b5vhsqsj0w82.cloudfront.net", "https://mgng.site","http://localhost:80", "https://localhost:80", "https://3.34.164.100:80", "http://mgng.site"}
,allowCredentials = "true")
@RequiredArgsConstructor
@ResponseBody
public class TestContoller {
    @GetMapping("/admin222")
    public String adminP() {
        System.out.println("나다");
        return "admin Controller";
    }
}
