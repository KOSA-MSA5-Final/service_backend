package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class TestContoller {
    @GetMapping("/admin222")
    public String adminP() {
        System.out.println("나다");
        return "admin Controller";
    }
}
